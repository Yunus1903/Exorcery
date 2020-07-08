package com.yunus1903.exorcery.common.effect;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author Yunus1903
 * @since 30/06/2020
 */
public class FluidWalkEffect extends Effect
{
    public FluidWalkEffect()
    {
        super(EffectType.NEUTRAL, 0x4040FF);
        this.setRegistryName(Exorcery.MOD_ID, "fluid_walk");
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof PlayerEntity)
        {
            PlayerEntity p = (PlayerEntity) entityLivingBaseIn;

            if (!p.isSneaking() && !p.isElytraFlying() && !p.abilities.isFlying)
            {
                AxisAlignedBB bb = p.getBoundingBox().contract(0, 1, 0);

                if (p.world.containsAnyLiquid(p.isInLava() ? bb.offset(0, -0, 0) : bb))
                {
                    p.setMotion(p.getMotion().scale(0.5D).add(0.0D, 0.05D, 0.0D));

                    if (p.world.containsAnyLiquid(bb.offset(0, -1, 0)) && !p.world.containsAnyLiquid(bb.offset(0, 0.1, 0)))
                    {
                        p.setMotion(p.getMotion().x, 0, p.getMotion().z);
                    }

                    p.fallDistance = 0.0F;
                    p.onGround = true;
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
}
