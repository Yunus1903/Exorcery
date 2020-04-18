package com.yunus1903.exorcery.common.capabilities.spells;

import com.yunus1903.exorcery.common.spell.Spell;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 */
public class SpellsCapability implements ISpells
{
    private List<Spell> spells = new ArrayList<>();

    @Override
    public List<Spell> getSpells()
    {
        return spells;
    }

    @Override
    public void setSpells(List<Spell> spells)
    {
        this.spells = spells;
    }

    @Override
    public List<Spell> addSpell(Spell spell)
    {
        if (!spells.contains(spell)) spells.add(spell);
        return spells;
    }

    @Override
    public int getSpellId(Spell spell)
    {
        return spells.indexOf(spell);
    }

    @Override
    public Spell getSpellById(int id)
    {
        return spells.get(id);
    }
}
