package com.yunus1903.exorcery.common.capabilities.casting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CastingProvider implements ICapabilitySerializable<CompoundNBT>
{
    @CapabilityInject(ICasting.class)
    public static final Capability<ICasting> CASTING_CAPABILITY = null;

    private ICasting instance = CASTING_CAPABILITY.getDefaultInstance();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return cap == CASTING_CAPABILITY ? LazyOptional.of(() -> this.instance).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return (CompoundNBT) CASTING_CAPABILITY.getStorage().writeNBT(CASTING_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        CASTING_CAPABILITY.getStorage().readNBT(CASTING_CAPABILITY, this.instance, null, nbt);
    }
}
