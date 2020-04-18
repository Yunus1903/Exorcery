package com.yunus1903.exorcery.common.capabilities.spells;

import com.yunus1903.exorcery.common.spell.Spell;

import java.util.List;

/**
 * @author Yunus1903
 */
public interface ISpells
{
    List<Spell> getSpells();

    void setSpells(List<Spell> spells);

    List<Spell> addSpell(Spell spell);

    int getSpellId(Spell spell);

    Spell getSpellById(int id);
}
