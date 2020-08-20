package com.yunus1903.exorcery.common.infusion;

import net.minecraft.item.ItemStack;

/**
 * Interface for the infusion recipes
 * @author Yunus1903
 * @since 10/07/2020
 */
public interface IInfusionRecipe
{
    /**
     * Checks if the provided {@link ItemStack item} is a valid input
     * @param input The input {@link ItemStack}
     * @return {@code true} if the {@link ItemStack item} is valid as a input
     */
    boolean isInput(ItemStack input);

    /**
     * @return The input {@link ItemStack item}
     */
    ItemStack getInput();

    /**
     * @return The amount of mana needed to infuse
     */
    float getManaCost();

    /**
     * @return The output {@link ItemStack item}
     */
    ItemStack getOutput();
}
