package com.yunus1903.exorcery.client.screen.widget;

import com.yunus1903.exorcery.client.screen.SpellSelectorScreen;
import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 * @since 12/04/2020
 */
@OnlyIn(Dist.CLIENT)
public class SpellSelectorWidget extends Widget
{
    SpellSelectorScreen gui;
    private Spell spell;

    public SpellSelectorWidget(int x, int y, Spell spell, SpellSelectorScreen gui)
    {
        super(x, y, spell.getName().getString());
        this.gui = gui;
        this.spell = spell;
        setWidth((int) (52 * gui.scale));
        setHeight((int) (52 * gui.scale));
        this.x -= getWidth() / 2;
        this.y -= getHeight() / 2;
    }

    public Spell getSpell()
    {
        return spell;
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_)
    {
        ResourceLocation textureLocation = new ResourceLocation(spell.getRegistryName().getNamespace(), "textures/spell/" + spell.getRegistryName().getPath() + ".png");
        gui.getMinecraft().getTextureManager().bindTexture(textureLocation);

        int hoverSize = 0;

        if (isHovered())
        {
            hoverSize = (int) (10 * gui.scale);
        }

        blit(x - hoverSize / 2, y - hoverSize / 2, getWidth() + hoverSize, getHeight() + hoverSize, 0, 0, 16, 16, 16, 16);
    }

    @Override
    protected boolean isValidClickButton(int p_isValidClickButton_1_)
    {
        return false;
    }

    @Override
    public void renderToolTip(int p_renderToolTip_1_, int p_renderToolTip_2_)
    {
        List<String> tooltip = new ArrayList<>();
        tooltip.add(getMessage());
        tooltip.add("");
        tooltip.add(I18n.format("gui.exorcery.tooltip.mana_cost") + ": " + (spell.getManaCost() <= 0 ? I18n.format("gui.exorcery.tooltip.mana_cost_free") : spell.getManaCost() == Float.MAX_VALUE ? Character.toString('\u221e') : (int) spell.getManaCost()));
        tooltip.add(I18n.format("gui.exorcery.tooltip.cast_time") + ": " + (spell.getCastTime() <= 0 ? I18n.format("gui.exorcery.tooltip.cast_time_instant") : spell.getCastTime() / 20 + " " + I18n.format("gui.exorcery.tooltip.cast_time_seconds")));
        GuiUtils.drawHoveringText(tooltip, p_renderToolTip_1_, p_renderToolTip_2_, gui.getMinecraft().getMainWindow().getScaledWidth(), gui.getMinecraft().getMainWindow().getScaledHeight(), -1, gui.getMinecraft().fontRenderer);
    }
}
