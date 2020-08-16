package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.util.EntitySpellTarget;
import com.yunus1903.exorcery.common.util.SpellTarget;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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
        super(new Properties()
                .castTime(castTime)
                .type(type)
        );
        this.effect = effect;
        this.effectDuration = effectDuration;
        this.effectAmplifier = effectAmplifier;
        this.manaCostSelf = manaCostSelf;
        this.manaCostOther = manaCostOther;
        this.setRegistryName(Exorcery.MOD_ID, name);
        this.setManaCost(manaCostSelf);
    }

    @Override
    public void calculateManaCost(PlayerEntity player)
    {
        if (getTarget() != null && getTarget().getType() == SpellTarget.Type.ENTITY) setManaCost(manaCostOther);
        else setManaCost(manaCostSelf);
    }

    @Nullable
    @Override
    public SpellTarget determineTarget(Minecraft mc)
    {
        RayTraceResult result = mc.objectMouseOver;
        if (result.getType() == RayTraceResult.Type.ENTITY && result instanceof EntityRayTraceResult && ((EntityRayTraceResult) result).getEntity() instanceof LivingEntity)
        {
            return new EntitySpellTarget((LivingEntity) ((EntityRayTraceResult) result).getEntity());
        }
        else return new EntitySpellTarget(true, null);
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player, SpellTarget target)
    {
        if (!world.isRemote())
        {
            if (target == null || target.getType() != SpellTarget.Type.ENTITY)
            {
                player.addPotionEffect(new EffectInstance(effect, effectDuration, effectAmplifier));
            }
            else
            {
                ((EntitySpellTarget) target).getEntity().addPotionEffect(new EffectInstance(effect, effectDuration, effectAmplifier));
            }
        }
        return super.onSpellCast(world, player, target);
    }
}
