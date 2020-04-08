package com.yunus1903.exorcery.common.capabilities.mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaProvider implements ICapabilitySerializable<CompoundNBT>
{
    @CapabilityInject(IMana.class)
    public static final Capability<IMana> MANA_CAPABILITY = null;

    private IMana instance = MANA_CAPABILITY.getDefaultInstance();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        return cap == MANA_CAPABILITY ? LazyOptional.of(() -> this.instance).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return (CompoundNBT) MANA_CAPABILITY.getStorage().writeNBT(MANA_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        MANA_CAPABILITY.getStorage().readNBT(MANA_CAPABILITY, this.instance, null, nbt);
    }
}
