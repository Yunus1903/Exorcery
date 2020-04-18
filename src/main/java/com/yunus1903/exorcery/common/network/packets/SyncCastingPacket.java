package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.common.capabilities.casting.CastingCapability;
import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.capabilities.casting.ICasting;
import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 14/04/2020
 */
public class SyncCastingPacket
{
    private final boolean isCasting;
    private final Spell spell;

    public SyncCastingPacket(boolean isCasting, Spell spell)
    {
        this.isCasting = isCasting;
        this.spell = spell;
    }

    public static void encode(SyncCastingPacket pkt, PacketBuffer buf)
    {
        buf.writeBoolean(pkt.isCasting);
        buf.writeVarInt(Spell.getIdFromSpell(pkt.spell));
    }

    public static SyncCastingPacket decode(PacketBuffer buf)
    {
        return new SyncCastingPacket(buf.readBoolean(), Spell.getSpellById(buf.readVarInt()));
    }

    public static class Handler
    {
        public static void handle(SyncCastingPacket msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {
                    Minecraft mc = Minecraft.getInstance();
                    ICasting casting = mc.player.getCapability(CastingProvider.CASTING_CAPABILITY).orElse(new CastingCapability());
                    Spell spell = null;

                    if (msg.isCasting)
                    {
                        spell = msg.spell;
                    }

                    casting.startCasting(spell);
                });
            }
            ctx.get().setPacketHandled(true);
        }
    }
}
