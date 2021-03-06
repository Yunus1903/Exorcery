package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Yunus1903
 * @since 24/06/2020
 */
public class FrostSpell extends Spell
{
    public FrostSpell()
    {
        super(new Properties()
                .castTime(SpellConfig.frostCastTime)
                .type(SpellType.ICE)
                .castableWhileRunning()
        );
        this.setRegistryName(Exorcery.MOD_ID, "frost");
        this.setManaCost(SpellConfig.frostManaCost);
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        Vector3d playerPos = player.getPositionVec();

        final int radius = SpellConfig.frostRadius;

        List<Entity> entities =  world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(playerPos.getX() - radius, playerPos.getY() - 2, playerPos.getZ() - radius, playerPos.getX() + radius, playerPos.getY() + 3, playerPos.getZ() + radius), Entity::isAlive);

        for (Entity entity : entities)
        {
            if (entity instanceof LivingEntity)
            {
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, SpellConfig.frostDuration, 10, false, false));
            }
        }

        return super.onSpellCast(world, player);
    }
}
