package com.yunus1903.exorcery.common.capabilities.spells;

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
 */
public class SpellsProvider implements ICapabilitySerializable<CompoundNBT>
{
    @CapabilityInject(ISpells.class)
    public static final Capability<ISpells> SPELLS_CAPABILITY = null;

    private ISpells instance = SPELLS_CAPABILITY.getDefaultInstance();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        return cap == SPELLS_CAPABILITY ? LazyOptional.of(() -> this.instance).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return (CompoundNBT) SPELLS_CAPABILITY.getStorage().writeNBT(SPELLS_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        SPELLS_CAPABILITY.getStorage().readNBT(SPELLS_CAPABILITY, this.instance, null, nbt);
    }
}
