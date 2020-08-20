package com.yunus1903.exorcery.common.network;

import com.yunus1903.exorcery.common.network.packets.*;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * Handler class to register and send custom network packets
 * @author Yunus1903
 */
public final class PacketHandler
{
    private static final String PROTOCOL = Integer.toString(1);
    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Exorcery.MOD_ID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL::equals)
            .serverAcceptedVersions(PROTOCOL::equals)
            .networkProtocolVersion(() -> PROTOCOL)
            .simpleChannel();

    public static void register()
    {
        int id = 0;

        HANDLER.registerMessage(id++, SyncSpellsPacket.class, SyncSpellsPacket::encode, SyncSpellsPacket::decode, SyncSpellsPacket.Handler::handle);
        HANDLER.registerMessage(id++, CastSpellPacket.class, CastSpellPacket::encode, CastSpellPacket::decode, CastSpellPacket.Handler::handle);
        HANDLER.registerMessage(id++, SyncManaPacket.class, SyncManaPacket::encode, SyncManaPacket::decode, SyncManaPacket.Handler::handle);
        HANDLER.registerMessage(id++, SyncCastingPacket.class, SyncCastingPacket::encode, SyncCastingPacket::decode, SyncCastingPacket.Handler::handle);
        HANDLER.registerMessage(id++, SyncMorphPacket.class, SyncMorphPacket::encode, SyncMorphPacket::decode, SyncMorphPacket.Handler::handle);
        HANDLER.registerMessage(id++, MagicalPotionEntityInteractPacket.class, MagicalPotionEntityInteractPacket::encode, MagicalPotionEntityInteractPacket::decode, MagicalPotionEntityInteractPacket.Handler::handle);
    }

    public static void sendToServer(Object msg)
    {
        HANDLER.sendToServer(msg);
    }

    public static void sendToPlayer(ServerPlayerEntity player, Object msg)
    {
        if (!(player instanceof FakePlayer))
        {
            HANDLER.sendTo(msg, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToNearby(World world, BlockPos pos, Object msg)
    {
        if(world instanceof ServerWorld)
        {
            ServerWorld serverWorld = (ServerWorld) world;

            serverWorld.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
                    .filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                    .forEach(p -> HANDLER.send(PacketDistributor.PLAYER.with(() -> p), msg));
        }
    }

    public static void sendToNearby(World world, Entity entity, Object msg)
    {
        sendToNearby(world, new BlockPos(entity), msg);
    }

}
