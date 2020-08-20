package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.capabilities.casting.CastingProvider;
import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.misc.SoundHandler;
import com.yunus1903.exorcery.common.misc.TickHandler;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.CastSpellPacket;
import com.yunus1903.exorcery.common.network.packets.SyncCastingPacket;
import com.yunus1903.exorcery.common.network.packets.SyncManaPacket;
import com.yunus1903.exorcery.common.util.SpellTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistry;

import javax.annotation.Nullable;

/**
 * Abstract spell class, all spells are a instance of this class
 * @author Yunus1903
 */
public abstract class Spell extends net.minecraftforge.registries.ForgeRegistryEntry<Spell>
{
    private final int castTime;
    private final SpellType type;
    private final boolean whileRunning;

    private float manaCost = 0.0F;
    @Nullable
    private String translationKey;
    private boolean isCasting = false;
    @Nullable
    private SpellTarget target;

    /**
     * Constructor for the {@link Spell spell} instance
     * @param properties Instance of {@link Spell.Properties}
     */
    public Spell(Spell.Properties properties)
    {
        this.castTime = properties.castTime;
        this.type = properties.type;
        this.whileRunning = properties.whileRunning;
    }

    /**
     * @return The amount of mana it costs to cast this {@link Spell spell}
     */
    public float getManaCost()
    {
        return manaCost;
    }

    /**
     * @param cost The amount of mana required to cast this {@link Spell spell} as a {@link Float float}
     * @return Instance of {@link Spell spell}
     */
    public Spell setManaCost(float cost)
    {
        manaCost = cost >= 0 ? cost : 0;
        return this;
    }

    /**
     * @return The amount of time required to cast this {@link Spell spell}
     */
    public int getCastTime()
    {
        return castTime;
    }

    /**
     * @return The {@link SpellType type} of this {@link Spell spell}
     */
    public SpellType getType()
    {
        return type;
    }

    /**
     * @return {@code true} if this {@link Spell spell} can be casted while the {@link PlayerEntity player} is running
     */
    public boolean getWhileRunning()
    {
        return whileRunning;
    }

    /**
     * Calculated the {@link Spell#manaCost}
     * @param player Instance of the {@link PlayerEntity player}
     */
    public void calculateManaCost(PlayerEntity player) { }

    /**
     * @return A instance of {@link SpellTarget} if this {@link Spell spell} has a target, otherwise returns {@code null}
     */
    @Nullable
    public SpellTarget getTarget()
    {
        return target;
    }

    /**
     * Sets the {@link SpellTarget target} of this {@link Spell spell}
     * @param target Instance of {@link SpellTarget}
     */
    public void setTarget(@Nullable SpellTarget target)
    {
        this.target = target;
    }

    /**
     * Client-side only<br>
     * Determine the target for this {@link Spell spell}
     * @param mc Instance of {@link Minecraft}
     * @return Instance of {@link SpellTarget} if there's a target, otherwise returns {@code null}
     */
    @OnlyIn(Dist.CLIENT)
    @Nullable
    public SpellTarget determineTarget(Minecraft mc) { return null; }

    /**
     * Client-side only
     * @return Instance of {@link ITextComponent} with the name of this {@link Spell spell}
     */
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getName()
    {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    /**
     * @return Registry name of this {@link Spell spell}
     */
    @Override
    public String toString()
    {
        return ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).getKey(this).getPath();
    }

    /**
     * @return The {@link Spell#translationKey translation key} for the name of this {@link Spell spell}
     */
    public String getTranslationKey()
    {
        if (this.translationKey == null)
        {
            this.translationKey = Util.makeTranslationKey("spell", getRegistryName());
        }
        return this.translationKey;
    }

    /**
     * Cast this {@link Spell spell}
     * @param world Instance of the current {@link World world}
     * @param player Instance of the {@link PlayerEntity caster}
     * @return {@code true} if cast was successful
     */
    public boolean castSpell(World world, PlayerEntity player)
    {
        return castSpell(world, player, null, false);
    }

    /**
     * Cast this {@link Spell spell}
     * @param world Instance of the current {@link World world}
     * @param player Instance of the {@link PlayerEntity caster}
     * @param target Instance of this {@link Spell spell's} {@link SpellTarget target}
     * @return {@code true} if cast was successful
     */
    public final boolean castSpell(World world, PlayerEntity player, @Nullable SpellTarget target)
    {
        return castSpell(world, player, target, false);
    }

    /**
     * Cast this {@link Spell spell}
     * @param world Instance of the current {@link World world}
     * @param player Instance of the {@link PlayerEntity caster}
     * @param bypassManaAndSync Bypass mana check (Used when server tells client to cast)
     * @return {@code true} if cast was successful
     */
    public final boolean castSpell(World world, PlayerEntity player, boolean bypassManaAndSync)
    {
        return castSpell(world, player, null, bypassManaAndSync);
    }

