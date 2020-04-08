package com.yunus1903.exorcery.common.capabilities.mana;

public interface IMana
{
    float get();

    void set(float amount);

    float add(float amount);

    float reduce(float amount);

    float getMax();

    void setMax(float amount);

    float addMax(float amount);

    float reduceMax(float amount);

    int getRegenerationRate();

    void setRegenerationRate(int rate);
}
