package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.effect.PolymorphEffect;
import com.yunus1903.exorcery.common.effect.FluidWalkEffect;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.EntityType;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 * @since 24/06/2020
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ExorceryEffects
{
    private static final List<Effect> EFFECTS = new ArrayList<>();
    public static final Effect POLYMORPH_COW = register(new PolymorphEffect(EntityType.COW, 0x443626));
    public static final Effect POLYMORPH_CREEPER = register(new PolymorphEffect(EntityType.CREEPER, 0x0DA70B));
    public static final Effect POLYMORPH_HORSE = register(new PolymorphEffect(EntityType.HORSE, 0xC09E7D));
    public static final Effect POLYMORPH_LLAMA = register(new PolymorphEffect(EntityType.LLAMA, 0xC09E7D));
    public static final Effect POLYMORPH_PANDA = register(new PolymorphEffect(EntityType.PANDA, 0xE7E7E7));
    public static final Effect POLYMORPH_PIG = register(new PolymorphEffect(EntityType.PIG, 0xF0A5A2));
    public static final Effect POLYMORPH_POLAR_BEAR = register(new PolymorphEffect(EntityType.POLAR_BEAR, 0xF2F2F2));
    public static final Effect POLYMORPH_SHEEP = register(new PolymorphEffect(EntityType.SHEEP, 0xE7E7E7));
    public static final Effect POLYMORPH_SKELETON = register(new PolymorphEffect(EntityType.SKELETON, 0xC1C1C1));
    public static final Effect POLYMORPH_VILLAGER = register(new PolymorphEffect(EntityType.VILLAGER, 0x563C33));
    public static final Effect POLYMORPH_ZOMBIE = register(new PolymorphEffect(EntityType.ZOMBIE, 0x00AFAF));
    public static final Effect FLUID_WALK = register(new FluidWalkEffect());

    private static Effect register(Effect effect)
    {
        EFFECTS.add(effect);
        return effect;
    }

    @SubscribeEvent
    public static void registerEffects(final RegistryEvent.Register<Effect> event)
    {
        for (Effect effect : EFFECTS)
        {
            event.getRegistry().register(effect);
        }
    }
}
