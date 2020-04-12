package com.yunus1903.exorcery.client.gui;

import com.yunus1903.exorcery.core.ClientProxy;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class GuiSpellSelector extends Screen
{
    private ResourceLocation texture = new ResourceLocation(Exorcery.MOD_ID, "textures/spells/spell_test.png");

    public GuiSpellSelector()
    {
        super(new StringTextComponent("Spell Selector"));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_)
    {
        minecraft.getTextureManager().bindTexture(new ResourceLocation("textures/item/slime_ball.png"));
        blit(minecraft.getMainWindow().getScaledWidth() / 2 - 128, minecraft.getMainWindow().getScaledHeight() / 2 - 128, 0, 0, 256, 256, 16, 16);
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return false;
    }

    @Override
    public void tick()
    {
        if (!InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), ClientProxy.KEY_SPELL_SELECTOR.getKey().getKeyCode()))
        {
            onClose();
        }
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
