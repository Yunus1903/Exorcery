package com.yunus1903.exorcery.core;

import com.yunus1903.exorcery.client.misc.KeybindingHandler;
import com.yunus1903.exorcery.common.capabilities.casting.CastingCapability;
import com.yunus1903.exorcery.common.capabilities.casting.CastingStorage;
import com.yunus1903.exorcery.common.capabilities.casting.ICasting;
import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaStorage;
import com.yunus1903.exorcery.common.capabilities.morph.IMorph;
import com.yunus1903.exorcery.common.capabilities.morph.MorphCapability;
import com.yunus1903.exorcery.common.capabilities.morph.MorphStorage;
import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsStorage;
import com.yunus1903.exorcery.common.command.ExorceryCommand;
import com.yunus1903.exorcery.common.config.ExorceryConfig;
import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.init.ExorcerySpells;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Yunus1903
 */
@Mod(Exorcery.MOD_ID)
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Exorcery
{
    public static final String MOD_ID = "exorcery";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Exorcery instance;
    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    @OnlyIn(Dist.CLIENT)
    public static KeybindingHandler keybindingHandler;

    public MinecraftServer server;

    public Exorcery()
    {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ExorceryConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ExorceryConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ExorceryConfig.SERVER_SPEC);
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Exorcery is being initialized");
        ExorceryRegistry.createRegistries();
        CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), ManaCapability::new);
        CapabilityManager.INSTANCE.register(ISpells.class, new SpellsStorage(), SpellsCapability::new);
        CapabilityManager.INSTANCE.register(ICasting.class, new CastingStorage(), CastingCapability::new);
        CapabilityManager.INSTANCE.register(IMorph.class, new MorphStorage(), MorphCapability::new);
        PacketHandler.register();
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event)
    {
        keybindingHandler = new KeybindingHandler(event.getMinecraftSupplier().get());
    }

    @SubscribeEvent
    public static void enqueueIMC(final InterModEnqueueEvent event)
    {
        ExorcerySpells.registerSpells();
    }

    @SubscribeEvent
    public static void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Finished initializing Exorcery");
    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event)
    {
        server = event.getServer();
        ExorceryCommand.register(event.getCommandDispatcher());
    }
}
