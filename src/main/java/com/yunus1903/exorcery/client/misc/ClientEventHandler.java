package com.yunus1903.exorcery.client.misc;

import com.yunus1903.exorcery.client.gui.GuiSpellSelector;
import com.yunus1903.exorcery.core.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler
{
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (ClientProxy.KEY_SPELL_SELECTOR.isPressed())
        {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player.isSpectator()) return;
            mc.displayGuiScreen(new GuiSpellSelector());
        }
    }
}
