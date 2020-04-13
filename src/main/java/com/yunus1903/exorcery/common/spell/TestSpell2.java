package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.core.Exorcery;

public class TestSpell2 extends Spell
{
    public TestSpell2()
    {
        this.setRegistryName(Exorcery.MOD_ID, "spell_test_2")
                .setManaCost(10f)
                .setCastTime(100)
                .setType(SpellType.DEBUG)
                .setWhileRunning(true);
    }
}
