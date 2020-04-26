package com.yunus1903.exorcery.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author Yunus1903
 * @since 26/04/2020
 */
public class GeneralConfig implements IBaseConfig
{
    // TODO: player base mana

    public GeneralConfig(ForgeConfigSpec.Builder builder)
    {
        builder.comment("General config").push("general");

        builder.pop();
    }

    @Override
    public void bake()
    {

    }
}
