package com.yunus1903.exorcery.common.capabilities.morph;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yunus1903
 * @since 24/06/2020
 */
public class MorphCapability implements IMorph
{
    private HashMap<LivingEntity, EntityType<? extends LivingEntity>> morphedEntities = new HashMap<>();

    @Override
    public boolean isMorphed(LivingEntity entity)
    {
        return morphedEntities.containsKey(entity);
    }

    @Override
    public HashMap<LivingEntity, EntityType<? extends LivingEntity>> getMorphedEntities()
    {
        return new HashMap<>(Collections.unmodifiableMap(morphedEntities));
    }

    @Override
    public void setMorphedEntities(HashMap<LivingEntity, EntityType<? extends LivingEntity>> morphedEntities)
    {
        HashMap<LivingEntity, EntityType<? extends LivingEntity>> buf = getMorphedEntities();
        this.morphedEntities = morphedEntities;
        for (Map.Entry<LivingEntity, EntityType<? extends LivingEntity>> entry : buf.entrySet())
        {
            entry.getKey().recalculateSize();
        }
    }

    @Nullable
    @Override
    public EntityType<? extends LivingEntity> getMorphedEntityType(LivingEntity entity)
    {
        return morphedEntities.get(entity);
    }

    @Override
    public void morph(LivingEntity entity, EntityType<? extends LivingEntity> entityType)
    {
        if (morphedEntities.containsKey(entity)) morphedEntities.replace(entity, entityType);
        else morphedEntities.put(entity, entityType);
        entity.recalculateSize();
    }

    @Override
    public void stopMorph(LivingEntity entity)
    {
        morphedEntities.remove(entity);
        entity.recalculateSize();
    }
}
