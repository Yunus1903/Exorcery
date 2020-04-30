package com.yunus1903.exorcery.common.config;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

/**
 * @author Yunus1903
 * @since 26/04/2020
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExorceryConfig
{
    public static final ClientConfig CLIENT;
    public static final GeneralConfig GENERAL;
    public static final SpellConfig SPELL;

    public static final ForgeConfigSpec CLIENT_SPEC;

    static
    {
        final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        CLIENT = new ClientConfig(BUILDER);

        CLIENT_SPEC = BUILDER.build();
    }

    public static final ForgeConfigSpec COMMON_SPEC;

    static
    {
        final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        GENERAL = new GeneralConfig(BUILDER);
        SPELL = new SpellConfig(BUILDER);

        COMMON_SPEC = BUILDER.build();
    }

    public static final ForgeConfigSpec SERVER_SPEC;

    static
    {
        final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        SERVER_SPEC = BUILDER.build();
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent event)
    {
        if (event.getConfig().getSpec() == CLIENT_SPEC)
        {
            CLIENT.bake();
        }
        else if (event.getConfig().getSpec() == COMMON_SPEC)
        {
            GENERAL.bake();
            SPELL.bake();
        }
    }
}
