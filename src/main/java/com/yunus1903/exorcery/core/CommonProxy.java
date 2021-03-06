package com.yunus1903.exorcery.core;

import com.yunus1903.exorcery.common.infusion.InfusionRecipeRegistry;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

/**
 * Proxy class
 * @author Yunus1903
 */
public class CommonProxy
{
    static
    {
        BrewingRecipeRegistry.addRecipe(
                Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.fromItems(Items.LAPIS_LAZULI),
                new ItemStack(ExorceryItems.MANA_POTION)
        );

        InfusionRecipeRegistry.addRecipe(
                new ItemStack(ExorceryItems.MANA_POTION),
                500,
                new ItemStack(ExorceryItems.MAGICAL_POTION)
        );
    }
}
