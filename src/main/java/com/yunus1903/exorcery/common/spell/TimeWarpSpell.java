package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.common.misc.TickHandler;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

/**
 * @author Yunus1903
 * @since 03/05/2020
 */
public class TimeWarpSpell extends Spell
{
    private final int STEPS = SpellConfig.timeWarpSteps;

    private final int TIME;

    public TimeWarpSpell(String name, int time)
    {
        this.setRegistryName(Exorcery.MOD_ID, name)
                .setManaCost(SpellConfig.timeWarpManaCost)
                .setCastTime(SpellConfig.timeWarpCastTime)
                .setType(SpellType.NORMAL); // TODO: revisit
        this.TIME = time;
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        if (!world.isRemote())
        {
            int current = (int) world.getDayTime();
            int delta = current % 24000;
            int targetTime = current - delta + (delta >= TIME ? TIME + 24000 : TIME);
            int currentTicks = world.getServer().getTickCounter();
            int targetTicks = currentTicks + (targetTime - current) / STEPS;

            TickHandler.scheduleLoopTask(targetTicks, () ->
            {
                world.setDayTime(world.getDayTime() + STEPS);
            });
        }
        return super.onSpellCast(world, player);
    }
}
