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
    public static int fireballManaCost;
    public static int fireballCastTime;
    public static int timeWarpManaCost;
    public static int timeWarpCastTime;
    public static int timeWarpSteps;
    public static int frostManaCost;
    public static int frostCastTime;
    public static int frostRadius;
    public static int frostDuration;
    public static int counterManaCost;
    public static int counterCastTime;
    public static int counterRadius;
    public static int infusionCastTime;

    private final ForgeConfigSpec.IntValue TELEPORT_MANA_COST_MULTIPLIER;
    private final ForgeConfigSpec.IntValue TELEPORT_CAST_TIME;
    private final ForgeConfigSpec.IntValue SPEED_MANA_COST_SELF;
    private final ForgeConfigSpec.IntValue SPEED_MANA_COST_OTHER;
    private final ForgeConfigSpec.IntValue SPEED_CAST_TIME;
    private final ForgeConfigSpec.IntValue SPEED_EFFECT_DURATION;
    private final ForgeConfigSpec.IntValue FERTILITY_MANA_COST;
    private final ForgeConfigSpec.IntValue FERTILITY_CAST_TIME;
    private final ForgeConfigSpec.IntValue FERTILITY_RADIUS;
    private final ForgeConfigSpec.IntValue FIREBALL_MANA_COST;
    private final ForgeConfigSpec.IntValue FIREBALL_CAST_TIME;
    private final ForgeConfigSpec.IntValue TIME_WARP_MANA_COST;
    private final ForgeConfigSpec.IntValue TIME_WARP_CAST_TIME;
    private final ForgeConfigSpec.IntValue TIME_WARP_STEPS;
    private final ForgeConfigSpec.IntValue FROST_MANA_COST;
    private final ForgeConfigSpec.IntValue FROST_CAST_TIME;
    private final ForgeConfigSpec.IntValue FROST_RADIUS;
    private final ForgeConfigSpec.IntValue FROST_DURATION;
    private final ForgeConfigSpec.IntValue COUNTER_MANA_COST;
    private final ForgeConfigSpec.IntValue COUNTER_CAST_TIME;
    private final ForgeConfigSpec.IntValue COUNTER_RADIUS;
    private final ForgeConfigSpec.IntValue INFUSION_CAST_TIME;

    public SpellConfig(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Spell config").push("spell");

        builder.comment("Teleport").push("teleport");

        TELEPORT_MANA_COST_MULTIPLIER = builder
                .comment("Mana cost multiplier of the teleport spell")
                .translation("config.exorcery.spell.teleport_mana_cost_multiplier")
                .defineInRange("teleportManaCostMultiplier", 5, 0, 50);

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
                .defineInRange("speedManaCostSelf", 200, 0, 10000);

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
                .defineInRange("fertilityManaCost", 300, 0, 10000);

        FERTILITY_CAST_TIME = builder
                .comment("Cast time of the fertility spell (in ticks)")
                .translation("config.exorcery.spell.fertility_cast_time")
                .worldRestart()
                .defineInRange("fertilityCastTime", 80, 0, 6000);

        FERTILITY_RADIUS = builder
                .comment("Radius of the fertility spell")
                .translation("config.exorcery.spell.fertility_radius")
                .defineInRange("fertilityRadius", 4, 1, 50);

        builder.pop().comment("Fireball").push("fireball");

        FIREBALL_MANA_COST = builder
                .comment("Mana cost of the fireball spell")
                .translation("config.exorcery.spell.fireball_mana_cost")
                .worldRestart()
                .defineInRange("fireballManaCost", 150, 0, 10000);

        FIREBALL_CAST_TIME = builder
                .comment("Cast time of the fireball spell (in ticks)")
                .translation("config.exorcery.spell.fireball_cast_time")
                .worldRestart()
                .defineInRange("fireballCastTime", 40, 0, 6000);

        builder.pop().comment("TimeWarp").push("timewarp");

        TIME_WARP_MANA_COST = builder
                .comment("Mana cost of the time warp spell")
                .translation("config.exorcery.spell.time_warp_mana_cost")
                .worldRestart()
                .defineInRange("timeWarpManaCost", 800, 0, 10000);

        TIME_WARP_CAST_TIME = builder
                .comment("Cast time of the time warp spell (in ticks)")
                .translation("config.exorcery.spell.time_warp_cast_time")
                .worldRestart()
                .defineInRange("timeWarpCastTime", 200, 0, 6000);

        TIME_WARP_STEPS = builder
                .comment("Time steps to reach day/night time")
                .translation("config.exorcery.spell.time_warp_steps")
                .worldRestart()
                .defineInRange("timeWarpSteps", 50, 0, 6000);

        builder.pop().comment("Frost").push("frost");

        FROST_MANA_COST = builder
                .comment("Mana cost of the frost spell")
                .translation("config.exorcery.spell.frost_mana_cost")
                .worldRestart()
                .defineInRange("frostManaCost", 450, 0, 10000);

        FROST_CAST_TIME = builder
                .comment("Cast time of the frost spell (in ticks)")
                .translation("config.exorcery.spell.frost_cast_time")
                .worldRestart()
                .defineInRange("frostCastTime", 0, 0, 6000);

        FROST_RADIUS = builder
                .comment("Radius of the frost spell")
                .translation("config.exorcery.spell.frost_radius")
                .defineInRange("frostRadius", 5, 1, 50);

        FROST_DURATION = builder
                .comment("Effect duration of the frost spell")
                .translation("config.exorcery.spell.frost_duration")
                .defineInRange("frostDuration", 100, 0, 20000000);

        builder.pop().comment("Counter").push("counter");

        COUNTER_MANA_COST = builder
                .comment("Mana cost of the counter spell")
                .translation("config.exorcery.spell.counter_mana_cost")
                .worldRestart()
                .defineInRange("counterManaCost", 600, 0, 10000);

        COUNTER_CAST_TIME = builder
                .comment("Cast time of the counter spell (in ticks)")
                .translation("config.exorcery.spell.counter_cast_time")
                .worldRestart()
                .defineInRange("counterCastTime", 0, 0, 6000);

        COUNTER_RADIUS = builder
                .comment("Radius of the counter spell")
                .translation("config.exorcery.spell.counter_radius")
                .defineInRange("counterRadius", 5, 1, 50);

        builder.pop().comment("Infusion").push("infusion");

        INFUSION_CAST_TIME = builder
                .comment("Cast time of the infusion spell (in ticks)")
                .translation("config.exorcery.spell.infusion_cast_time")
                .worldRestart()
                .defineInRange("infusionCastTime", 100, 0, 6000);

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
        fireballManaCost = FIREBALL_MANA_COST.get();
        fireballCastTime = FIREBALL_CAST_TIME.get();
        timeWarpManaCost = TIME_WARP_MANA_COST.get();
        timeWarpCastTime = TIME_WARP_CAST_TIME.get();
        timeWarpSteps = TIME_WARP_STEPS.get();
        frostManaCost = FROST_MANA_COST.get();
        frostCastTime = FROST_CAST_TIME.get();
        frostRadius = FROST_RADIUS.get();
        frostDuration = FROST_DURATION.get();
        counterManaCost = COUNTER_MANA_COST.get();
        counterCastTime = COUNTER_CAST_TIME.get();
        counterRadius = COUNTER_RADIUS.get();
        infusionCastTime = INFUSION_CAST_TIME.get();
    }
}
