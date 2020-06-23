package com.yunus1903.exorcery.client.screen.widget;

import com.yunus1903.exorcery.client.screen.SpellSelectorScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Yunus1903
 * @since 14/05/2020
 */
@OnlyIn(Dist.CLIENT)
public class KeybindingsWidget extends Widget
{
    SpellSelectorScreen gui;

    public KeybindingsWidget(int xIn, int yIn, SpellSelectorScreen gui)
    {
        super(xIn, yIn, (new TranslationTextComponent("gui.exorcery.spell_selector.keybindings").getString()));
        this.gui = gui;
        setWidth(80);
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_)
    {
        gui.keybindMode = !gui.keybindMode;

        if (gui.keybindMode) setMessage((new TranslationTextComponent("gui.exorcery.spell_selector.keybindings.close").getString()));
        else setMessage((new TranslationTextComponent("gui.exorcery.spell_selector.keybindings").getString()));
    }
}