package com.yunus1903.exorcery.common.util;

/**
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
