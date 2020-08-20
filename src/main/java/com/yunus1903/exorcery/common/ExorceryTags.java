package com.yunus1903.exorcery.common;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

/**
 * Mod {@link net.minecraft.tags.Tag tags}
 * @author Yunus1903
 * @since 09/07/2020
 */
public final class ExorceryTags
{
    public static class Items
    {
        public static final ITag.INamedTag<Item> SPELL_SCROLLS = tag("spell_scrolls");

        private static ITag.INamedTag<Item> tag(String name)
        {
            return ItemTags.makeWrapperTag(new ResourceLocation(Exorcery.MOD_ID, name).toString());
        }
    }
}
