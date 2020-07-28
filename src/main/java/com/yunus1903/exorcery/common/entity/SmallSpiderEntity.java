package com.yunus1903.exorcery.common.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author Yunus1903
 * @since 18/07/2020
 */
public class SmallSpiderEntity extends SpiderEntity
{
    public SmallSpiderEntity(EntityType<? extends SpiderEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute func_234277_m_()
    {
        return SpiderEntity.func_234305_eI_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 8.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5F);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag)
    {
        return spawnDataIn;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn)
    {
        return sizeIn.height * 0.7F;
    }
}
