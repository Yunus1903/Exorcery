package com.yunus1903.exorcery.common.infusion;

import net.minecraft.item.ItemStack;

/**
 * @author Yunus1903
 * @since 10/07/2020
 */
public interface IInfusionRecipe
{
    boolean isInput(ItemStack input);

    ItemStack getInput();

    float getManaCost();

    ItemStack getOutput();
}
