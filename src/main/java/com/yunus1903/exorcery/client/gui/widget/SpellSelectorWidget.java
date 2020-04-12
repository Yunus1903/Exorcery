package com.yunus1903.exorcery.client.gui.widget;

import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellSelectorWidget extends Widget
{
    private Spell spell;
    private float scale;

    public SpellSelectorWidget(int x, int y, Spell spell)
    {
        this(x, y, spell, 1);
    }

    public SpellSelectorWidget(int x, int y, Spell spell, float scale)
    {
        super(x, y, spell.getName().getString());
        this.spell = spell;
        this.scale = scale;
        setWidth((int) (48 * scale));
        setHeight((int) (48 * scale));
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
        Minecraft.getInstance().getTextureManager().bindTexture(textureLocation);

        int size = (int) (48 * scale);

        blit(x, y, size, size, 0, 0, 16, 16, 16, 16);
    }
}
