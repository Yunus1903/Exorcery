package com.yunus1903.exorcery.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.command.arguments.SpellArgument;
import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncManaPacket;
import com.yunus1903.exorcery.common.network.packets.SyncSpellsPacket;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

/**
 * @author Yunus1903
 * @since 04/05/2020
 */
public final class ExorceryCommand
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        Exorcery.LOGGER.info("Registering commands");
        dispatcher.register(Commands.literal("exorcery")
                .requires(cs -> cs.hasPermissionLevel(2))
//                .requires(cs -> cs.getEntity() instanceof ServerPlayerEntity)
                .then(Give.register())
                .then(Take.register())
                .then(Mana.register())
        );
    }

    private static class Give
    {
        static ArgumentBuilder<CommandSource, ?> register()
        {
            return Commands.literal("give")
                    .requires(cs -> cs.hasPermissionLevel(2))
                    .then(Commands.argument("targets", EntityArgument.players())
                            .then(Commands.argument("spell", SpellArgument.spell())
                                    .executes(ctx ->
                                    {
                                        Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(ctx, "targets");
                                        Spell spell = SpellArgument.getSpell(ctx, "spell").getSpell();

                                        for (ServerPlayerEntity player : targets)
                                        {
                                            player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
                                            {
                                                if (!spells.getSpells().contains(spell))
                                                {
                                                    spells.addSpell(spell);
                                                    PacketHandler.sendToPlayer(player, new SyncSpellsPacket(spells.getSpells()));
                                                    player.sendMessage(new TranslationTextComponent("chat.exorcery.spell_learned"));
                                                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                                }
                                            });
                                        }
                                        return targets.size();
                                    })
                            )
                            .then(Commands.literal("all")
                                    .executes(ctx ->
                                    {
                                        Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(ctx, "targets");

                                        for (ServerPlayerEntity player : targets)
                                        {
                                            player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
                                            {
                                                for (Spell spell : ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).getValues())
                                                {
                                                    if (!spells.getSpells().contains(spell))
                                                    {
                                                        spells.addSpell(spell);
                                                        PacketHandler.sendToPlayer(player, new SyncSpellsPacket(spells.getSpells()));
                                                        player.sendMessage(new TranslationTextComponent("chat.exorcery.spell_learned"));
                                                        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                                    }
                                                }
                                            });
                                        }
                                        return targets.size();
                                    })
                            )
                    );
        }
    }

    private static class Take
    {
        static ArgumentBuilder<CommandSource, ?> register()
        {
            return Commands.literal("take")
                    .requires(cs -> cs.hasPermissionLevel(2))
                    .then(Commands.argument("targets", EntityArgument.players())
                            .then(Commands.argument("spell", SpellArgument.spell())
                                    .executes(ctx ->
                                    {
                                        Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(ctx, "targets");
                                        Spell spell = SpellArgument.getSpell(ctx, "spell").getSpell();

                                        for (ServerPlayerEntity player : targets)
                                        {
                                            player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
                                            {
                                                if (spells.getSpells().contains(spell))
                                                {
                                                    spells.getSpells().remove(spell);
                                                    PacketHandler.sendToPlayer(player, new SyncSpellsPacket(spells.getSpells()));
                                                    player.sendMessage(new TranslationTextComponent("chat.exorcery.forgotten"));
                                                }
                                            });
                                        }
                                        return targets.size();
                                    })
                            )
                            .then(Commands.literal("all")
                                    .executes(ctx ->
                                    {
                                        Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(ctx, "targets");

                                        for (ServerPlayerEntity player : targets)
                                        {
                                            player.getCapability(SpellsProvider.SPELLS_CAPABILITY).ifPresent(spells ->
                                            {
                                                for (Spell spell : ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).getValues())
                                                {
                                                    if (spells.getSpells().contains(spell))
                                                    {
                                                        spells.getSpells().remove(spell);
                                                        PacketHandler.sendToPlayer(player, new SyncSpellsPacket(spells.getSpells()));
                                                        player.sendMessage(new TranslationTextComponent("chat.exorcery.forgotten"));
                                                    }
                                                }
                                            });
                                        }
                                        return targets.size();
                                    })
                            )
                    );
        }
    }

    private static class Mana
    {
        static ArgumentBuilder<CommandSource, ?> register()
        {
            return Commands.literal("mana")
                    .requires(cs -> cs.hasPermissionLevel(2))
                    .then(Commands.argument("targets", EntityArgument.players())
                            .then(Commands.literal("set")
                                    .then(Commands.argument("mana", FloatArgumentType.floatArg(0F))
                                            .executes(ctx ->
                                            {
                                                Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(ctx, "targets");
                                                float mana = FloatArgumentType.getFloat(ctx, "mana");

                                                for (ServerPlayerEntity player : targets)
                                                {
                                                    player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(manaCap ->
                                                    {
                                                        manaCap.set(mana);
                                                        PacketHandler.sendToPlayer(player, new SyncManaPacket(manaCap.get(), manaCap.getMax(), manaCap.getRegenerationRate()));
                                                    });
                                                }
                                                return targets.size();
                                            })
                                    )
                            )
                            .then(Commands.literal("reset")
                                    .executes(ctx ->
                                    {
                                        Collection<ServerPlayerEntity> targets = EntityArgument.getPlayers(ctx, "targets");

                                        for (ServerPlayerEntity player : targets)
                                        {
                                            player.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(manaCap ->
                                            {
                                                manaCap.set(manaCap.getMax());
                                                PacketHandler.sendToPlayer(player, new SyncManaPacket(manaCap.get(), manaCap.getMax(), manaCap.getRegenerationRate()));
                                            });
                                        }
                                        return targets.size();
                                    })
                            )
                    );
        }
    }
}
