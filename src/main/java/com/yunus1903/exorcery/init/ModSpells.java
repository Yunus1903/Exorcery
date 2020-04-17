package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.spell.SpeedSpell;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.common.spell.TeleportSpell;
import com.yunus1903.exorcery.common.spell.TestSpell;

public final class ModSpells
{
    public static final Spell TEST = new TestSpell();
    public static final Spell TELEPORT = new TeleportSpell();
    public static final Spell SPEED = new SpeedSpell();

    public static void register()
    {
        ExorceryRegistry.SPELLS.register(TEST);
        ExorceryRegistry.SPELLS.register(TELEPORT);
        ExorceryRegistry.SPELLS.register(SPEED);
    }
}
