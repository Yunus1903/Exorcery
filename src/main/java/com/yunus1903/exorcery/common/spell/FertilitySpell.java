package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.stream.Stream;

public class FertilitySpell extends Spell
{
    private final int RADIUS = 4;

    public FertilitySpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "fertility")
                .setManaCost(70F)
                .setCastTime(80)
                .setType(SpellType.NATURAL);
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        // TODO: Live mana calc
        // TODO: Animal Fertility

        BlockPos playerPos = player.getPosition();
        int x = playerPos.getX();
        int y = playerPos.getY();
        int z = playerPos.getZ();

        Stream<BlockPos> stream =  BlockPos.getAllInBox(x - RADIUS, y - 2, z - RADIUS, x + RADIUS, y + 3, z + RADIUS);
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

        return super.onSpellCast(world, player);
    }
}
