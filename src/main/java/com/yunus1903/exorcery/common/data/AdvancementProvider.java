package com.yunus1903.exorcery.common.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Abstract class for a custom {@link Advancement advancement} {@link DataGenerator datagenerator}
 * @author Yunus1903
 * @since 10/07/2020
 */
public abstract class AdvancementProvider implements IDataProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;

    public AdvancementProvider(DataGenerator generatorIn)
    {
        this.generator = generatorIn;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException
    {
        Path path = generator.getOutputFolder();
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
                catch (IOException e)
                {
                    Exorcery.LOGGER.error("Couldn't save advancement {}", path1, e);
                }
            }
        };

        registerAdvancements(consumer);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn)
    {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    protected abstract void registerAdvancements(Consumer<Advancement> consumer);

    protected Advancement register(Consumer<Advancement> consumer, ResourceLocation resourceLocation, Advancement.Builder builder)
    {
        return builder.register(consumer, resourceLocation.toString());
    }

    @Override
    public String getName()
    {
        return "Exorcery Advancements";
    }
}
