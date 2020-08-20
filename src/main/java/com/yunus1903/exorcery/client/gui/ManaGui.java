package com.yunus1903.exorcery.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Overlay GUI for displaying {@link com.yunus1903.exorcery.common.capabilities.mana.IMana mana}
 * @author Yunus1903
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Exorcery.MOD_ID)
public class ManaGui
{
    private static final ResourceLocation MANA_BAR_LOCATION = new ResourceLocation(Exorcery.MOD_ID, "textures/gui/mana_bar.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Pre event)
    {
        Minecraft mc = Minecraft.getInstance();

        switch (event.getType())
        {
            case ARMOR:
            case HEALTH:
            case FOOD:
            case AIR:
                if (!mc.player.isRidingHorse())
                {
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef(0, -8, 0);
                }
                break;
            case EXPERIENCE:
                if (!(mc.player.isCreative() || mc.player.isSpectator())) renderMana();
                break;
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event)
    {
        switch (event.getType())
        {
            case ARMOR:
            case HEALTH:
            case FOOD:
            case AIR:
                if (!Minecraft.getInstance().player.isRidingHorse()) GlStateManager.popMatrix();
                break;
        }
    }

    private static void renderMana()
    {
        Minecraft mc = Minecraft.getInstance();
        IngameGui gui = mc.ingameGUI;

        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();

        mc.player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(mana ->
        {
            mc.getProfiler().startSection("manaBar");
            mc.getTextureManager().bindTexture(MANA_BAR_LOCATION);

            int x = scaledWidth / 2 - 91;
            int y = scaledHeight - 32 + 3 - 8;

            int k = (int) (mana.get() / mana.getMax() * 183.0F);
            gui.blit(x, y, 0, 0, 182, 5);
            if (k > 0) gui.blit(x, y, 0, 5, k, 5);

            mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
            mc.getProfiler().endStartSection("manaNumber");

            String s = "" + (int) mana.get();
            x = (scaledWidth - mc.fontRenderer.getStringWidth(s)) / 2;
            y = scaledHeight - 31 - 4 - 8;
            mc.fontRenderer.drawString(s, (float) (x + 1), (float) y, 0);
            mc.fontRenderer.drawString(s, (float) (x - 1), (float) y, 0);
            mc.fontRenderer.drawString(s, (float) x, (float) (y + 1), 0);
            mc.fontRenderer.drawString(s, (float) x, (float) (y - 1), 0);
            mc.fontRenderer.drawString(s, (float) x, (float) y, 0x26EEEE);

            mc.getProfiler().endSection();
        });
    }
}
