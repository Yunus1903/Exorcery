package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SStopSoundPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public final class SoundHandler
{
    public static final SoundEvent SPELL_CHANTING = new SoundEvent(new ResourceLocation(Exorcery.MOD_ID, "spell_chanting")).setRegistryName("spell_chanting");

    @SubscribeEvent
    public void onRegisterSound(RegistryEvent.Register<SoundEvent> event)
    {
        event.getRegistry().register(SPELL_CHANTING);
    }

    public static void stopChanting(ServerPlayerEntity player)
    {
        player.getServer().getPlayerList().sendToAllNearExcept(null, player.getPosX(), player.getPosY(), player.getPosZ(), 16, player.dimension, new SStopSoundPacket(SoundHandler.SPELL_CHANTING.getName(), SoundCategory.VOICE));
    }
}