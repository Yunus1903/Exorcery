package com.yunus1903.exorcery.common.capabilities.spells;

import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 */
public class SpellsStorage implements Capability.IStorage<ISpells>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<ISpells> capability, ISpells instance, Direction side)
    {
        ListNBT listNBT = new ListNBT();
        for (Spell spell : instance.getSpells())
        {
            listNBT.add(IntNBT.valueOf(Spell.getIdFromSpell(spell)));
        }
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("spells", listNBT);
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<ISpells> capability, ISpells instance, Direction side, INBT nbt)
    {
        ListNBT listNBT =  (ListNBT) ((CompoundNBT) nbt).get("spells");
        if (listNBT.isEmpty() || listNBT.getInt(0) == -1) return;
        List<Spell> spells = new ArrayList<>();
        for (INBT id : listNBT)
        {
            spells.add(Spell.getSpellById(((IntNBT) id).getInt()));
        }
        instance.setSpells(spells);
    }
}
