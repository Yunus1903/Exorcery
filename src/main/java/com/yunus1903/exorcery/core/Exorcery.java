package com.yunus1903.exorcery.core;

import com.yunus1903.exorcery.client.gui.GuiMana;
import com.yunus1903.exorcery.client.gui.GuiSpellSelector;
import com.yunus1903.exorcery.common.capabilities.CapabilityHandler;
import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.mana.ManaStorage;
import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsStorage;
import com.yunus1903.exorcery.common.misc.EventHandler;
import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.network.packets.PacketSyncMana;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.common.spell.TestSpell;
import com.yunus1903.exorcery.common.spell.TestSpell2;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.PacketSyncSpells;
import com.yunus1903.exorcery.init.ModSpells;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Exorcery.MOD_ID)
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Exorcery
{
    public static final String MOD_ID = "exorcery";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Exorcery instance;
    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    private static KeyBinding key;
    private static KeyBinding debugKey;

    public Exorcery()
    {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Exorcery is being initialized");
        ExorceryRegistry.createRegistries();
        proxy.registerMod();
        CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), ManaCapability::new);
        CapabilityManager.INSTANCE.register(ISpells.class, new SpellsStorage(), SpellsCapability::new);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        PacketHandler.register();
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event)
    {
        key = new KeyBinding("key.exorcery.spell_selector", 192, "key.categories.misc");
        debugKey = new KeyBinding("key.exorcery.debug", 72, "key.categories.misc");
        ClientRegistry.registerKeyBinding(key);
        ClientRegistry.registerKeyBinding(debugKey);
        MinecraftForge.EVENT_BUS.register(new GuiMana());
        //MinecraftForge.EVENT_BUS.register(new GuiSpellSelector());
    }

    @SubscribeEvent
    public static void enqueueIMC(final InterModEnqueueEvent event)
    {
        ModSpells.register();
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

    public MinecraftServer server;

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event)
    {
        server = event.getServer();
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            ISpells spells = event.getPlayer().getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());
            IMana mana = event.getPlayer().getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());

            TestSpell testSpell = (TestSpell) ExorceryRegistry.SPELLS.getValue(new ResourceLocation(MOD_ID, "spell_test"));
            TestSpell2 testSpell2 = (TestSpell2) ExorceryRegistry.SPELLS.getValue(new ResourceLocation(MOD_ID, "spell_test_2"));
            spells.addSpell(testSpell);
            spells.addSpell(testSpell2);

            PacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new PacketSyncSpells(spells.getSpells()));
            PacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new PacketSyncMana(mana.get(), mana.getMax(), mana.getRegenerationRate()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (key.isPressed())
        {
            Minecraft mc = Minecraft.getInstance();
            //ISpells spells = mc.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());

            // Don't even show the selector to spectator mode
            // Maybe make config to enable/disable in adventure mode
            if (mc.player.isSpectator()) return;

            mc.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent((spells) ->
            {
                if (!spells.getSpells().isEmpty()) spells.getSpellById(0).castSpell(mc.world, mc.player);
            });



            /*
            Tessellator tessellator = Tessellator.getInstance();

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            GL11.glVertex2d(20, 20);
            for(int i = 0; i <= 100; i++)
            {
                GL11.glVertex2d(20 + (40 * Math.cos(i *  (2 * Math.PI) / 100)), 20 + (40 * Math.sin(i * (2 * Math.PI) / 100)));
            }
            GL11.glEnd();

             */
        }
        else if (debugKey.isPressed())
        {

        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public void ticker(TickEvent event)
    {
        /*
        World world = server.getWorld(DimensionType.OVERWORLD);
        if (world.getPlayers().isEmpty()) return;
        PlayerEntity player = world.getPlayers().get(0);
        LazyOptional<IMana> manaCapability = player.getCapability(ManaProvider.MANA_CAPABILITY, null);
        IMana mana = manaCapability.orElse(new ManaCapability());
        Exorcery.LOGGER.info(mana.get());

         */
    }
}
