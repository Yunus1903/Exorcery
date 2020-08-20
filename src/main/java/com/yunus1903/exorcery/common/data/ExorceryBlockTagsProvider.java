package com.yunus1903.exorcery.common.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;

/**
 * Mod {@link DataGenerator datagenerator} {@link net.minecraft.data.IDataProvider provider} for {@link net.minecraft.tags.BlockTags blocktags}
 * @author Yunus1903
 * @since 12/07/2020
 */
public class ExorceryBlockTagsProvider extends BlockTagsProvider
{
    public ExorceryBlockTagsProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerTags()
    {

    }

    @Override
    public String getName()
    {
        return "Exorcery " + super.getName();
    }
}
