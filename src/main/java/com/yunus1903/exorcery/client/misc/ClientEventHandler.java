package com.yunus1903.exorcery.client.misc;

import com.yunus1903.exorcery.client.screen.SpellSelectorScreen;
import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncCastingPacket;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.ClientProxy;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Yunus1903
 * @since 12/04/2020
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Exorcery.MOD_ID)
public final class ClientEventHandler
{
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft mc = Minecraft.getInstance();

        if (ClientProxy.KEY_SPELL_SELECTOR.isPressed())
        {
            if (mc.player.isSpectator()) return;

            if (mc.player.isRidingHorse())
            {
                mc.player.sendMessage(new TranslationTextComponent("chat.exorcery.mounted"));
                return;
            }

            ISpells spells = mc.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());
            if (spells.getSpells().isEmpty())
            {
                mc.player.sendMessage(new TranslationTextComponent("chat.exorcery.no_spells"));
                return;
            }

            mc.displayGuiScreen(new SpellSelectorScreen());
        }

        if (mc.currentScreen instanceof SpellSelectorScreen)
        {
            SpellSelectorScreen gui = (SpellSelectorScreen) mc.currentScreen;

            if (gui.keybindMode)
            {
                gui.onKeyPress(event.getKey());
            }
        }

        if (mc.player != null && mc.currentScreen == null)
        {
            mc.player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
            {
                for (Spell spell : spells.getSpells())
                {
                    InputMappings.Input key = Exorcery.keybindingHandler.getKey(spell);
                    if (key != null && InputMappings.isKeyDown(mc.getMainWindow().getHandle(), key.getKeyCode()))
                    {
                        mc.player.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
                        {
                            if (!casting.isCasting()) spell.castSpell(mc.world, mc.player);
                        });
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onInitGui(GuiScreenEvent.InitGuiEvent.Pre event)
    {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player == null) return;

        player.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
        {
            if (casting.isCasting())
            {
                casting.stopCasting();
                PacketHandler.sendToServer(new SyncCastingPacket(casting.isCasting(), casting.getSpell()));
            }
        });
    }
}
