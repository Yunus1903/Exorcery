package com.yunus1903.exorcery.client.screen;

import com.yunus1903.exorcery.client.screen.widget.SpellSelectorWidget;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.ClientProxy;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 */
@OnlyIn(Dist.CLIENT)
public class SpellSelectorScreen extends Screen
{
    private final int CENTER_OFFSET_STRAIGHT = 100;
    private final int CENTER_OFFSET_SIDE = 60;

    public float scale = 1.0F;

    public SpellSelectorScreen()
    {
        super(new StringTextComponent("Spell Selector")); // TODO: TranslationTextComponent
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_)
    {
        super.render(p_render_1_, p_render_2_, p_render_3_);
        for (Widget btn : buttons)
        {
            if (btn.isHovered()) btn.renderToolTip(p_render_1_, p_render_2_);
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

            scale = minecraft.getMainWindow().getScaledHeight() / 636.0F;

            for (int i = 0; i < spellList.size(); i++)
            {
                if (i >= 8) return;
                int x = minecraft.getMainWindow().getScaledWidth() / 2;
                int y = minecraft.getMainWindow().getScaledHeight() / 2;

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

                addButton(new SpellSelectorWidget(x, y, spellList.get(i), this));
            }
        });
    }

    @Override
    public void tick()
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
                if (btn instanceof SpellSelectorWidget && btn.isHovered())
                {
                    ((SpellSelectorWidget) btn).getSpell().castSpell(minecraft.world, minecraft.player);
                }
            }
            onClose();
        }
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
