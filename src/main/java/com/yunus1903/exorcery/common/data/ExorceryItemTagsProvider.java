package com.yunus1903.exorcery.common.data;

import com.yunus1903.exorcery.common.item.SpellScrollItem;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

/**
 * @author Yunus1903
 * @since 09/07/2020
 */
public class ExorceryItemTagsProvider extends ItemTagsProvider
{
    private static final Tag<Item> SPELL_SCROLLS = new ItemTags.Wrapper(new ResourceLocation(Exorcery.MOD_ID, "spell_scrolls"));

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
                getBuilder(SPELL_SCROLLS).add(item);
            }
        }
    }

    @Override
    public String getName()
    {
        return "Exorcery " + super.getName();
    }
}
