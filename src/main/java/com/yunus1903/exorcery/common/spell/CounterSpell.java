package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.capabilities.casting.ICasting;
import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Yunus1903
 * @since 30/06/2020
 */
public class CounterSpell extends Spell
{
    public CounterSpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "counter")
                .setManaCost(SpellConfig.counterManaCost)
                .setCastTime(SpellConfig.counterCastTime)
                .setType(SpellType.NORMAL)
                .setWhileRunning(true);
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        BlockPos pos = player.getPosition();

        final int radius = SpellConfig.counterRadius;

        List<Entity> entities = world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(pos).grow(radius, 3, radius), Entity::isAlive);

        for (Entity entity : entities)
        {
            if (entity instanceof PlayerEntity)
            {
                entity.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(ICasting::stopCasting);
            }
        }

        return super.onSpellCast(world, player);
    }
}
