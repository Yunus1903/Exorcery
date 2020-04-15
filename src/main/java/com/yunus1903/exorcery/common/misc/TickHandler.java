package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncCastingPacket;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID)
public final class TickHandler
{
    private static HashMap<Long, Runnable> schedule = new HashMap<>();

    public static void scheduleTask(long timeToRun, Runnable runnable)
    {
        schedule.put(timeToRun, runnable);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event)
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

            for (ServerPlayerEntity player : server.getPlayerList().getPlayers())
            {
                player.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
                {
                    if (casting.isCasting())
                    {
                        BlockPos prevPos = new BlockPos(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
                        if (!player.getPosition().equals(prevPos) && !casting.getSpell().getWhileRunning())
                        {
                            SoundHandler.stopChanting(player);
                            casting.stopCasting();
                            PacketHandler.sendToPlayer(player, new SyncCastingPacket(casting.isCasting(), casting.getSpell()));
                        }
                    }
                });
            }
        }
    }
}
