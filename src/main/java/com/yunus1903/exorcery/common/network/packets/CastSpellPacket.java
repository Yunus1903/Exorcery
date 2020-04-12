package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CastSpellPacket
{
    private final Spell spell;
    private final PlayerEntity player;

    public CastSpellPacket(Spell spell, PlayerEntity player)
    {
        this.spell = spell;
        this.player = player;
    }

    public static void encode(CastSpellPacket pkt, PacketBuffer buf)
    {
        ISpells spells = pkt.player.getCapability(SpellsProvider.SPELLS_CAPABILITY, null).orElse(new SpellsCapability());
        buf.writeUniqueId(pkt.player.getUniqueID());
        buf.writeInt(spells.getSpellId(pkt.spell));
    }

    public static CastSpellPacket decode(PacketBuffer buf)
    {
        PlayerEntity player;

        if (Exorcery.instance.server == null)
        {
            player = Minecraft.getInstance().player;
        }
        else
        {
            player = Exorcery.instance.server.getPlayerList().getPlayerByUUID(buf.readUniqueId());
        }

        ISpells spells = player.getCapability(SpellsProvider.SPELLS_CAPABILITY, null).orElse(new SpellsCapability());
        return new CastSpellPacket(spells.getSpellById(buf.readInt()), player);
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
                    msg.spell.castSpell(player.world, player);
                });
            }
            else if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {
                    PlayerEntity player = Minecraft.getInstance().player;
                    msg.spell.castSpell(player.world, player, true);
                });
            }
            ctx.get().setPacketHandled(true);
        }
    }
}
