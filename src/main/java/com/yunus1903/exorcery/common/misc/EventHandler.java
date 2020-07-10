package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.common.ExorceryTags;
import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncManaPacket;
import com.yunus1903.exorcery.common.network.packets.SyncSpellsPacket;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Yunus1903
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID)
public final class EventHandler
{
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        PlayerEntity oldPlayer = event.getOriginal();
        PlayerEntity newPlayer = event.getPlayer();

        if (event.isWasDeath() && newPlayer instanceof ServerPlayerEntity)
        {
            oldPlayer.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(oldSpells ->
            {
                ISpells newSpells = newPlayer.getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());
                newSpells.setSpells(oldSpells.getSpells());
            });

            oldPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(oldMana ->
            {
                IMana newMana = newPlayer.getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());
                newMana.set(oldMana.getMax());
                newMana.setMax(oldMana.getMax());
                newMana.setRegenerationRate(oldMana.getRegenerationRate());
            });

            syncSpellsAndManaToClient((ServerPlayerEntity) newPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            ISpells spells = event.getPlayer().getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());
            IMana mana = event.getPlayer().getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());

            syncSpellsAndManaToClient((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            syncSpellsAndManaToClient((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            syncSpellsAndManaToClient((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event)
    {
        if (ItemTags.getCollection().getOrCreate(new ResourceLocation(Exorcery.MOD_ID, "spell_scrolls")).contains(event.getLeft().getItem()) && event.getRight().getItem() == ExorceryItems.SPELL_SCROLL_EMPTY)
        {
            event.setOutput(new ItemStack(event.getLeft().getItem()));
            event.setCost(5);
            event.setMaterialCost(1);
        }
    }

    @SubscribeEvent
    public static void onAnvilRepair(AnvilRepairEvent event)
    {
        if (ExorceryTags.Items.SPELL_SCROLLS.contains(event.getItemResult().getItem()))
        {
            event.getPlayer().inventory.addItemStackToInventory(event.getItemInput());
        }
    }

    private static void syncSpellsAndManaToClient(ServerPlayerEntity player)
    {
        player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent((spells) ->
        {
            PacketHandler.sendToPlayer(player, new SyncSpellsPacket(spells.getSpells()));
        });

        player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent((mana) ->
        {
            PacketHandler.sendToPlayer(player, new SyncManaPacket(mana.get(), mana.getMax(), mana.getRegenerationRate()));
        });
    }
}
