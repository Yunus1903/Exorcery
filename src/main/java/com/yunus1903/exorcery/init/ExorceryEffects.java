package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.core.Exorcery;
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
