package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.config.ExorceryConfig;
import com.yunus1903.exorcery.common.item.MagicalPotionItem;
import com.yunus1903.exorcery.common.item.ManaPotionItem;
import com.yunus1903.exorcery.common.item.SpellScrollItem;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mod {@link Item items} registry
 * @author Yunus1903
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ExorceryItems
{
    static
    {
        ExorceryConfig.SPELL.bake();
    }

    private static final List<Item> ITEMS = new ArrayList<>();
    public static final Item SPELL_SCROLL_EMPTY = register(new SpellScrollItem());
    public static final Item SPELL_SCROLL_TELEPORT = register(new SpellScrollItem(ExorcerySpells.TELEPORT));
    public static final Item SPELL_SCROLL_SPEED = register(new SpellScrollItem(ExorcerySpells.SPEED));
    public static final Item SPELL_SCROLL_FERTILITY = register(new SpellScrollItem(ExorcerySpells.FERTILITY));
    public static final Item SPELL_SCROLL_FIREBALL = register(new SpellScrollItem(ExorcerySpells.FIREBALL));
    public static final Item SPELL_SCROLL_TIME_WARP_DAY = register(new SpellScrollItem(ExorcerySpells.TIME_WARP_DAY));
    public static final Item SPELL_SCROLL_TIME_WARP_NIGHT = register(new SpellScrollItem(ExorcerySpells.TIME_WARP_NIGHT));
    public static final Item SPELL_SCROLL_FROST = register(new SpellScrollItem(ExorcerySpells.FROST));
    public static final Item SPELL_SCROLL_COUNTER = register(new SpellScrollItem(ExorcerySpells.COUNTER));
    public static final Item SPELL_SCROLL_INFUSION = register(new SpellScrollItem(ExorcerySpells.INFUSION));
    public static final Item SPELL_SCROLL_FLUID_WALK = register(new SpellScrollItem(ExorcerySpells.FLUID_WALK));
    public static final Item MANA_POTION = register(new ManaPotionItem(200));
    public static final Item MAGICAL_POTION = register(new MagicalPotionItem());

    private static Item register(Item item)
    {
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        for (Item item : ITEMS)
        {
            event.getRegistry().register(item);
        }
    }

    public static List<Item> getItems()
    {
        return Collections.unmodifiableList(ITEMS);
    }
}
