package com.yunus1903.exorcery.common.capabilities.casting;

import com.yunus1903.exorcery.common.spell.Spell;
import javax.annotation.Nullable;

/**
 * @author Yunus1903
 * @since 14/04/2020
 */
public class CastingCapability implements ICasting
{
    private boolean isCasting = false;
    private Spell spell = null;
    private int currentCastTime = 0;

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
    public int getCurrentCastTime()
    {
        return currentCastTime;
    }

    @Override
    public int getTotalCastTime()
    {
        return spell.getCastTime();
    }

    @Override
    public boolean startCasting(@Nullable Spell spell)
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
        spell = null;
        currentCastTime = 0;
    }

    @Override
    public void tick()
    {
        if (isCasting) currentCastTime++;
    }
}
