package com.yunus1903.exorcery.integration.jei;

import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Yunus1903
 * @since 01/05/2020
 */
@JeiPlugin
public class ExorceryJeiPlugin implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(Exorcery.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        Collection<Item> items =  ItemTags.getCollection().getOrCreate(new ResourceLocation(Exorcery.MOD_ID, "spell_scrolls")).getAllElements();

        Collection<Object> collection = new ArrayList<>();

        for (Item item : items)
        {
            List<ItemStack> right = new ArrayList<>();
            List<ItemStack> output = new ArrayList<>();
            right.add(new ItemStack(ModItems.SPELL_SCROLL_EMPTY));
            output.add(new ItemStack(item));
            collection.add(registration.getVanillaRecipeFactory().createAnvilRecipe(new ItemStack(item), right, output));
        }

        registration.addRecipes(collection, VanillaRecipeCategoryUid.ANVIL);
    }


}
