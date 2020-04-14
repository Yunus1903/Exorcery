package com.yunus1903.exorcery.common.capabilities;

import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class CapabilityHandler
{
    public static final ResourceLocation MANA_CAPABILITY = new ResourceLocation(Exorcery.MOD_ID, "mana");
    public static final ResourceLocation SPELLS_CAPABILITY = new ResourceLocation(Exorcery.MOD_ID, "spells");
    public static final ResourceLocation CASTING_CAPABILITY = new ResourceLocation(Exorcery.MOD_ID, "casting");

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof PlayerEntity)) return;
        event.addCapability(MANA_CAPABILITY, new ManaProvider());
        event.addCapability(SPELLS_CAPABILITY, new SpellsProvider());
        event.addCapability(CASTING_CAPABILITY, new CastingProvider());
    }
}
