package com.yunus1903.exorcery.integration.jei;

import com.yunus1903.exorcery.common.ExorceryTags;
import com.yunus1903.exorcery.common.infusion.InfusionRecipeRegistry;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Yunus1903
 * @since 01/05/2020
 */
@OnlyIn(Dist.CLIENT)
@JeiPlugin
public class ExorceryJeiPlugin implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(Exorcery.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new InfusionRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        Collection<Item> items =  ExorceryTags.Items.SPELL_SCROLLS.getAllElements();

        Collection<Object> collection = new ArrayList<>();

        for (Item item : items)
        {
            List<ItemStack> right = new ArrayList<>();
            List<ItemStack> output = new ArrayList<>();
            right.add(new ItemStack(ExorceryItems.SPELL_SCROLL_EMPTY));
            output.add(new ItemStack(item));
            collection.add(registration.getVanillaRecipeFactory().createAnvilRecipe(new ItemStack(item), right, output));
        }

        registration.addRecipes(collection, VanillaRecipeCategoryUid.ANVIL);

        registration.addRecipes(InfusionRecipeRegistry.getRecipes(), InfusionRecipeCategory.UID);
    }
}
