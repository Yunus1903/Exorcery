package com.yunus1903.exorcery.common.capabilities.casting;

import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * @author Yunus1903
 * @since 14/04/2020
 */
public class CastingStorage implements Capability.IStorage<ICasting>
{

    @Nullable
    @Override
    public INBT writeNBT(Capability<ICasting> capability, ICasting instance, Direction side)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("isCasting", ByteNBT.valueOf(instance.isCasting()));
        compoundNBT.put("spell", IntNBT.valueOf(Spell.getIdFromSpell(instance.getSpell())));
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<ICasting> capability, ICasting instance, Direction side, INBT nbt)
    {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        Spell spell = null;

        if (compoundNBT.getBoolean("isCasting"))
        {
            spell = Spell.getSpellById(compoundNBT.getInt("spell"));
        }

        instance.startCasting(spell);
    }
}
