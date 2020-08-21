package com.yunus1903.exorcery.common.infusion;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registry for {@link InfusionRecipe}
 * @author Yunus1903
 * @since 10/07/2020
 */
public class InfusionRecipeRegistry
{
    private static final List<IInfusionRecipe> recipes = new ArrayList<>();

    /**
     * Register a {@link InfusionRecipe recipe}
     * @param input The input {@link ItemStack}
     * @param manaCost Amount of mana required to infuse the {@link ItemStack item}
     * @param output The {@link ItemStack} that's returned from the infusion
     * @return {@code true} if adding the {@link InfusionRecipe recipe} was successful
     */
    public static boolean addRecipe(ItemStack input, float manaCost, ItemStack output)
    {
        return addRecipe(new InfusionRecipe(input, manaCost, output));
    }

    /**
     * Register a {@link InfusionRecipe recipe}
     * @param recipe The recipe as a {@link InfusionRecipe}
     * @return {@code true} if adding the {@link InfusionRecipe recipe} was successful
     */
    public static boolean addRecipe(IInfusionRecipe recipe)
    {
        if (isValidInput(recipe.getInput()))
        {
            Exorcery.LOGGER.error("Recipe input already exists");
            return false;
        }
        return recipes.add(recipe);
    }

    /**
     * Checks if the {@link ItemStack item} is a valid input for a recipe
     * @param input The input {@link ItemStack} of the recipe
     * @return {@code true} if the {@link ItemStack item} is a valid input
     */
    public static boolean isValidInput(ItemStack input)
    {
        if (input.isEmpty()) return false;

        for (IInfusionRecipe recipe : recipes)
        {
            if (recipe.isInput(input)) return true;
        }
        return false;
    }

    /**
     * Gets the amount of mana required to infuse the specific {@link ItemStack item} according to it's recipe
     * @param input The input {@link ItemStack} of the recipe
     * @return The amount of mana required as a {@link Float float}
     */
    public static float getManaCost(ItemStack input)
    {
        if (isValidInput(input))
        {
            for (IInfusionRecipe recipe : recipes)
            {
                if (recipe.isInput(input)) return recipe.getManaCost();
            }
        }
        return Float.MAX_VALUE;
    }

    /**
     * Gets the recipe output according to the input {@link ItemStack}
     * @param input The input {@link ItemStack} of the recipe
     * @return The recipe output as a {@link ItemStack}
     */
    public static ItemStack getOutput(ItemStack input)
    {
        if (isValidInput(input))
        {
            for (IInfusionRecipe recipe : recipes)
            {
                if (recipe.isInput(input)) return recipe.getOutput();
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * @return All recipes
     */
    public static List<IInfusionRecipe> getRecipes()
    {
        return Collections.unmodifiableList(recipes);
    }
}
