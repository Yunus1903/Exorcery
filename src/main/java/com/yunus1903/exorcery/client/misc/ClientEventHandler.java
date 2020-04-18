package com.yunus1903.exorcery.client.misc;

import com.yunus1903.exorcery.client.gui.SpellSelectorGui;
import com.yunus1903.exorcery.core.ClientProxy;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
            mc.displayGuiScreen(new SpellSelectorGui());
        }
    }
}
