package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Yunus1903
 * @since 13/04/2020
 */
public class SpeedSpell extends Spell
{
    private final float DEFAULT_MANA_COST = 50F;

    public SpeedSpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "speed")
                .setManaCost(DEFAULT_MANA_COST)
                .setCastTime(60)
                .setType(SpellType.NORMAL);
    }

    @Override
    public void calculateManaCost(PlayerEntity player)
    {
        if (targetEntity != null) setManaCost(DEFAULT_MANA_COST * 2);
        else setManaCost(DEFAULT_MANA_COST);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setTargetEntity(Minecraft mc)
    {
        RayTraceResult result = mc.objectMouseOver;
        if (result.getType() == RayTraceResult.Type.ENTITY && result instanceof EntityRayTraceResult && ((EntityRayTraceResult) result).getEntity() instanceof LivingEntity)
        {
            targetEntity = (LivingEntity) ((EntityRayTraceResult) result).getEntity();
        }
        else targetEntity = null;
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player, LivingEntity targetEntity, BlockPos targetLocation)
    {
        if (!world.isRemote())
        {
            if (targetEntity == null)
            {
                player.addPotionEffect(new EffectInstance(Effects.SPEED, 400, 2));
            }
            else
            {
                targetEntity.addPotionEffect(new EffectInstance(Effects.SPEED, 400, 2));
            }
        }
        return super.onSpellCast(world, player);
    }
}
