package com.yunus1903.exorcery.common.util;

/**
 * Abstract class for {@link com.yunus1903.exorcery.common.spell.Spell spell} target
 * @author Yunus1903
 * @since 15/08/2020
 */
public abstract class SpellTarget
{
    public enum Type
    {
        MISS,
        BLOCK,
        ENTITY
    }

    public abstract Type getType();
}
