package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.common.misc.TickHandler;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.ServerWorldInfo;

/**
 * @author Yunus1903
 * @since 03/05/2020
 */
public class TimeWarpSpell extends Spell
{
    private final int steps = SpellConfig.timeWarpSteps;

    private final int time;

    public TimeWarpSpell(String name, int time)
    {
        super(new Properties()
                .castTime(SpellConfig.timeWarpCastTime)
                .type(SpellType.NORMAL)
        );
        this.setRegistryName(Exorcery.MOD_ID, name);
        this.setManaCost(SpellConfig.timeWarpManaCost);
        this.time = time;
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        if (!world.isRemote())
        {
            int current = (int) world.getDayTime();
            int delta = current % 24000;
            int targetTime = current - delta + (delta >= time ? time + 24000 : time);
            int currentTicks = world.getServer().getTickCounter();
            int targetTicks = currentTicks + (targetTime - current) / steps;

            TickHandler.scheduleLoopTask(targetTicks, () ->
            {
                IWorldInfo worldInfo = world.getWorldInfo();
                if (worldInfo instanceof ServerWorldInfo)
                {
                    ((ServerWorldInfo) worldInfo).setDayTime(world.getDayTime() + steps);
                }
            });
        }
        return super.onSpellCast(world, player);
    }
}
