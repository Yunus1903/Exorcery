package com.yunus1903.exorcery.common.capabilities.mana;

import com.yunus1903.exorcery.common.config.GeneralConfig;

/**
 * @author Yunus1903
 */
public class ManaCapability implements IMana
{
    private float mana = GeneralConfig.playerMana;
    private float maximumMana = GeneralConfig.playerMana;
    private int regenerationRate = 1;

    @Override
    public float get()
    {
        return mana;
    }

    @Override
    public void set(float amount)
    {
        mana = amount < 0 ? 0 : amount;
        mana = Math.min(mana, maximumMana);
    }

    @Override
    public float add(float amount)
    {
        mana += amount;
        mana = Math.min(mana, maximumMana);
        return mana;
    }

    @Override
    public float reduce(float amount)
    {
        mana -= amount;
        mana = mana < 0 ? 0 : mana;
        return mana;
    }

    @Override
    public float getMax()
    {
        return maximumMana;
    }

    @Override
    public void setMax(float amount)
    {
        float scale = mana / maximumMana;
        maximumMana = amount < 0 ? 0 : amount;
        mana = maximumMana * scale;
    }

    @Override
    public float addMax(float amount)
    {
        float scale = mana / maximumMana;
        maximumMana += amount;
        mana = maximumMana * scale;
        return maximumMana;
    }

    @Override
    public float reduceMax(float amount)
    {
        float scale = mana / maximumMana;
        maximumMana -= amount;
        maximumMana = maximumMana < 0 ? 0 : maximumMana;
        mana = maximumMana * scale;
        return maximumMana;
    }

    @Override
    public int getRegenerationRate()
    {
        return regenerationRate;
    }

    @Override
    public void setRegenerationRate(int rate)
    {
        regenerationRate = rate;
    }
}
