package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.spell.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 */
public final class ModSpells
{
    private static final List<Spell> SPELLS = new ArrayList<>();
    public static final Spell TELEPORT = register(new TeleportSpell());
    public static final Spell SPEED = register(new SpeedSpell());
    public static final Spell FERTILITY = register(new FertilitySpell());

    private static Spell register(Spell spell)
    {
        SPELLS.add(spell);
        return spell;
    }

    public static void registerSpells()
    {
        for (Spell spell : SPELLS)
        {
            ExorceryRegistry.SPELLS.register(spell);
        }
    }
}
