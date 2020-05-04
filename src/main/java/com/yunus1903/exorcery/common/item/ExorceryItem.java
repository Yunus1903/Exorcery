package com.yunus1903.exorcery.common.item;

import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * @author Yunus1903
 * @since 04/05/2020
 */
public abstract class ExorceryItem extends Item
{
    private static final ItemGroup EXORCERY = new ItemGroup("exorcery")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ExorceryItems.SPELL_SCROLL_TELEPORT);
        }
    };

    public ExorceryItem(String name)
    {
        this(name, new Item.Properties());
    }

    public ExorceryItem(String name, Properties properties)
    {
        super(properties.group(EXORCERY));
        setRegistryName(Exorcery.MOD_ID, name);
    }
}
