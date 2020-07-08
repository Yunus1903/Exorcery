package com.yunus1903.exorcery.common.capabilities.morph;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Yunus1903
 * @since 24/06/2020
 */
public class MorphProvider implements ICapabilitySerializable<CompoundNBT>
{
    @CapabilityInject(IMorph.class)
    public static final Capability<IMorph> MORPH_CAPABILITY = null;

    private IMorph instance = MORPH_CAPABILITY.getDefaultInstance();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return cap == MORPH_CAPABILITY ? LazyOptional.of(() -> this.instance).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return (CompoundNBT) MORPH_CAPABILITY.getStorage().writeNBT(MORPH_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        MORPH_CAPABILITY.getStorage().readNBT(MORPH_CAPABILITY, this.instance, null, nbt);
    }
}
