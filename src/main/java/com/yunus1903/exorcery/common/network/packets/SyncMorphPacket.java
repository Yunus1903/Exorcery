package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.client.misc.ClientEventHandler;
import com.yunus1903.exorcery.common.capabilities.morph.MorphProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 25/06/2020
 */
public class SyncMorphPacket
{
    private final HashMap<LivingEntity, EntityType<? extends LivingEntity>> morphedEntities;

    public SyncMorphPacket(HashMap<LivingEntity, EntityType<? extends LivingEntity>> morphedEntities)
    {
        this.morphedEntities = morphedEntities;
    }

    public static void encode(SyncMorphPacket pkt, PacketBuffer buf)
    {
        ListNBT list = new ListNBT();

        pkt.morphedEntities.forEach((a, b) ->
        {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("entity", IntNBT.valueOf(a.getEntityId()));
            compoundNBT.put("entityType", StringNBT.valueOf(ForgeRegistries.ENTITIES.getKey(b).toString()));
            list.add(compoundNBT);
        });

        CompoundNBT nbt = new CompoundNBT();
        nbt.put("list", list);

        buf.writeCompoundTag(nbt);
    }

    public static SyncMorphPacket decode(PacketBuffer buf)
    {
        HashMap<LivingEntity, EntityType<? extends LivingEntity>> entities = new HashMap<>();

        ((ListNBT) buf.readCompoundTag().get("list")).forEach(a ->
        {
            CompoundNBT compoundNBT = (CompoundNBT) a;
            entities.put((LivingEntity) Minecraft.getInstance().world.getEntityByID(compoundNBT.getInt("entity")), (EntityType<? extends LivingEntity>) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(compoundNBT.getString("entityType"))));
        });

        return new SyncMorphPacket(entities);
    }

    public static class Handler
    {
        public static void handle(SyncMorphPacket msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {
                    Minecraft.getInstance().world.getCapability(MorphProvider.MORPH_CAPABILITY).ifPresent(morph ->
                    {
                        morph.setMorphedEntities(msg.morphedEntities);
                    });
                });
            }
            ctx.get().setPacketHandled(true);
        }
    }
}
