package com.yunus1903.exorcery.common.capabilities.casting;

import com.yunus1903.exorcery.common.spell.Spell;

import javax.annotation.Nullable;

public interface ICasting
{
    boolean isCasting();

    @Nullable
    Spell getSpell();

    boolean startCasting(Spell spell);

    void stopCasting();
}
