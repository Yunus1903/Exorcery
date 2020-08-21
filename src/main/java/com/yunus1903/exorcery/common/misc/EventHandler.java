package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.common.ExorceryTags;
import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.morph.MorphProvider;
import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.command.ExorceryCommand;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncCastingPacket;
import com.yunus1903.exorcery.common.network.packets.SyncManaPacket;
import com.yunus1903.exorcery.common.network.packets.SyncMorphPacket;
import com.yunus1903.exorcery.common.network.packets.SyncSpellsPacket;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Event handler class
 * @author Yunus1903
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID)
public final class EventHandler
{
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        ExorceryCommand.register(event.getDispatcher());
    }

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

            syncCapabilities((ServerPlayerEntity) newPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            event.getPlayer().getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());
            event.getPlayer().getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());

            syncCapabilities((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            syncCapabilities((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity)
        {
            syncCapabilities((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event)
    {
        if (ExorceryTags.Items.SPELL_SCROLLS.contains(event.getLeft().getItem()) && event.getRight().getItem() == ExorceryItems.SPELL_SCROLL_EMPTY)
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

    @SubscribeEvent
    public static void onEntityEyeHeightChange(EntityEvent.EyeHeight event)
    {
        if (event.getEntity() instanceof LivingEntity)
        {
            LivingEntity originalEntity = (LivingEntity) event.getEntity();

            originalEntity.world.getCapability(MorphProvider.MORPH_CAPABILITY).ifPresent(morph ->
            {
                if (morph.isMorphed(originalEntity))
                {
                    EntityType<? extends LivingEntity> entityType = morph.getMorphedEntityType(originalEntity);
                    if (entityType == null) return;
                    LivingEntity entity = entityType.create(originalEntity.world);
                    if (entity == null) return;
                    event.setNewHeight(entity.getEyeHeight());
                }
            });
        }
    }

    private static void syncCapabilities(ServerPlayerEntity player)
    {
        player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells -> PacketHandler.sendToPlayer(player, new SyncSpellsPacket(spells.getSpells())));
        player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(mana -> PacketHandler.sendToPlayer(player, new SyncManaPacket(mana.get(), mana.getMax(), mana.getRegenerationRate())));
        player.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting -> PacketHandler.sendToPlayer(player, new SyncCastingPacket(casting.isCasting(), casting.getSpell())));
        player.world.getCapability(MorphProvider.MORPH_CAPABILITY).ifPresent(morph -> PacketHandler.sendToPlayer(player, new SyncMorphPacket(morph.getMorphedEntities())));
    }
}
