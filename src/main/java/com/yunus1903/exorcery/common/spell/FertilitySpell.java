package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Yunus1903
 * @since 18/04/2020
 */
public class FertilitySpell extends Spell
{
    public FertilitySpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "fertility")
                .setManaCost(SpellConfig.fertilityManaCost)
                .setCastTime(SpellConfig.fertilityCastTime)
                .setType(SpellType.NATURAL);
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        BlockPos playerPos = new BlockPos(player.getLookVec());
        int x = playerPos.getX();
        int y = playerPos.getY();
        int z = playerPos.getZ();

        final int radius = SpellConfig.fertilityRadius;

        Stream<BlockPos> stream =  BlockPos.getAllInBox(x - radius, y - 2, z - radius, x + radius, y + 3, z + radius);
        stream.forEach(pos ->
        {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.getBlock() instanceof IGrowable)
            {
                IGrowable block = (IGrowable) blockState.getBlock();
                if (block.canGrow(world, pos, blockState, world.isRemote()) && world instanceof ServerWorld)
                {
                    if (block.canUseBonemeal(world, world.rand, pos, blockState))
                    {
                        block.grow((ServerWorld) world, world.rand, pos, blockState);
                    }
                }
            }
        });

        List<Entity> entities =  world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(x - radius, y - 2, z - radius, x + radius, y + 3, z + radius), Entity::isAlive);

        for (Entity entity : entities)
        {
            if (entity instanceof AnimalEntity)
            {
                ((AnimalEntity) entity).setInLove(player);
            }
        }

        return super.onSpellCast(world, player);
    }
}
