package com.yunus1903.exorcery.common.capabilities.morph;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * @author Yunus1903
 * @since 24/06/2020
 */
public interface IMorph
{
    boolean isMorphed(LivingEntity entity);

    HashMap<LivingEntity, EntityType<? extends LivingEntity>> getMorphedEntities();

    void setMorphedEntities(HashMap<LivingEntity, EntityType<? extends LivingEntity>> morphedEntities);

    @Nullable
    EntityType<? extends LivingEntity> getMorphedEntityType(LivingEntity entity);

    void morph(LivingEntity entity, EntityType<? extends LivingEntity> entityType);

    void stopMorph(LivingEntity entity);
}
