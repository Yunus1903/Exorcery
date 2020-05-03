package com.yunus1903.exorcery.client.misc;

import com.yunus1903.exorcery.client.screen.SpellSelectorScreen;
import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncCastingPacket;
import com.yunus1903.exorcery.core.ClientProxy;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Yunus1903
 * @since 12/04/2020
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Exorcery.MOD_ID)
public final class ClientEventHandler
{
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (ClientProxy.KEY_SPELL_SELECTOR.isPressed())
        {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player.isSpectator()) return;
            mc.displayGuiScreen(new SpellSelectorScreen());
        }
    }

    @SubscribeEvent
    public static void onInitGui(GuiScreenEvent.InitGuiEvent.Pre event)
    {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player == null) return;

        player.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
        {
            if (casting.isCasting())
            {
                casting.stopCasting();
                PacketHandler.sendToServer(new SyncCastingPacket(casting.isCasting(), casting.getSpell()));
            }
        });
    }
}
