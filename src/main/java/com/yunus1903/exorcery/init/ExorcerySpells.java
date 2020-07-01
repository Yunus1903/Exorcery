package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.config.ExorceryConfig;
import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.spell.*;
import net.minecraft.potion.Effects;

import java.util.ArrayList;
import java.util.List;

import static com.yunus1903.exorcery.common.config.SpellConfig.*;

/**
 * @author Yunus1903
 */
public final class ExorcerySpells
{
    static
    {
        ExorceryConfig.SPELL.bake();
    }

    private static final List<Spell> SPELLS = new ArrayList<>();
    public static final Spell TELEPORT = register(new TeleportSpell());
    public static final Spell SPEED = register(new EffectSpell("speed",
            Effects.SPEED,
            speedEffectDuration,
            2,
            speedManaCostSelf,
            speedManaCostOther,
            speedCastTime,
            SpellType.NORMAL
    ));
    public static final Spell FERTILITY = register(new FertilitySpell());
    public static final Spell FIREBALL = register(new FireballSpell());
    public static final Spell TIME_WARP_DAY = register(new TimeWarpSpell("time_warp_day", 0));
    public static final Spell TIME_WARP_NIGHT = register(new TimeWarpSpell("time_warp_night", 14000));
    public static final Spell FROST = register(new FrostSpell());
    public static final Spell COUNTER = register(new CounterSpell());

    private static Spell register(Spell spell)
    {
        SPELLS.add(spell);
        return spell;
    }

    public static void registerSpells()
    {
        for (Spell spell : SPELLS)
        {
            ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).register(spell);
        }
    }
}
