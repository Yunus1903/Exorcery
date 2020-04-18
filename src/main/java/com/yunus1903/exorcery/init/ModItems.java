package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.item.SpellScrollItem;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModItems
{
    public static List<Item> items = new ArrayList<>();
    public static final Item SPELL_SCROLL_EMPTY = register(new SpellScrollItem());
    public static final Item SPELL_SCROLL_TELEPORT = register(new SpellScrollItem(ModSpells.TELEPORT));
    public static final Item SPELL_SCROLL_SPEED = register(new SpellScrollItem(ModSpells.SPEED));
    public static final Item SPELL_SCROLL_FERTILITY = register(new SpellScrollItem(ModSpells.FERTILITY));

    private static Item register(Item item)
    {
        items.add(item);
        return item;
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        for (Item item : items)
        {
            event.getRegistry().register(item);
        }
    }
}
