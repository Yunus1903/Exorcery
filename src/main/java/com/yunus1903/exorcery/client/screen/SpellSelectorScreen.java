package com.yunus1903.exorcery.client.screen;

import com.yunus1903.exorcery.client.screen.widget.KeybindingsWidget;
import com.yunus1903.exorcery.client.screen.widget.SpellWidget;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.ClientProxy;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.glfw.GLFW;

import java.util.*;

/**
 * @author Yunus1903
 */
@OnlyIn(Dist.CLIENT)
public class SpellSelectorScreen extends Screen
{
    private final int CENTER_OFFSET_STRAIGHT = 100;
    private final int CENTER_OFFSET_SIDE = 60;

    public float scale = 1.0F;

    public boolean keybindMode = false;

    public SpellSelectorScreen()
    {
        super(new TranslationTextComponent("gui.exorcery.spell_selector"));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_)
    {
        super.render(p_render_1_, p_render_2_, p_render_3_);
        boolean isHovering = false;
        for (Widget btn : buttons)
        {
            if (btn.isHovered())
            {
                btn.renderToolTip(p_render_1_, p_render_2_);
                isHovering = true;
            }
        }

        int scaledWidth = minecraft.getMainWindow().getScaledWidth();
        int scaledHeight = minecraft.getMainWindow().getScaledHeight();

        if (keybindMode)
        {
            List<String> text = new ArrayList<>();
            text.add(TextFormatting.YELLOW + "" + TextFormatting.UNDERLINE + I18n.format("gui.exorcery.spell_selector.keybindings.info.line1"));
            text.add("");
            text.add(TextFormatting.YELLOW + I18n.format("gui.exorcery.spell_selector.keybindings.info.line2"));
            text.add("");
            text.add(I18n.format("gui.exorcery.spell_selector.keybindings.info.line3"));
            text.add("");
            text.add(TextFormatting.YELLOW + I18n.format("gui.exorcery.spell_selector.keybindings.info.line4"));

            GuiUtils.drawHoveringText(text, 4, scaledHeight - 103, scaledWidth, scaledHeight, 120, minecraft.fontRenderer);
        }
        else if (isHovering)
        {
            List<String> text = new ArrayList<>();
            text.add(I18n.format("gui.exorcery.spell_selector.spell_description"));
            GuiUtils.drawHoveringText(text, 4, scaledHeight - 23, scaledWidth, scaledHeight, 120, minecraft.fontRenderer);
        }
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return false;
    }

    @Override
    protected void init()
    {
        minecraft.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
        {
            List<Spell> spellList = new ArrayList<>();

            minecraft.player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(mana ->
            {
                for (Spell spell : spells.getSpells())
                {
                    if (mana.get() >= spell.getManaCost() || minecraft.player.isCreative())
                    {
                        spellList.add(spell);
                    }
                }
            });

            if (spellList.isEmpty())
            {
                minecraft.player.sendMessage(new TranslationTextComponent("chat.exorcery.no_mana"));
                return;
            }

            int scaledWidth = minecraft.getMainWindow().getScaledWidth();
            int scaledHeight = minecraft.getMainWindow().getScaledHeight();

            addButton(new KeybindingsWidget(scaledWidth - 90, scaledHeight - 30, this));

            scale = scaledHeight / 636.0F;

            for (int i = 0; i < spellList.size(); i++)
            {
                if (i >= 8) return;
                int x = scaledWidth / 2;
                int y = scaledHeight / 2;

                switch (i)
                {
                    case 0:
                        y -= CENTER_OFFSET_STRAIGHT * scale;
                        break;
                    case 1:
                        y += CENTER_OFFSET_STRAIGHT * scale;
                        break;
                    case 2:
                        x += CENTER_OFFSET_STRAIGHT * scale;
                        break;
                    case 3:
                        x -= CENTER_OFFSET_STRAIGHT * scale;
                        break;
                    case 4:
                        x += CENTER_OFFSET_SIDE * scale;
                        y -= CENTER_OFFSET_SIDE * scale;
                        break;
                    case 5:
                        x -= CENTER_OFFSET_SIDE * scale;
                        y += CENTER_OFFSET_SIDE * scale;
                        break;
                    case 6:
                        x -= CENTER_OFFSET_SIDE * scale;
                        y -= CENTER_OFFSET_SIDE * scale;
                        break;
                    case 7:
                        x += CENTER_OFFSET_SIDE * scale;
                        y += CENTER_OFFSET_SIDE * scale;
                        break;
                }

                addButton(new SpellWidget(x, y, spellList.get(i), this));
            }
        });
    }

    @Override
    public void tick()
    {
        if (keybindMode)
        {
            if (InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), GLFW.GLFW_KEY_ESCAPE))
            {
                keybindMode = false;
                onClose();
            }
        }
        else
        {
            if (InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), ClientProxy.KEY_SPELL_SELECTOR.getKey().getKeyCode()))
            {
                minecraft.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
                {
                    for (Spell spell : spells.getSpells())
                    {
                        spell.setTargetEntity(minecraft);
                        spell.setTargetLocation(minecraft);
                        spell.calculateManaCost(minecraft.player);
                    }
                });
            }
            else
            {
                for (Widget btn : buttons)
                {
                    if (btn instanceof SpellWidget && btn.isHovered())
                    {
                        ((SpellWidget) btn).getSpell().castSpell(minecraft.world, minecraft.player);
                    }
                }
                onClose();
            }
        }
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    public void onKeyPress(int keyCode)
    {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) return;
        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT) return;
        if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) return;
        if (GLFW.glfwGetKeyName(keyCode, GLFW.glfwGetKeyScancode(keyCode)) == null) return;

        for (KeyBinding key : minecraft.gameSettings.keyBindings)
        {
            if (keyCode == key.getKey().getKeyCode()) return;
        }

        for (Widget btn : buttons)
        {
            if (btn instanceof SpellWidget && btn.isHovered())
            {
                Spell spell = ((SpellWidget) btn).getSpell();
                Exorcery.keybindingHandler.setKey(spell, keyCode);
            }
        }
    }
}
