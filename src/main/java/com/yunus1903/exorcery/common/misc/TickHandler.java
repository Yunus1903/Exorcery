package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

public final class TickHandler
{
    private static HashMap<Long, Runnable> schedule = new HashMap<>();

    public static void scheduleTask(long timeToRun, Runnable runnable)
    {
        schedule.put(timeToRun, runnable);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START && Exorcery.instance.server != null)
        {
            MinecraftServer server = Exorcery.instance.server;

            for (HashMap.Entry<Long, Runnable> map : schedule.entrySet())
            {
                if (map.getKey() <= server.getTickCounter())
                {
                    schedule.remove(map.getKey()).run();
                }
            }
        }
    }
}
