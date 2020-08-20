package com.yunus1903.exorcery.integration.jei;

import com.yunus1903.exorcery.common.infusion.IInfusionRecipe;
import com.yunus1903.exorcery.core.Exorcery;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * JEI recipe category for {@link com.yunus1903.exorcery.common.infusion.InfusionRecipe InfusionRecipe}
 * @author Yunus1903
 * @since 10/07/2020
 */
@OnlyIn(Dist.CLIENT)
public class InfusionRecipeCategory implements IRecipeCategory<IInfusionRecipe>
{
    public static final ResourceLocation UID = new ResourceLocation(Exorcery.MOD_ID, "infusion");

    private final IGuiHelper guiHelper;
    private final IDrawable background;
    private final IDrawable icon;

    public InfusionRecipeCategory(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(Exorcery.MOD_ID, "textures/gui/jei_infusion.png"), 0, 0, 116, 54)
                .setTextureSize(116, 54)
                .build();
        icon = guiHelper.drawableBuilder(new ResourceLocation(Exorcery.MOD_ID, "textures/spell/infusion.png"), 0, 0, 16, 16)
                .setTextureSize(16, 16)
                .build();
    }

    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Override
    public Class<? extends IInfusionRecipe> getRecipeClass()
    {
        return IInfusionRecipe.class;
    }

    @Override
    public String getTitle()
    {
        return I18n.format("gui.jei.category.infusion");
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(IInfusionRecipe recipe, IIngredients ingredients)
    {
        ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IInfusionRecipe recipe, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();

        guiItemStackGroup.init(0, true, 12, 18);
        guiItemStackGroup.set(0, recipe.getInput());
        guiItemStackGroup.setBackground(0, guiHelper.getSlotDrawable());

        guiItemStackGroup.init(1, false, 86, 18);
        guiItemStackGroup.set(1, recipe.getOutput());
        guiItemStackGroup.setBackground(1, guiHelper.getSlotDrawable());
    }

    @Override
    public void draw(IInfusionRecipe recipe, double mouseX, double mouseY)
    {
        Minecraft mc = Minecraft.getInstance();

        String s = String.valueOf((int) recipe.getManaCost());
        int x = 40 + mc.fontRenderer.getStringWidth(s) / 2;
        int y = 40;

        mc.fontRenderer.drawString(s, (float) (x + 1), (float) y, 0);
        mc.fontRenderer.drawString(s, (float) (x - 1), (float) y, 0);
        mc.fontRenderer.drawString(s, (float) x, (float) (y + 1), 0);
        mc.fontRenderer.drawString(s, (float) x, (float) (y - 1), 0);
        mc.fontRenderer.drawString(s, (float) x, (float) y, 0x26EEEE);
    }
}
