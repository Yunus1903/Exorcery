package com.yunus1903.exorcery.common.util;

import net.minecraft.entity.LivingEntity;

import javax.annotation.Nullable;

/**
 * {@link LivingEntity Entity} type {@link com.yunus1903.exorcery.common.spell.Spell spell} target
 * @author Yunus1903
 * @since 15/08/2020
 */
public class EntitySpellTarget extends SpellTarget
{
    private final LivingEntity entity;
    private final boolean isMiss;

    public EntitySpellTarget(@Nullable LivingEntity entity)
    {
        this(false, entity);
    }

    public EntitySpellTarget(boolean isMiss, @Nullable LivingEntity entity)
    {
        this.entity = entity;
        this.isMiss = isMiss;
    }

    @Nullable
    public LivingEntity getEntity()
    {
        return entity;
    }

    @Override
    public Type getType()
    {
        return isMiss ? Type.MISS : Type.ENTITY;
    }
}
