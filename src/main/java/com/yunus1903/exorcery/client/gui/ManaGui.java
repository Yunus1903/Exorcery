package com.yunus1903.exorcery.client.gui;

import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Yunus1903
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Exorcery.MOD_ID)
public class ManaGui
{
    private static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation(Exorcery.MOD_ID, "textures/gui/icons.png");

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        /*
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH)
        {
            mc.player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(mana ->
            {
                int manaVal = (int) (mana.get() / mana.getMax() * 20);
                int scaledWidth = mc.getMainWindow().getScaledWidth() / 2 - 91;

                int i2 = MathHelper.ceil(20 / 2.0F / 10.0F);
                i2 = (mc.player.getTotalArmorValue() > 0) ? i2 * 2 : i2;
                int scaledHeight = mc.getMainWindow().getScaledHeight() - 39 - (i2 - 1) * Math.max(10 - (i2 - 2), 3) - 10;

                mc.getProfiler().startSection("mana");

                for (int i = 0; i < 10; ++i)
                {
                    mc.ingameGUI.blit(scaledWidth + i * 8, scaledHeight, 16, 0, 9, 9);
                    mc.ingameGUI.blit(scaledWidth + i * 8, scaledHeight, 52, 0, 9, 9);
                }

                mc.getProfiler().endSection();
            });
        }
         */

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            mc.player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(mana ->
            {
                mc.fontRenderer.drawString("Mana: " + (int) mana.get() + " / " + (int) mana.getMax(), 20, mc.getMainWindow().getScaledHeight() - 25, 0);
            });
        }
    }
}
