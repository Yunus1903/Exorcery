package com.yunus1903.exorcery.common.data;

import com.yunus1903.exorcery.common.ExorceryTags;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;

import java.util.function.Consumer;

/**
 * Mod {@link DataGenerator datagenerator} {@link net.minecraft.data.IDataProvider provider} for {@link IFinishedRecipe recipes}
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

        ShapelessRecipeBuilder.shapelessRecipe(ExorceryItems.SPELL_SCROLL_EMPTY)
                .addIngredient(ExorceryTags.Items.SPELL_SCROLLS)
                .setGroup(ExorceryItems.SPELL_SCROLL_EMPTY.getRegistryName().toString())
                .addCriterion("has_spell_scroll", hasItem(ExorceryTags.Items.SPELL_SCROLLS))
                .build(consumer, ExorceryItems.SPELL_SCROLL_EMPTY.getRegistryName().toString() + "_spell_scrolls_tag");
    }

    @Override
    public String getName()
    {
        return "Exorcery " + super.getName();
    }
}
