package com.yunus1903.exorcery.common.network.packets;

import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 */
public class SyncSpellsPacket
{
    private final List<Spell> spells;

    public SyncSpellsPacket(List<Spell> spells)
    {
        this.spells = spells;
    }

    public static void encode(SyncSpellsPacket pkt, PacketBuffer buf)
    {
        List<Integer> listSpells = new ArrayList<>();
        for (Spell spell : pkt.spells)
        {
            listSpells.add(Spell.getIdFromSpell(spell));
        }
        buf.writeVarIntArray(listSpells.stream().mapToInt(i -> i).toArray());
    }

    public static SyncSpellsPacket decode(PacketBuffer buf)
    {
        List<Spell> spells = new ArrayList<>();
        int[] spellsIds = buf.readVarIntArray();
        for (int id : spellsIds)
        {
            spells.add(Spell.getSpellById(id));
        }
        return new SyncSpellsPacket(spells);
    }

    public static class Handler
    {
        public static void handle(SyncSpellsPacket msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                ctx.get().enqueueWork(() ->
                {
                    Minecraft mc = Minecraft.getInstance();
                    ISpells spells = mc.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());
                    spells.setSpells(msg.spells);
                });
            }
            ctx.get().setPacketHandled(true);
        }
    }
}
