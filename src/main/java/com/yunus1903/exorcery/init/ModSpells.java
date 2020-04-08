package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.spell.TestSpell;
import com.yunus1903.exorcery.common.spell.TestSpell2;

public abstract class ModSpells
{
    public static void register()
    {
        ExorceryRegistry.SPELLS.register(new TestSpell());
        ExorceryRegistry.SPELLS.register(new TestSpell2());
    }
}
