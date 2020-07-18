package com.yunus1903.exorcery.common.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunus1903.exorcery.common.item.SpellScrollItem;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yunus1903
 * @since 11/07/2020
 */
public class ExorceryLootTableProvider extends LootTableProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final HashMap<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();

    private final DataGenerator generator;

    public ExorceryLootTableProvider(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

    protected void registerLootTables()
    {
        for (ResourceLocation resourceLocation : LootTables.func_215796_a()) // This is temporary
        {
            if (resourceLocation.getPath().contains("chests/"))
            {
                LootPool.Builder builder = LootPool.builder()
                        .name(Exorcery.MOD_ID)
                        .bonusRolls(0, 1);

                //for (Item item : ExorceryTags.Items.SPELL_SCROLLS.getAllElements()) // Breaks
                for (Item item : ExorceryItems.getItems()) // Temp fix
                {
                    if (item instanceof SpellScrollItem && ((SpellScrollItem) item).hasSpell()) // Temp fix
                    {
                        builder.addEntry(ItemLootEntry.builder(item));
                    }
                }

                getBuilder(new ResourceLocation(Exorcery.MOD_ID, "inject/" + resourceLocation.getPath())).addLootPool(builder);
            }
        }
    }

    private LootTable.Builder getBuilder(ResourceLocation resourceLocation)
    {
        LootTable.Builder builder = LootTable.builder();
        lootTables.put(resourceLocation, builder);
        return builder;
    }

    @Override
    public void act(DirectoryCache cache)
    {
        registerLootTables();

        HashMap<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<ResourceLocation, LootTable.Builder> entry : lootTables.entrySet())
        {
            tables.put(entry.getKey(), entry.getValue().build());
        }

        writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables)
    {
        Path outputFolder = generator.getOutputFolder();
        tables.forEach((key, lootTable) ->
        {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try
            {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            }
            catch (IOException e)
            {
                Exorcery.LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    @Override
    public String getName()
    {
        return "Exorcery " + super.getName();
    }
}
