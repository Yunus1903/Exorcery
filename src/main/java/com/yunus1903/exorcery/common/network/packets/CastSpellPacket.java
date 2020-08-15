package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.common.util.BlockSpellTarget;
import com.yunus1903.exorcery.common.util.EntitySpellTarget;
import com.yunus1903.exorcery.common.util.SpellTarget;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 */
public class CastSpellPacket
{
    private final Spell spell;
    private final PlayerEntity player;
    private final SpellTarget target;

    public CastSpellPacket(Spell spell, PlayerEntity player)
    {
        this(spell, player, null);
    }

    public CastSpellPacket(Spell spell, PlayerEntity player, @Nullable SpellTarget target)
    {
        this.spell = spell;
        this.player = player;
        this.target = target;
    }

    public static void encode(CastSpellPacket pkt, PacketBuffer buf)
    {
        pkt.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
        {
            buf.writeUniqueId(pkt.player.getUniqueID());
            buf.writeInt(spells.getSpellId(pkt.spell));

            if (pkt.target != null)
            {
                if (pkt.target instanceof BlockSpellTarget)
                {
                    buf.writeInt(1);
                    boolean exists = pkt.target.getType() == SpellTarget.Type.BLOCK;
                    buf.writeBoolean(exists);
                    if (exists) buf.writeBlockPos(((BlockSpellTarget) pkt.target).getPos());
                    return;
                }
                else if (pkt.target instanceof EntitySpellTarget)
                {
                    buf.writeInt(2);
                    boolean exists = pkt.target.getType() == SpellTarget.Type.ENTITY;
                    buf.writeBoolean(exists);
                    if (exists) buf.writeInt(((EntitySpellTarget) pkt.target).getEntity().getEntityId());
                    return;
                }
            }
            buf.writeInt(0);
        });
    }

    public static CastSpellPacket decode(PacketBuffer buf)
    {
        AtomicReference<PlayerEntity> player = new AtomicReference<>();

        if (Exorcery.instance.server == null)
        {
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> player.set(Minecraft.getInstance().player));
            buf.readUniqueId();
        }
        else
        {
            player.set(Exorcery.instance.server.getPlayerList().getPlayerByUUID(buf.readUniqueId()));
        }

        ISpells spells = player.get().getCapability(SpellsProvider.SPELLS_CAPABILITY, null).orElse(new SpellsCapability());
        Spell spell = spells.getSpellById(buf.readInt());

        int test = buf.readInt();
        switch (test)
        {
            case 1:
                if (buf.readBoolean()) return new CastSpellPacket(spell, player.get(), new BlockSpellTarget(null, buf.readBlockPos()));
                else return new CastSpellPacket(spell, player.get(), new BlockSpellTarget(true, null, null));
            case 2:
                if (buf.readBoolean()) return new CastSpellPacket(spell, player.get(), new EntitySpellTarget((LivingEntity) player.get().world.getEntityByID(buf.readInt())));
                else return new CastSpellPacket(spell, player.get(), new EntitySpellTarget(true, null));
        }
        return new CastSpellPacket(spell, player.get());
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
                    msg.spell.castSpell(player.world, player, msg.target);
                });
            }
            else if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {
                    AtomicReference<PlayerEntity> player = new AtomicReference<>();

                    DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> player.set(Minecraft.getInstance().player));
                    msg.spell.castSpell(player.get().world, player.get(), true);
                });
            }
            ctx.get().setPacketHandled(true);
        }
    }
}
