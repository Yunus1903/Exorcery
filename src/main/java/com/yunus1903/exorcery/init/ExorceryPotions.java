package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yunus1903
 * @since 26/06/2020
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ExorceryPotions
{
    private static final List<Potion> POTIONS = new ArrayList<>();
    public static final Potion POLYMORPH_COW = register(ExorceryEffects.POLYMORPH_COW, 900);
    public static final Potion POLYMORPH_CREEPER = register(ExorceryEffects.POLYMORPH_CREEPER, 900);
    public static final Potion POLYMORPH_HORSE = register(ExorceryEffects.POLYMORPH_HORSE, 900);
    public static final Potion POLYMORPH_LLAMA = register(ExorceryEffects.POLYMORPH_LLAMA, 900);
    public static final Potion POLYMORPH_PANDA = register(ExorceryEffects.POLYMORPH_PANDA, 900);
    public static final Potion POLYMORPH_PIG = register(ExorceryEffects.POLYMORPH_PIG, 900);
    public static final Potion POLYMORPH_POLAR_BEAR = register(ExorceryEffects.POLYMORPH_POLAR_BEAR, 900);
    public static final Potion POLYMORPH_SHEEP = register(ExorceryEffects.POLYMORPH_SHEEP, 900);
    public static final Potion POLYMORPH_SKELETON = register(ExorceryEffects.POLYMORPH_SKELETON, 900);
    public static final Potion POLYMORPH_VILLAGER = register(ExorceryEffects.POLYMORPH_VILLAGER, 900);
    public static final Potion POLYMORPH_ZOMBIE = register(ExorceryEffects.POLYMORPH_ZOMBIE, 900);

    private static Potion register(Effect effect, int duration)
    {
        return register(new Potion(new EffectInstance(effect, duration)));
    }

    private static Potion register(Potion potion)
    {
        potion.setRegistryName(potion.getEffects().get(0).getPotion().getRegistryName());
        POTIONS.add(potion);
        return potion;
    }

    @SubscribeEvent
    public static void registerPotions(final RegistryEvent.Register<Potion> event)
    {
        for (Potion potion : POTIONS)
        {
            event.getRegistry().register(potion);
        }
    }

    public static List<Potion> getPotions()
    {
        return Collections.unmodifiableList(POTIONS);
    }
}
