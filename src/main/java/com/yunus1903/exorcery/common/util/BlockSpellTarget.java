package com.yunus1903.exorcery.common.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

/**
 * @author Yunus1903
 * @since 15/08/2020
 */
public class BlockSpellTarget extends SpellTarget
{
    private final Direction face;
    private final BlockPos pos;
    private final boolean isMiss;

    public BlockSpellTarget(@Nullable Direction face, @Nullable BlockPos pos)
    {
        this(false, face, pos);
    }

    public BlockSpellTarget(boolean isMiss, @Nullable Direction face, @Nullable BlockPos pos)
    {
        this.face = face;
        this.pos = pos;
        this.isMiss = isMiss;
    }

    @Nullable
    public BlockPos getPos()
    {
        return pos;
    }

    @Nullable
    public Direction getFace()
    {
        return face;
    }

    @Override
    public Type getType()
    {
        return isMiss ? Type.MISS : Type.BLOCK;
    }
}
