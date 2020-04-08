package com.yunus1903.exorcery.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Exorcery.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Exorcery
{
    public static final String MODID = "exorcery";
    public static final String NAME = "Exorcery";
    public static final String VERSION = "${mod_version}";

    private static final Logger LOGGER = LogManager.getLogger(NAME);

    public static Exorcery instance;

    public Exorcery()
    {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void preInit(final FMLCommonSetupEvent event)
    {

    }

    @SubscribeEvent
    public static void init(final InterModEnqueueEvent event)
    {

    }

    @SubscribeEvent
    public static void postInit(final InterModProcessEvent event)
    {

    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event)
    {

    }
}