    /**
     * Cast this {@link Spell spell}
     * @param world Instance of the current {@link World world}
     * @param player Instance of the {@link PlayerEntity caster}
     * @param target Instance of this {@link Spell spell's} {@link SpellTarget target}
     * @param bypassManaAndSync Bypass mana check (Used when server tells client to cast)
     * @return {@code true} if cast was successful
     */
    public final boolean castSpell(World world, PlayerEntity player, @Nullable SpellTarget target, boolean bypassManaAndSync)
    {
        if (isCasting) return false;

        calculateManaCost(player);

        if (bypassManaAndSync)
        {
            //This runs on client if server tells client to cast

            onSpellCast(world, player, target); // Client-Side
            return true;
        }

        if (player instanceof ServerPlayerEntity)
        {
            // This runs if on server when it receives cast action from client

            setTarget(target);

            if (manaCost == Float.MAX_VALUE) return false;

            IMana mana = player.getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());

            if (mana.get() >= manaCost || player.isCreative())
            {
                if (!player.isCreative()) PacketHandler.sendToPlayer((ServerPlayerEntity) player, new SyncManaPacket(mana.reduce(manaCost), mana.getMax(), mana.getRegenerationRate()));

                player.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
                {
                    isCasting = casting.startCasting(this);
                    PacketHandler.sendToPlayer((ServerPlayerEntity) player, new SyncCastingPacket(casting.isCasting(), casting.getSpell()));
                });

                if (castTime > 0)
                {
                    world.playMovingSound(null, player, SoundHandler.SPELL_CHANTING, SoundCategory.VOICE, 1, 1);
                }

                TickHandler.scheduleTask(world.getServer().getTickCounter() + castTime, () ->
                {
                    player.getCapability(CastingProvider.CASTING_CAPABILITY).ifPresent(casting ->
                    {
                        isCasting = casting.isCasting();
                        if (isCasting)
                        {
                            casting.stopCasting();
                            PacketHandler.sendToPlayer((ServerPlayerEntity) player, new SyncCastingPacket(casting.isCasting(), casting.getSpell()));
                        }
                    });

                    SoundHandler.stopChanting((ServerPlayerEntity) player);

                    if (!isCasting) return;
                    isCasting = false;

                    PacketHandler.sendToPlayer((ServerPlayerEntity) player, new CastSpellPacket(this, player));
                    onSpellCast(world, player, target); // Server-Side
                });
                return true;
            }
            return false;
        }
        else
        {
            // This runs on client when it receives cast action

            PacketHandler.sendToServer(new CastSpellPacket(this, player, target));
            return false;
        }
    }

    /**
     * Called when {@link Spell spell} has been casted
     * @param world Instance of the {@link World world} the {@link PlayerEntity player} is in
     * @param player The {@link PlayerEntity player} that has casted the {@link Spell spell}
     * @return The {@link ActionResult} of the cast
     */
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        if (type == SpellType.ENDER)
        {
            world.setEntityState(player, (byte) 46);
        }

        player.swingArm(Hand.MAIN_HAND);
        return new ActionResult<>(ActionResultType.PASS, this);
    }

    /**
     * Called when {@link Spell spell} has been casted
     * @param world Instance of the {@link World world} the {@link PlayerEntity player} is in
     * @param player The {@link PlayerEntity player} that has casted the {@link Spell spell}
     * @param target Instance of the {@link SpellTarget target} of the {@link Spell spell}, {@code null} if there is no target
     * @return The {@link ActionResult} of the cast
     */
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player, @Nullable SpellTarget target)
    {
        return onSpellCast(world, player);
    }

    /**
     * @param spell Instance of {@link Spell}
     * @return The registry ID of the {@link Spell spell}
     */
    public static int getIdFromSpell(Spell spell)
    {
        ForgeRegistry<Spell> registry = ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS);
        int id = registry.getID(spell);
        return spell == null ? 0 : id;
    }

    /**
     * @param id Registry ID of the {@link Spell}
     * @return A instance of the corresponding {@link Spell spell}
     */
    public static Spell getSpellById(int id)
    {
        return ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).getValue(id);
    }

    /**
     * Properties for {@link Spell spell}
     */
    protected static class Properties
    {
        private int castTime = 0;
        private SpellType type = SpellType.NORMAL;
        private boolean whileRunning = false;

        /**
         * Sets the {@link Spell.Properties#castTime}
         * @param castTime The amount of time required to cast the {@link Spell spell}
         * @return Instance of this {@link Spell.Properties property}
         */
        public Properties castTime(int castTime)
        {
            this.castTime = castTime;
            return this;
        }

        /**
         * Sets the {@link Spell.Properties#type}
         * @param type The {@link SpellType type} of the {@link Spell spell}
         * @return Instance of this {@link Spell.Properties property}
         */
        public Properties type(SpellType type)
        {
            this.type = type;
            return this;
        }

        /**
         * Makes the {@link Spell spell} castable while running
         * Sets {@link Spell.Properties#whileRunning} to {@code true}
         * @return Instance of this {@link Spell.Properties property}
         */
        public Properties castableWhileRunning()
        {
            whileRunning = true;
            return this;
        }
    }
}
