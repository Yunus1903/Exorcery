package com.yunus1903.exorcery.common.data;

import com.yunus1903.exorcery.common.ExorceryTags;
import com.yunus1903.exorcery.common.item.SpellScrollItem;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;

/**
 * @author Yunus1903
 * @since 09/07/2020
 */
public class ExorceryItemTagsProvider extends ItemTagsProvider
{
    public ExorceryItemTagsProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerTags()
    {
        for (Item item : ExorceryItems.getItems())
        {
            if (item instanceof SpellScrollItem && ((SpellScrollItem) item).hasSpell())
            {
                getBuilder(ExorceryTags.Items.SPELL_SCROLLS).add(item);
            }
        }
    }

    @Override
    public String getName()
    {
        return "Exorcery " + super.getName();
    }
}
