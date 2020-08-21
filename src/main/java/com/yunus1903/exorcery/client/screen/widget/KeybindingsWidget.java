package com.yunus1903.exorcery.client.screen.widget;

import com.yunus1903.exorcery.client.screen.SpellSelectorScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * {@link SpellSelectorScreen} {@link Widget widget} for setting {@link com.yunus1903.exorcery.client.misc.KeybindingHandler keybinding} mode
 * @author Yunus1903
 * @since 14/05/2020
 */
@OnlyIn(Dist.CLIENT)
public class KeybindingsWidget extends Widget
{
    private final SpellSelectorScreen gui;

    public KeybindingsWidget(int xIn, int yIn, SpellSelectorScreen gui)
    {
        super(xIn, yIn, 80, 20, new TranslationTextComponent("gui.exorcery.spell_selector.keybindings"));
        this.gui = gui;
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_)
    {
        gui.keybindingMode = !gui.keybindingMode;

        if (gui.keybindingMode) setMessage(new TranslationTextComponent("gui.exorcery.spell_selector.keybindings.close"));
        else setMessage(new TranslationTextComponent("gui.exorcery.spell_selector.keybindings"));
    }
}
