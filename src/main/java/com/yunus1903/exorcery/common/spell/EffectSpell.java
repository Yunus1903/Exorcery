package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Yunus1903
 * @since 23/06/2020
 */
public class EffectSpell extends Spell
{
    private final Effect effect;
    private final int effectDuration;
    private final int effectAmplifier;
    private final float manaCostSelf;
    private final float manaCostOther;

    public EffectSpell(String name, Effect effect, int effectDuration, int effectAmplifier, float manaCost, int castTime, SpellType type)
    {
        this(name, effect, effectDuration, effectAmplifier, manaCost, manaCost, castTime, type);
    }

    public EffectSpell(String name, Effect effect, int effectDuration, int effectAmplifier, float manaCostSelf, float manaCostOther, int castTime, SpellType type)
    {
        this.effect = effect;
        this.effectDuration = effectDuration;
        this.effectAmplifier = effectAmplifier;
        this.manaCostSelf = manaCostSelf;
        this.manaCostOther = manaCostOther;
        this.setRegistryName(Exorcery.MOD_ID, name)
                .setManaCost(manaCostSelf)
                .setCastTime(castTime)
                .setType(type);
    }

    @Override
    public void calculateManaCost(PlayerEntity player)
    {
        if (targetEntity != null) setManaCost(manaCostOther);
        else setManaCost(manaCostSelf);
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
                player.addPotionEffect(new EffectInstance(effect, effectDuration, effectAmplifier));
            }
            else
            {
                targetEntity.addPotionEffect(new EffectInstance(effect, effectDuration, effectAmplifier));
            }
        }
        return super.onSpellCast(world, player, targetEntity, targetLocation);
    }
}
