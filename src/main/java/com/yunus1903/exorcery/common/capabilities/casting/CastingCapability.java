package com.yunus1903.exorcery.common.capabilities.casting;

import com.yunus1903.exorcery.common.spell.Spell;

import javax.annotation.Nullable;

public class CastingCapability implements ICasting
{
    private boolean isCasting = false;
    private Spell spell;

    @Override
    public boolean isCasting()
    {
        return isCasting;
    }

    @Nullable
    @Override
    public Spell getSpell()
    {
        return spell;
    }

    @Override
    public boolean startCasting(Spell spell)
    {
        if (spell != null)
        {
            isCasting = true;
            this.spell = spell;
            return true;
        }
        return false;
    }

    @Override
    public void stopCasting()
    {
        isCasting = false;
        this.spell = null;
    }
}
