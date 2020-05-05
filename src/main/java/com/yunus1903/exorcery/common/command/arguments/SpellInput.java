package com.yunus1903.exorcery.common.command.arguments;

import com.yunus1903.exorcery.common.spell.Spell;

import java.util.function.Predicate;

/**
 * @author Yunus1903
 * @since 04/05/2020
 */
public class SpellInput implements Predicate<Spell>
{
    private final Spell spell;

    public SpellInput(Spell spell)
    {
        this.spell = spell;
    }

    @Override
    public boolean test(Spell spell)
    {
        return this.spell == spell;
    }

    public Spell getSpell()
    {
        return spell;
    }
}
