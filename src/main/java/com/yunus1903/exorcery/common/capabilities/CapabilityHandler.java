package com.yunus1903.exorcery.common.capabilities;

import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.morph.MorphProvider;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler class for custom {@link net.minecraftforge.common.capabilities.Capability capabilities}
 * @author Yunus1903
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID)
public final class CapabilityHandler
{
    public static final ResourceLocation MANA_CAPABILITY = new ResourceLocation(Exorcery.MOD_ID, "mana");
    public static final ResourceLocation SPELLS_CAPABILITY = new ResourceLocation(Exorcery.MOD_ID, "spells");
    public static final ResourceLocation CASTING_CAPABILITY = new ResourceLocation(Exorcery.MOD_ID, "casting");
    public static final ResourceLocation MORPH_CAPABILITY = new ResourceLocation(Exorcery.MOD_ID, "morph");

    @SubscribeEvent
    public static void attachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof PlayerEntity)) return;
        event.addCapability(MANA_CAPABILITY, new ManaProvider());
        event.addCapability(SPELLS_CAPABILITY, new SpellsProvider());
        event.addCapability(CASTING_CAPABILITY, new CastingProvider());
    }

    @SubscribeEvent
    public static void attachCapabilitiesWorld(AttachCapabilitiesEvent<World> event)
    {
        event.addCapability(MORPH_CAPABILITY, new MorphProvider());
    }
}
