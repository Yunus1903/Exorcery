package com.yunus1903.exorcery.client.gui.widget;

import com.yunus1903.exorcery.client.gui.SpellSelectorGui;
import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellSelectorWidget extends Widget
{
    SpellSelectorGui gui;
    private Spell spell;

    public SpellSelectorWidget(int x, int y, Spell spell, SpellSelectorGui gui)
    {
        super(x, y, spell.getName().getString());
        this.gui = gui;
        this.spell = spell;
        setWidth((int) (48 * gui.scale));
        setHeight((int) (48 * gui.scale));
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
}
