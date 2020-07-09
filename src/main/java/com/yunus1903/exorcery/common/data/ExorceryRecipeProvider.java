package com.yunus1903.exorcery.common.data;

import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

/**
 * @author Yunus1903
 * @since 09/07/2020
 */
public class ExorceryRecipeProvider extends RecipeProvider
{
    public ExorceryRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shapedRecipe(ExorceryItems.SPELL_SCROLL_EMPTY)
                .key('P', Items.PAPER)
                .key('E', Items.EMERALD)
                .patternLine("PPP")
                .patternLine("PEP")
                .patternLine("PPP")
                .setGroup(ExorceryItems.SPELL_SCROLL_EMPTY.getRegistryName().toString())
                .addCriterion("has_paper", hasItem(Items.PAPER))
                .addCriterion("has_emerald", hasItem(Items.EMERALD))
                .build(consumer);
    }

    @Override
    public String getName()
    {
        return "Exorcery " + super.getName();
    }
}
