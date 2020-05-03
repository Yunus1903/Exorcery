package com.yunus1903.exorcery.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author Yunus1903
 * @since 26/04/2020
 */
public class SpellConfig implements IBaseConfig
{
    public static int teleportManaCostMultiplier;
    public static int teleportCastTime;
    public static int speedManaCostSelf;
    public static int speedManaCostOther;
    public static int speedCastTime;
    public static int speedEffectDuration;
    public static int fertilityManaCost;
    public static int fertilityCastTime;
    public static int fertilityRadius;

    private final ForgeConfigSpec.IntValue TELEPORT_MANA_COST_MULTIPLIER;
    private final ForgeConfigSpec.IntValue TELEPORT_CAST_TIME;
    private final ForgeConfigSpec.IntValue SPEED_MANA_COST_SELF;
    private final ForgeConfigSpec.IntValue SPEED_MANA_COST_OTHER;
    private final ForgeConfigSpec.IntValue SPEED_CAST_TIME;
    private final ForgeConfigSpec.IntValue SPEED_EFFECT_DURATION;
    private final ForgeConfigSpec.IntValue FERTILITY_MANA_COST;
    private final ForgeConfigSpec.IntValue FERTILITY_CAST_TIME;
    private final ForgeConfigSpec.IntValue FERTILITY_RADIUS;

    public SpellConfig(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Spell config").push("spell");

        builder.comment("Teleport").push("teleport");

        TELEPORT_MANA_COST_MULTIPLIER = builder
                .comment("Mana cost multiplier of the teleport spell")
                .translation("config.exorcery.spell.teleport_mana_cost_multiplier")
                .defineInRange("teleportManaCostMultiplier", 3, 0, 50);

        TELEPORT_CAST_TIME = builder
                .comment("Cast time of the teleport spell (in ticks)")
                .translation("config.exorcery.spell.teleport_cast_time")
                .worldRestart()
                .defineInRange("teleportCastTime", 0, 0, 6000);

        builder.pop().comment("Speed").push("speed");

        SPEED_MANA_COST_SELF = builder
                .comment("Mana cost of the speed spell on yourself")
                .translation("config.exorcery.spell.speed_mana_cost_self")
                .worldRestart()
                .defineInRange("speedManaCostSelf", 100, 0, 10000);

        SPEED_MANA_COST_OTHER = builder
                .comment("Mana cost of the speed spell on others")
                .translation("config.exorcery.spell.speed_mana_cost_other")
                .defineInRange("speedManaCostOther", 150, 0, 10000);

        SPEED_CAST_TIME = builder
                .comment("Cast time of the speed spell (in ticks)")
                .translation("config.exorcery.spell.speed_cast_time")
                .worldRestart()
                .defineInRange("speedCastTime", 60, 0, 6000);

        SPEED_EFFECT_DURATION = builder
                .comment("Effect duration of the speed spell")
                .translation("config.exorcery.spell.speed_effect_duration")
                .defineInRange("speedEffectDuration", 400, 0, 20000000);

        builder.pop().comment("Fertility").push("fertility");

        FERTILITY_MANA_COST = builder
                .comment("Mana cost of the fertility spell")
                .translation("config.exorcery.spell.fertility_mana_cost")
                .worldRestart()
                .defineInRange("fertilityManaCost", 200, 0, 10000);

        FERTILITY_CAST_TIME = builder
                .comment("Cast time of the fertility spell (in ticks)")
                .translation("config.exorcery.spell.fertility_cast_time")
                .worldRestart()
                .defineInRange("fertilityCastTime", 80, 0, 6000);

        FERTILITY_RADIUS = builder
                .comment("Radius of the fertility spell")
                .translation("config.exorcery.spell.fertility_radius")
                .defineInRange("fertilityRadius", 4, 1, 50);

        builder.pop(2);
    }

    @Override
    public void bake()
    {
        teleportManaCostMultiplier = TELEPORT_MANA_COST_MULTIPLIER.get();
        teleportCastTime = TELEPORT_CAST_TIME.get();
        speedManaCostSelf = SPEED_MANA_COST_SELF.get();
        speedManaCostOther = SPEED_MANA_COST_OTHER.get();
        speedCastTime = SPEED_CAST_TIME.get();
        speedEffectDuration = SPEED_EFFECT_DURATION.get();
        fertilityManaCost = FERTILITY_MANA_COST.get();
        fertilityCastTime = FERTILITY_CAST_TIME.get();
        fertilityRadius = FERTILITY_RADIUS.get();
    }
}
