package com.yunus1903.exorcery.common.capabilities.casting;

import com.yunus1903.exorcery.common.spell.Spell;

import javax.annotation.Nullable;

/**
 * @author Yunus1903
 * @since 14/04/2020
 */
public interface ICasting
{
    boolean isCasting();

    @Nullable
    Spell getSpell();

    boolean startCasting(Spell spell);

    void stopCasting();
}
