package com.yunus1903.exorcery.common.infusion;

import net.minecraft.item.ItemStack;

/**
 * Custom recipe for {@link com.yunus1903.exorcery.common.spell.InfusionSpell InfusionSpell}
 * @author Yunus1903
 * @since 10/07/2020
 */
public class InfusionRecipe implements IInfusionRecipe
{
    private final ItemStack input;
    private final float manaCost;
    private final ItemStack output;

    /**
     * {@link InfusionRecipe} constructor
     * @param input The input {@link ItemStack}
     * @param manaCost Amount of mana required to infuse the {@link ItemStack item}
     * @param output The {@link ItemStack} that's returned from the infusion
     */
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
        return input.copy();
    }

    @Override
    public float getManaCost()
    {
        return manaCost;
    }

    @Override
    public ItemStack getOutput()
    {
        return output.copy();
    }
}
