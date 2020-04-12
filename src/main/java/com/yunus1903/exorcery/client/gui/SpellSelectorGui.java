package com.yunus1903.exorcery.client.gui;

import com.yunus1903.exorcery.client.gui.widget.SpellSelectorWidget;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.core.ClientProxy;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellSelectorGui extends Screen
{
    private final int CENTER_OFFSET_STRAIGHT = 100;
    private final int CENTER_OFFSET_SIDE = 60;

    public float scale = 1.0F;

    public SpellSelectorGui()
    {
        super(new StringTextComponent("Spell Selector"));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_)
    {
        super.render(p_render_1_, p_render_2_, p_render_3_);
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
            int numberOfSpells = spells.getSpells().size();
            if (numberOfSpells > 8) return;

            scale = minecraft.getMainWindow().getScaledHeight() / 636.0F;

            for (int i = 0; i < numberOfSpells; i++)
            {
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

                addButton(new SpellSelectorWidget(x, y, spells.getSpells().get(i), this));
            }
        });
    }

    @Override
    public void tick()
    {
        if (!InputMappings.isKeyDown(minecraft.getMainWindow().getHandle(), ClientProxy.KEY_SPELL_SELECTOR.getKey().getKeyCode()))
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
