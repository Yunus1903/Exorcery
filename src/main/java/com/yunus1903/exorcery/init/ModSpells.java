package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.spell.SpeedSpell;
import com.yunus1903.exorcery.common.spell.TeleportSpell;
import com.yunus1903.exorcery.common.spell.TestSpell;

public abstract class ModSpells
{
    public static void register()
    {
        ExorceryRegistry.SPELLS.register(new TestSpell());
        ExorceryRegistry.SPELLS.register(new TeleportSpell());
        ExorceryRegistry.SPELLS.register(new SpeedSpell());
    }
}
