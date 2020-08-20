package com.yunus1903.exorcery.common.data;

import com.yunus1903.exorcery.common.item.SpellScrollItem;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;

/**
 * Mod {@link DataGenerator datagenerator} {@link net.minecraft.data.IDataProvider provider} for {@link ItemModelBuilder itemmodels}
 * @author Yunus1903
 * @since 09/07/2020
 */
public class ExorceryItemModelProvider extends ItemModelProvider
{
    public ExorceryItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, Exorcery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        registerSimpleItem(ExorceryItems.SPELL_SCROLL_EMPTY.getRegistryName().getPath());

        for (Item item : ExorceryItems.getItems())
        {
            if (item instanceof SpellScrollItem && ((SpellScrollItem) item).hasSpell())
            {
                getBuilder(item.getRegistryName().getPath())
                        .parent(getExistingFile(new ResourceLocation(Exorcery.MOD_ID, "item/" + ExorceryItems.SPELL_SCROLL_EMPTY.getRegistryName().getPath())))
                        .texture("layer0", new ResourceLocation(Exorcery.MOD_ID, "item/spell_scroll"));
            }
        }

        registerSimpleItem(ExorceryItems.MANA_POTION.getRegistryName().getPath())
                .texture("layer1", new ResourceLocation("minecraft", "item/potion"));

        registerSimpleItem(ExorceryItems.MAGICAL_POTION.getRegistryName().getPath())
                .texture("layer1", new ResourceLocation("minecraft", "item/potion"));
    }

    @Override
    public String getName()
    {
        return "Exorcery Item Models";
    }

    private ItemModelBuilder registerSimpleItem(String path)
    {
        return getBuilder(path)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(Exorcery.MOD_ID, "item/" + path));
    }
}
