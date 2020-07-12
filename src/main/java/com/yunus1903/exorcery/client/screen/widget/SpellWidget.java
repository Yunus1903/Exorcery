package com.yunus1903.exorcery.client.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yunus1903.exorcery.client.screen.SpellSelectorScreen;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 * @since 12/04/2020
 */
@OnlyIn(Dist.CLIENT)
public class SpellWidget extends Widget
{
    SpellSelectorScreen gui;
    private Spell spell;

    public SpellWidget(int x, int y, Spell spell, SpellSelectorScreen gui)
    {
        super(x, y, (int) (52 * gui.scale), (int) (52 * gui.scale), spell.getName());
        this.gui = gui;
        this.spell = spell;
        this.x -= getWidth() / 2;
        this.y -= getHeight() / 2;
    }

    public Spell getSpell()
    {
        return spell;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_)
    {
        ResourceLocation textureLocation = new ResourceLocation(spell.getRegistryName().getNamespace(), "textures/spell/" + spell.getRegistryName().getPath() + ".png");
        gui.getMinecraft().getTextureManager().bindTexture(textureLocation);

        int hoverSize = 0;

        if (isHovered())
        {
            hoverSize = (int) (10 * gui.scale);
        }

        blit(matrixStack, x - hoverSize / 2, y - hoverSize / 2, getWidth() + hoverSize, getHeight() + hoverSize, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_)
    {
        Exorcery.keybindingHandler.removeKey(spell);
    }

    @Override
    protected boolean isValidClickButton(int p_isValidClickButton_1_)
    {
        return gui.keybindMode;
    }

    @Override
    public void renderToolTip(MatrixStack matrixStack, int p_renderToolTip_1_, int p_renderToolTip_2_)
    {
        List<ITextComponent> tooltip = new ArrayList<>();
        tooltip.add(getMessage().copyRaw().func_240699_a_(spell.getType().getColor()));
        tooltip.add(new StringTextComponent(""));
        tooltip.add(new TranslationTextComponent("gui.exorcery.tooltip.mana_cost").func_240699_a_(TextFormatting.GRAY).func_230529_a_(new StringTextComponent(": ").func_240699_a_(TextFormatting.GRAY)).func_230529_a_(spell.getManaCost() <= 0 ? new TranslationTextComponent("gui.exorcery.tooltip.mana_cost_free") : spell.getManaCost() == Float.MAX_VALUE ? new StringTextComponent(Character.toString('\u221e')) : new StringTextComponent(String.valueOf((int) spell.getManaCost()))));
        tooltip.add(new TranslationTextComponent("gui.exorcery.tooltip.cast_time").func_240699_a_(TextFormatting.GRAY).func_230529_a_(new StringTextComponent(": ").func_240699_a_(TextFormatting.GRAY)).func_230529_a_(spell.getCastTime() <= 0 ? new TranslationTextComponent("gui.exorcery.tooltip.cast_time_instant").func_240699_a_(TextFormatting.YELLOW) : new StringTextComponent(spell.getCastTime() / 20 + " ").func_230529_a_(new TranslationTextComponent("gui.exorcery.tooltip.cast_time_seconds"))));
        if (spell.getWhileRunning()) tooltip.add(new TranslationTextComponent("gui.exorcery.tooltip.while_running").func_240699_a_(TextFormatting.BLUE));
        InputMappings.Input key = Exorcery.keybindingHandler.getKey(spell);
        if (key != null) tooltip.add(new TranslationTextComponent("gui.exorcery.tooltip.keybinding").func_230529_a_(new StringTextComponent(": " + GLFW.glfwGetKeyName(key.getKeyCode(), key.getKeyCode()))).func_240699_a_(TextFormatting.YELLOW));
        else if (gui.keybindMode) tooltip.add(new TranslationTextComponent("gui.exorcery.tooltip.keybinding").func_230529_a_(new StringTextComponent(": ")).func_230529_a_(new TranslationTextComponent("gui.exorcery.tooltip.keybinding.none")).func_240699_a_(TextFormatting.YELLOW));
        if (InputMappings.isKeyDown(gui.getMinecraft().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(gui.getMinecraft().getMainWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT))
        {
            String translationKey = "spell." + spell.getRegistryName().getNamespace() + "." + spell.getRegistryName().getPath() + ".description";
            String line1 = I18n.format(translationKey + ".line1");
            String line2 = I18n.format(translationKey + ".line2");

            if (!line1.equals(translationKey + ".line1") || line1.isEmpty())
            {
                tooltip.add(new StringTextComponent(""));
                tooltip.add(new StringTextComponent(line1).func_240701_a_(TextFormatting.GRAY, TextFormatting.ITALIC));
                if (!line2.equals(translationKey + ".line2") || line2.isEmpty())
                    tooltip.add(new StringTextComponent(line2).func_240701_a_(TextFormatting.GRAY, TextFormatting.ITALIC));
            }
        }
        GuiUtils.drawHoveringText(matrixStack,
                tooltip,
                p_renderToolTip_1_,
                p_renderToolTip_2_,
                gui.getMinecraft().getMainWindow().getScaledWidth(),
                gui.getMinecraft().getMainWindow().getScaledHeight(),
                -1,
                gui.getMinecraft().fontRenderer
        );
    }
}
