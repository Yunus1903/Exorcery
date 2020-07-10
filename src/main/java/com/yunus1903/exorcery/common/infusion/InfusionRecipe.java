package com.yunus1903.exorcery.common.infusion;

import net.minecraft.item.ItemStack;

/**
 * @author Yunus1903
 * @since 10/07/2020
 */
public class InfusionRecipe implements IInfusionRecipe
{
    private final ItemStack input;
    private final float manaCost;
    private final ItemStack output;

    public InfusionRecipe(ItemStack input, float manaCost, ItemStack output)
    {
        this.input = input;
        this.manaCost = manaCost;
        this.output = output;
    }

    @Override
    public boolean isInput(ItemStack input)
    {
        return this.input.getItem() == input.getItem();
    }

    @Override
    public ItemStack getInput()
    {
        return input;
    }

    @Override
    public float getManaCost()
    {
        return manaCost;
    }

    @Override
    public ItemStack getOutput()
    {
        return output;
    }
}
