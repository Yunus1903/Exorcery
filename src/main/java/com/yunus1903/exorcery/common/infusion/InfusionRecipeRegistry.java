package com.yunus1903.exorcery.common.infusion;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yunus1903
 * @since 10/07/2020
 */
public class InfusionRecipeRegistry
{
    private static List<IInfusionRecipe> recipes = new ArrayList<>();

    public static boolean addRecipe(ItemStack input, float manaCost, ItemStack output)
    {
        return addRecipe(new InfusionRecipe(input, manaCost, output));
    }

    public static boolean addRecipe(IInfusionRecipe recipe)
    {
        if (isValidInput(recipe.getInput()))
        {
            Exorcery.LOGGER.error("Recipe input already exists");
            return false;
        }
        return recipes.add(recipe);
    }

    public static boolean isValidInput(ItemStack input)
    {
        if (input.isEmpty()) return false;

        for (IInfusionRecipe recipe : recipes)
        {
            if (recipe.isInput(input)) return true;
        }
        return false;
    }

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

    public static List<IInfusionRecipe> getRecipes()
    {
        return Collections.unmodifiableList(recipes);
    }
}
