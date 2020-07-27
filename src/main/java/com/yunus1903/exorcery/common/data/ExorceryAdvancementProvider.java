package com.yunus1903.exorcery.common.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunus1903.exorcery.common.ExorceryTags;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Yunus1903
 * @since 10/07/2020
 */
public class ExorceryAdvancementProvider implements IDataProvider
{
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public ExorceryAdvancementProvider(DataGenerator generatorIn)
    {
        this.generator = generatorIn;
    }

    protected void registerAdvancements(Consumer<Advancement> consumer)
    {
        Advancement root = register(consumer, new ResourceLocation(Exorcery.MOD_ID, "exorcery/root"), Advancement.Builder.builder()
                .withDisplay(ExorceryItems.SPELL_SCROLL_EMPTY,
                        new TranslationTextComponent("advancements.exorcery.root.title"),
                        new TranslationTextComponent("advancements.exorcery.root.description"),
                        new ResourceLocation("textures/block/andesite.png"),
                        FrameType.TASK,
                        true,
                        true,
                        false)
                .withCriterion("has_spell_scroll", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ExorceryTags.Items.SPELL_SCROLLS).build())));

        Advancement mana = register(consumer, new ResourceLocation(Exorcery.MOD_ID, "exorcery/mana"), Advancement.Builder.builder()
                .withParent(root)
                .withDisplay(ExorceryItems.MANA_POTION,
                        new TranslationTextComponent("advancements.exorcery.mana.title"),
                        new TranslationTextComponent("advancements.exorcery.mana.description"),
                        null,
                        FrameType.TASK,
                        false,
                        false,
                        false)
                .withCriterion("has_mana_potion", InventoryChangeTrigger.Instance.forItems(ExorceryItems.MANA_POTION)));
    }

    private Advancement register(Consumer<Advancement> consumer, ResourceLocation resourceLocation, Advancement.Builder builder)
    {
        return builder.register(consumer, resourceLocation.toString());
    }

    @Override
    public void act(DirectoryCache cache) throws IOException
    {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = advancement ->
        {
            if (!set.add(advancement.getId()))
            {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            }
            else
            {
                Path path1 = getPath(path, advancement);

                try
                {
                    IDataProvider.save(GSON, cache, advancement.copy().serialize(), path1);
                }
                catch (IOException ioexception)
                {
                    Exorcery.LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }
            }
        };

        registerAdvancements(consumer);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn)
    {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    @Override
    public String getName()
    {
        return "Exorcery Advancements";
    }
}
