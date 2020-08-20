package com.yunus1903.exorcery.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * General config
 * @author Yunus1903
 * @since 26/04/2020
 */
public class GeneralConfig implements IBaseConfig
{
    public static int playerMana;

    private final ForgeConfigSpec.IntValue PLAYER_MANA;

    public GeneralConfig(ForgeConfigSpec.Builder builder)
    {
        builder.comment("General config").push("general");

        PLAYER_MANA = builder
                .comment("The default mana value of the player")
                .translation("config.exorcery.general.player_mana")
                .worldRestart()
                .defineInRange("playerMana", 1000, 1, 100000);

        builder.pop();
    }

    @Override
    public void bake()
    {
        playerMana = PLAYER_MANA.get();
    }
}
