package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 */
public class CastSpellPacket
{
    private final Spell spell;
    private final PlayerEntity player;
    private LivingEntity targetEntity;
    private BlockPos targetLocation;

    public CastSpellPacket(Spell spell, PlayerEntity player)
    {
        this(spell, player, null, null);
    }

    public CastSpellPacket(Spell spell, PlayerEntity player, LivingEntity target)
    {
        this(spell, player, target, null);
    }

    public CastSpellPacket(Spell spell, PlayerEntity player, BlockPos target)
    {
        this(spell, player, null, target);
    }

    public CastSpellPacket(Spell spell, PlayerEntity player, LivingEntity targetEntity, BlockPos targetLocation)
    {
        this.spell = spell;
        this.player = player;
        this.targetEntity = targetEntity;
        this.targetLocation = targetLocation;
    }

    public static void encode(CastSpellPacket pkt, PacketBuffer buf)
    {
        pkt.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
        {
            buf.writeUniqueId(pkt.player.getUniqueID());
            buf.writeInt(spells.getSpellId(pkt.spell));

            int target = 0;
            if (pkt.targetEntity != null && pkt.targetLocation != null) target = 3;
            else if (pkt.targetEntity != null) target = 1;
            else if (pkt.targetLocation != null) target = 2;

            buf.writeInt(target);

            if (target == 1 || target == 3) buf.writeInt(pkt.targetEntity.getEntityId());
            if (target == 2 || target == 3) buf.writeBlockPos(pkt.targetLocation);
        });
    }

    public static CastSpellPacket decode(PacketBuffer buf)
    {
        AtomicReference<PlayerEntity> player = new AtomicReference<>();

        if (Exorcery.instance.server == null)
        {
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
            {
                player.set(Minecraft.getInstance().player);
            });
            buf.readUniqueId();
        }
        else
        {
            player.set(Exorcery.instance.server.getPlayerList().getPlayerByUUID(buf.readUniqueId()));
        }

        ISpells spells = player.get().getCapability(SpellsProvider.SPELLS_CAPABILITY, null).orElse(new SpellsCapability());
        Spell spell = spells.getSpellById(buf.readInt());

        int target = buf.readInt();

        LivingEntity targetEntity;
        BlockPos targetLocation;

        switch (target)
        {
            case 0:
                return new CastSpellPacket(spell, player.get());
            case 1:
                targetEntity = (LivingEntity) player.get().world.getEntityByID(buf.readInt());
                return new CastSpellPacket(spell, player.get(), targetEntity);
            case 2:
                targetLocation = buf.readBlockPos();
                return new CastSpellPacket(spell, player.get(), targetLocation);
            case 3:
                targetEntity = (LivingEntity) player.get().world.getEntityByID(buf.readInt());
                targetLocation = buf.readBlockPos();
                return new CastSpellPacket(spell, player.get(), targetEntity, targetLocation);
            default:
                return null;
        }
    }

    public static class Handler
    {
        public static void handle(CastSpellPacket msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (ctx.get().getDirection().getReceptionSide().isServer())
            {
                ctx.get().enqueueWork(() ->
                {
                    ServerPlayerEntity player = ctx.get().getSender();
                    msg.spell.castSpell(player.world, player, msg.targetEntity, msg.targetLocation);
                });
            }
            else if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {
                    AtomicReference<PlayerEntity> player = new AtomicReference<>();

                    DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
                    {
                        player.set(Minecraft.getInstance().player);
                    });
                    msg.spell.castSpell(player.get().world, player.get(), true);
                });
            }
            ctx.get().setPacketHandled(true);
        }
    }
}
