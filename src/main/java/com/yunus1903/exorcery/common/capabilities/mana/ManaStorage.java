package com.yunus1903.exorcery.common.capabilities.mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * @author Yunus1903
 */
public class ManaStorage implements Capability.IStorage<IMana>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IMana> capability, IMana instance, Direction side)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("mana", FloatNBT.valueOf(instance.get()));
        compoundNBT.put("manaMax", FloatNBT.valueOf(instance.getMax()));
        compoundNBT.put("manaRegenRate", IntNBT.valueOf(instance.getRegenerationRate()));
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<IMana> capability, IMana instance, Direction side, INBT nbt)
    {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        instance.set(compoundNBT.getFloat("mana"));
        instance.setMax(compoundNBT.getFloat("manaMax"));
        instance.setRegenerationRate(compoundNBT.getInt("manaRegenRate"));
    }
}
