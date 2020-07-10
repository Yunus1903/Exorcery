package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.common.item.MagicalPotionItem;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 09/07/2020
 */
public class MagicalPotionEntityInteractPacket
{
    private final int entityId;

    public MagicalPotionEntityInteractPacket(int entityId)
    {
        this.entityId = entityId;
    }

    public static void encode(MagicalPotionEntityInteractPacket pkt, PacketBuffer buf)
    {
        buf.writeVarInt(pkt.entityId);
    }

    public static MagicalPotionEntityInteractPacket decode(PacketBuffer buf)
    {
        return new MagicalPotionEntityInteractPacket(buf.readVarInt());
    }

    public static class Handler
    {
        public static void handle(MagicalPotionEntityInteractPacket msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (ctx.get().getDirection().getReceptionSide().isServer())
            {
                ctx.get().enqueueWork(() ->
                {
                    ServerPlayerEntity player = ctx.get().getSender();
                    ItemStack stack = player.getActiveItemStack();
                    if (stack.getItem() == ExorceryItems.MAGICAL_POTION)
                    {
                        MagicalPotionItem.entityInteract(stack, player, player.world.getEntityByID(msg.entityId));
                    }
                });
                ctx.get().setPacketHandled(true);
            }
        }
    }
}
