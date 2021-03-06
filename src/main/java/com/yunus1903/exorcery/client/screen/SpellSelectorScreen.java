package com.yunus1903.exorcery.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yunus1903.exorcery.client.screen.widget.KeybindingsWidget;
import com.yunus1903.exorcery.client.screen.widget.SpellWidget;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.ClientProxy;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Selector {@link Screen} for {@link Spell spells}
 * @author Yunus1903
 */
@OnlyIn(Dist.CLIENT)
public class SpellSelectorScreen extends Screen
{
    private final int CENTER_OFFSET_STRAIGHT = 100;
    private final int CENTER_OFFSET_SIDE = 60;

    public float scale = 1.0F;

    public boolean keybindingMode = false;

    public SpellSelectorScreen()
    {
        super(new TranslationTextComponent("gui.exorcery.spell_selector"));
    }

    @Override
    public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_)
    {
        if (minecraft == null) return;
        super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
        boolean isHovering = false;
        for (Widget btn : buttons)
        {
            if (btn.isHovered())
            {
                btn.renderToolTip(matrixStack, p_render_1_, p_render_2_);
                isHovering = true;
            }
        }

        int scaledWidth = minecraft.getMainWindow().getScaledWidth();
        int scaledHeight = minecraft.getMainWindow().getScaledHeight();

        if (keybindingMode)
        {
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.exorcery.spell_selector.keybindings.info.line1").mergeStyle(TextFormatting.YELLOW, TextFormatting.UNDERLINE));
            text.add(new StringTextComponent(" "));
            text.add(new TranslationTextComponent("gui.exorcery.spell_selector.keybindings.info.line2").mergeStyle(TextFormatting.YELLOW));
            text.add(new StringTextComponent(" "));
            text.add(new TranslationTextComponent("gui.exorcery.spell_selector.keybindings.info.line3"));
            text.add(new StringTextComponent(" "));
            text.add(new TranslationTextComponent("gui.exorcery.spell_selector.keybindings.info.line4").mergeStyle(TextFormatting.YELLOW));

            GuiUtils.drawHoveringText(matrixStack, text, 4, scaledHeight - 103, scaledWidth, scaledHeight, 120, minecraft.fontRenderer);
        }
        else if (isHovering)
        {
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.exorcery.spell_selector.spell_description"));
            GuiUtils.drawHoveringText(matrixStack, text, 4, scaledHeight - 23, scaledWidth, scaledHeight, 120, minecraft.fontRenderer);
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
        if (minecraft == null || minecraft.player == null) return;
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
                minecraft.player.sendMessage(new TranslationTextComponent("chat.exorcery.no_mana"), Util.DUMMY_UUID);
                return;
            }

            int scaledWidth = minecraft.getMainWindow().getScaledWidth();
            int scaledHeight = minecraft.getMainWindow().getScaledHeight();

            addButton(new KeybindingsWidget(scaledWidth - 90, scaledHeight - 30, this));

            scale = scaledHeight / 636.0F;

            for (int i = 0; i < spellList.size(); i++)
            {
                if (i >= 16) return;
                int x = scaledWidth / 2;
                int y = scaledHeight / 2;

                float offset = i >= 8 ? 1.7F : 1.0F;

                switch (i)
                {
                    case 0:
                    case 8:
                        y -= CENTER_OFFSET_STRAIGHT * scale * offset;
                        break;
                    case 1:
                    case 9:
                        y += CENTER_OFFSET_STRAIGHT * scale * offset;
                        break;
                    case 2:
                    case 10:
                        x += CENTER_OFFSET_STRAIGHT * scale * offset;
                        break;
                    case 3:
                    case 11:
                        x -= CENTER_OFFSET_STRAIGHT * scale * offset;
                        break;
                    case 4:
                    case 12:
                        x += CENTER_OFFSET_SIDE * scale * offset;
                        y -= CENTER_OFFSET_SIDE * scale * offset;
                        break;
                    case 5:
                    case 13:
                        x -= CENTER_OFFSET_SIDE * scale * offset;
                        y += CENTER_OFFSET_SIDE * scale * offset;
                        break;
                    case 6:
                    case 14:
                        x -= CENTER_OFFSET_SIDE * scale * offset;
                        y -= CENTER_OFFSET_SIDE * scale * offset;
                        break;
                    case 7:
                    case 15:
                        x += CENTER_OFFSET_SIDE * scale * offset;
                        y += CENTER_OFFSET_SIDE * scale * offset;
                        break;
                }

                addButton(new SpellWidget(x, y, spellList.get(i), this));
            }
        });
    }

    @Override
    public void tick()
    {
        if (minecraft == null || minecraft.player == null || minecraft.world == null) return;
        minecraft.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
        {
            for (Spell spell : spells.getSpells())
            {
                spell.setTarget(spell.determineTarget(minecraft));
                spell.calculateManaCost(minecraft.player);
            }
        });

        if (keybindingMode)
        {
            if (InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), GLFW.GLFW_KEY_ESCAPE))
            {
                keybindingMode = false;
                closeScreen();
            }
        }
        else
        {
            if (!InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), ClientProxy.KEY_SPELL_SELECTOR.getKey().getKeyCode()))
            {
                for (Widget btn : buttons)
                {
                    if (btn instanceof SpellWidget && btn.isHovered())
                    {
                        ((SpellWidget) btn).getSpell().castSpell(minecraft.world, minecraft.player, ((SpellWidget) btn).getSpell().getTarget());
                    }
                }
                closeScreen();
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
        if (minecraft == null) return;
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
