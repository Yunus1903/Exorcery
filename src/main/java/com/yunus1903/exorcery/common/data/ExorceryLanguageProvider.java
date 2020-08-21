package com.yunus1903.exorcery.common.data;

import com.yunus1903.exorcery.common.effect.PolymorphEffect;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryEffects;
import com.yunus1903.exorcery.init.ExorceryItems;
import com.yunus1903.exorcery.init.ExorceryPotions;
import com.yunus1903.exorcery.init.ExorcerySpells;
import net.minecraft.client.resources.I18n;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Mod {@link DataGenerator datagenerator} {@link net.minecraft.data.IDataProvider provider} for localization
 * @author Yunus1903
 * @since 05/08/2020
 */
public class ExorceryLanguageProvider extends LanguageProvider
{
    public ExorceryLanguageProvider(DataGenerator generatorIn)
    {
        super(generatorIn, Exorcery.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        addItems();
        addSpells();
        addEntities();
        addEffects();
        addPotions();
        addGuis();
        addAdvancements();
        addConfigs();
        addMisc();
    }

    @Override
    public String getName()
    {
        return "Exorcery " + super.getName();
    }

    private void addItems()
    {
        add("item.exorcery.spell_scroll", "Spell Scroll");
        add(ExorceryItems.SPELL_SCROLL_EMPTY, "Empty Spell Scroll");
        add(ExorceryItems.MANA_POTION, "Mana Potion");
        add(ExorceryItems.MAGICAL_POTION, "Magical Potion", "Right click on a entity to", "create a polymorph potion");
    }

    private void addSpells()
    {
        add(ExorcerySpells.TELEPORT, "Teleport", "Teleport to the block", "you're looking at");
        add(ExorcerySpells.SPEED, "Speed", "Give speed effect");
        add(ExorcerySpells.FERTILITY, "Fertility", "Fertilize your", "surroundings");
        add(ExorcerySpells.FIREBALL, "Fireball", "Shoot a fireball");
        add(ExorcerySpells.TIME_WARP_DAY, "Time Warp Day", "Warp time to daytime");
        add(ExorcerySpells.TIME_WARP_NIGHT, "Time Warp Night", "Warp time to nighttime");
        add(ExorcerySpells.FROST, "Frost", "Freeze surrounding entities");
        add(ExorcerySpells.COUNTER, "Counter", "Stop surrounding players", "from casting a spell");
        add(ExorcerySpells.INFUSION, "Infusion", "Infuse specific items");
        add(ExorcerySpells.FLUID_WALK, "Fluid Walk", "Walk on liquids!");
    }

    private void addEntities()
    {

    }

    private void addEffects()
    {
        add("effect.exorcery.polymorph", "Polymorph");
        add(ExorceryEffects.FLUID_WALK, "Fluid Walk");
    }

    private void addPotions()
    {
        // Polymorph potions
        addPolymorphPotion(ExorceryPotions.POLYMORPH_COW);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_CREEPER);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_HORSE);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_LLAMA);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_PANDA);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_PIG);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_POLAR_BEAR);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_SHEEP);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_SKELETON);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_VILLAGER);
        addPolymorphPotion(ExorceryPotions.POLYMORPH_ZOMBIE);
    }

    private void addGuis()
    {
        addGui("actionbar.casting", "Casting");

        // Spell selector
        addGui("spell_selector", "Spell Selector");
        addGui("spell_selector.keybindings", "Keybindings");
        addGui("spell_selector.keybindings.close", "Close");
        addGui("spell_selector.keybindings.info.line1", "Keybinding menu");
        addGui("spell_selector.keybindings.info.line2", "Press a key while hovering over a spell to bind it");
        addGui("spell_selector.keybindings.info.line3", "You can only bind unbound keys");
        addGui("spell_selector.keybindings.info.line4", "Click to unbind");
        addGui("spell_selector.spell_description", "Press shift for more info");

        // Tooltips
        addGui("tooltip.cast_time", "Cast Time");
        addGui("tooltip.cast_time_instant", "Instant");
        addGui("tooltip.cast_time_seconds", "seconds");
        addGui("tooltip.keybinding", "Key");
        addGui("tooltip.keybinding.none", "none");
        addGui("tooltip.mana_cost", "Mana Cost");
        addGui("tooltip.mana_cost_free", "Free");
        addGui("tooltip.while_running", "Can be casted while running");

        // Jei
        add("gui.jei.category.infusion", "Infusion");
    }

    private void addAdvancements()
    {
        addAdvancement("root", "Exorcery", "This seems like something new");
        addAdvancement("mana", "Mana", "Some type of energy?");
    }

    private void addConfigs()
    {
        // GeneralConfig
        addConfig("general", "player_mana", "The default mana value of the player");

        // SpellConfig
        addConfig("spell", "teleport_mana_cost_multiplier", "Mana cost multiplier of the teleport spell");
        addConfig("spell", "teleport_cast_time", "Cast time of the teleport spell (in ticks)");

        addConfig("spell", "speed_mana_cost_self", "Mana cost of the speed spell on yourself");
        addConfig("spell", "speed_mana_cost_other", "Mana cost of the speed spell on others");
        addConfig("spell", "speed_cast_time", "Cast time of the speed spell (in ticks)");
        addConfig("spell", "speed_effect_duration", "Effect duration of the speed spell");

        addConfig("spell", "fertility_mana_cost", "Mana cost of the fertility spell");
        addConfig("spell", "fertility_cast_time", "Cast time of the fertility spell (in ticks)");
        addConfig("spell", "fertility_radius", "Radius of the fertility spell");

        addConfig("spell", "fireball_mana_cost", "Mana cost of the fireball spell");
        addConfig("spell", "fireball_cast_time", "Cast time of the fireball spell (in ticks)");

        addConfig("spell", "time_warp_mana_cost", "Mana cost of the time warp spell");
        addConfig("spell", "time_warp_cast_time", "Cast time of the time warp spell (in ticks)");
        addConfig("spell", "time_warp_steps", "Time steps to reach day/night time");

        addConfig("spell", "frost_mana_cost", "Mana cost of the frost spell");
        addConfig("spell", "frost_cast_time", "Cast time of the frost spell (in ticks)");
        addConfig("spell", "frost_radius", "Radius of the frost spell");
        addConfig("spell", "frost_duration", "Effect duration of the frost spell");

        addConfig("spell", "counter_mana_cost", "Mana cost of the counter spell");
        addConfig("spell", "counter_cast_time", "Cast time of the counter spell (in ticks)");
        addConfig("spell", "counter_radius", "Radius of the counter spell");

        addConfig("spell", "infusion_cast_time", "Cast time of the infusion spell (in ticks)");

        addConfig("spell", "fluidWalk_mana_cost", "Mana cost of the fluid walk spell");
        addConfig("spell", "fluidWalk_cast_time", "Cast time of the fluid walk spell (in ticks)");
        addConfig("spell", "fluidWalk_duration", "Effect duration of the fluid Walk spell");
    }

    private void addMisc()
    {
        add("argument.exorcery.spell.invalid", "Unknown spell '%s'");

        // Chat messages
        add("chat.exorcery.forgotten", "You have forgotten a spell");
        add("chat.exorcery.mounted", "You can't do that while mounted");
        add("chat.exorcery.no_mana", "You don't have enough mana to cast any spells");
        add("chat.exorcery.no_spells", "You haven't learned any spells yet");
        add("chat.exorcery.spell_known", "You already know this spell");
        add("chat.exorcery.spell_learned", "You have learned a new spell!");

        // ItemGroups
        add("itemGroup.exorcery", "Exorcery");

        // KeyBindings
        add("key.exorcery.spell_selector", "Select/Cast Spell");

        // Subtitles
        add("subtitles.exorcery.spell_chanting", "Chanting Spell");
    }

    private void add(Item key, String name, String... tooltip)
    {
        add(key, name);
        for (int i = 0; i < tooltip.length; i++)
        {
            add(key.getTranslationKey() + ".tooltip.line" + (i + 1), tooltip[i]);
        }
    }

    private void addSpell(Supplier<? extends Spell> key, String name)
    {
        add(key.get(), name);
    }

    private void add(Spell key, String name)
    {
        add(key.getTranslationKey(), name);
    }

    private void add(Spell key, String name, String... description)
    {
        add(key, name);
        for (int i = 0; i < description.length; i++)
        {
            add(key.getTranslationKey() + ".description.line" + (i + 1), description[i]);
        }
    }

    private void addPolymorphPotion(Potion potion)
    {
        Effect effect = potion.getEffects().get(0).getPotion();
        if (effect instanceof PolymorphEffect)
        {
            String name = I18n.format(((PolymorphEffect) effect).getEntityType().getTranslationKey());
            add(potion, name + " " + I18n.format("effect.exorcery.polymorph"));
        }
    }

    private void add(Potion key, String name)
    {
        String potion = Objects.requireNonNull(key.getRegistryName()).getPath();
        add("item.minecraft.potion.effect." + potion, name + " Potion");
        add("item.minecraft.splash_potion.effect." + potion, name + " Splash Potion");
        add("item.minecraft.lingering_potion.effect." + potion, name + " Lingering Potion");
    }

    private void addGui(String key, String name)
    {
        add("gui." + Exorcery.MOD_ID + "." + key, name);
    }

    private void addAdvancement(String advancement, String title, String description)
    {
        add("advancements." + Exorcery.MOD_ID + "." + advancement + ".title", title);
        add("advancements." + Exorcery.MOD_ID + "." + advancement + ".description", description);
    }

    private void addConfig(String config, String key, String name)
    {
        add("config." + Exorcery.MOD_ID + "." + config + "." + key, name);
    }
}
