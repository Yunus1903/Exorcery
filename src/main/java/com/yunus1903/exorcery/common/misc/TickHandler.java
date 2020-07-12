package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncCastingPacket;
import com.yunus1903.exorcery.common.network.packets.SyncManaPacket;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;

import static net.minecraftforge.event.TickEvent.*;

/**
 * @author Yunus1903
 * @since 13/04/2020
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID)
public final class TickHandler
{
    private static final HashMap<Long, Runnable> tasks = new HashMap<>();
    private static final HashMap<Long, Runnable> loopTasks = new HashMap<>();

    public static void scheduleTask(long timeToRun, Runnable runnable)
    {
        tasks.put(timeToRun, runnable);
    }

    public static void scheduleLoopTask(long timeToRun, Runnable runnable)
    {
        loopTasks.put(timeToRun, runnable);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent event)
    {
        if (event.side.isServer()  && event.phase == Phase.START && Exorcery.instance.server != null)
        {
            MinecraftServer server = Exorcery.instance.server;

            try
            {
                for (HashMap.Entry<Long, Runnable> map : tasks.entrySet())
                {
                    if (map.getKey() <= server.getTickCounter())
                    {
                        tasks.remove(map.getKey()).run();
                    }
                }
            }
            catch (Exception e)
            {
                Exorcery.LOGGER.error(e.getMessage());
            }

            try
            {
                for (HashMap.Entry<Long, Runnable> map : loopTasks.entrySet())
                {
                    map.getValue().run();
                    if (map.getKey() <= server.getTickCounter())
                    {
                        loopTasks.remove(map.getKey());
                    }
                }
            }
            catch (Exception e)
            {
                Exorcery.LOGGER.error(e.getMessage());
            }

            for (ServerPlayerEntity player : server.getPlayerList().getPlayers())
            {
                player.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
                {
                    casting.tick();

                    if (casting.isCasting())
                    {
                        if (casting.getSpell().getType().getFrightensAnimals())
                        {
                            List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(8));

                            for (Entity entity : entities)
                            {
                                if (entity instanceof AnimalEntity)
                                {
                                    Vector3d pos = RandomPositionGenerator.findRandomTargetBlockAwayFrom((CreatureEntity) entity, 20, 4, player.getPositionVec());
                                    if (pos != null) ((AnimalEntity) entity).getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, 2.0D);
                                }
                            }
                        }

                        Vector3d prevPos = new Vector3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
                        if (!player.getPositionVec().equals(prevPos) && !casting.getSpell().getWhileRunning())
                        {
                            casting.stopCasting();
                            PacketHandler.sendToPlayer(player, new SyncCastingPacket(casting.isCasting(), casting.getSpell()));
                            SoundHandler.stopChanting(player);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event)
    {
        if (event.side.isServer())
        {
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(mana ->
            {
                if (mana.get() < mana.getMax() && player.server.getTickCounter() % 5 == 0 && !player.isCreative() && !player.isSpectator())
                {
                    mana.set(mana.get() + mana.getRegenerationRate());
                    PacketHandler.sendToPlayer(player, new SyncManaPacket(mana.get(), mana.getMax(), mana.getRegenerationRate()));
                }
            });
        }
    }
}
