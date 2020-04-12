package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.PacketSyncMana;
import com.yunus1903.exorcery.common.network.packets.PacketSyncSpells;
import com.yunus1903.exorcery.common.spell.TestSpell;
import com.yunus1903.exorcery.common.spell.TestSpell2;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class EventHandler
{
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
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
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            ISpells spells = event.getPlayer().getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());
            IMana mana = event.getPlayer().getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());

            TestSpell testSpell = (TestSpell) ExorceryRegistry.SPELLS.getValue(new ResourceLocation(Exorcery.MOD_ID, "spell_test"));
            TestSpell2 testSpell2 = (TestSpell2) ExorceryRegistry.SPELLS.getValue(new ResourceLocation(Exorcery.MOD_ID, "spell_test_2"));
            spells.addSpell(testSpell);
            spells.addSpell(testSpell2);

            PacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new PacketSyncSpells(spells.getSpells()));
            PacketHandler.sendToPlayer((ServerPlayerEntity) event.getPlayer(), new PacketSyncMana(mana.get(), mana.getMax(), mana.getRegenerationRate()));
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            syncSpellsAndManaToClient((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            syncSpellsAndManaToClient((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.side == LogicalSide.SERVER)
        {
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(mana ->
            {
                if (mana.get() < mana.getMax() && player.server.getTickCounter() % 20 == 0)
                {
                    mana.set(mana.get() + mana.getRegenerationRate());
                    PacketHandler.sendToPlayer(player, new PacketSyncMana(mana.get(), mana.getMax(), mana.getRegenerationRate()));
                }

                //Exorcery.LOGGER.debug("Mana: " + mana.get());
            });
        }
    }

    private void syncSpellsAndManaToClient(ServerPlayerEntity player)
    {
        player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent((spells) ->
        {
            PacketHandler.sendToPlayer(player, new PacketSyncSpells(spells.getSpells()));
        });

        player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent((mana) ->
        {
            PacketHandler.sendToPlayer(player, new PacketSyncMana(mana.get(), mana.getMax(), mana.getRegenerationRate()));
        });
    }
}
