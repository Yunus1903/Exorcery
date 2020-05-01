package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.spell.*;

/**
 * @author Yunus1903
 */
public final class ModSpells
{
    public static final Spell TELEPORT = new TeleportSpell();
    public static final Spell SPEED = new SpeedSpell();
    public static final Spell FERTILITY = new FertilitySpell();

    public static void register()
    {
        ExorceryRegistry.SPELLS.registerAll(
                TELEPORT,
                SPEED,
                FERTILITY
        );
    }
}
