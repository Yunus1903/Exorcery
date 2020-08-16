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

    public Spell(Properties properties)
    {
        this.castTime = properties.castTime;
        this.type = properties.type;
        this.whileRunning = properties.whileRunning;
    }

    public float getManaCost()
    {
        return manaCost;
    }

    public Spell setManaCost(float cost)
    {
        manaCost = cost >= 0 ? cost : 0;
        return this;
    }

    public int getCastTime()
    {
        return castTime;
    }

    public SpellType getType()
    {
        return type;
    }

    public boolean getWhileRunning()
    {
        return whileRunning;
    }

    public void calculateManaCost(PlayerEntity player) { }

    @Nullable
    public SpellTarget getTarget()
    {
        return target;
    }

    public void setTarget(@Nullable SpellTarget target)
    {
        this.target = target;
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public SpellTarget determineTarget(Minecraft mc) { return null; }

    @OnlyIn(Dist.CLIENT)
    public ITextComponent getName()
    {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    @Override
    public String toString()
    {
        return ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).getKey(this).getPath();
    }

    public String getTranslationKey()
    {
        if (this.translationKey == null)
        {
            this.translationKey = Util.makeTranslationKey("spell", getRegistryName());
        }
        return this.translationKey;
    }

    public boolean castSpell(World world, PlayerEntity player)
    {
        return castSpell(world, player, null, false);
    }

    public final boolean castSpell(World world, PlayerEntity player, @Nullable SpellTarget target)
    {
        return castSpell(world, player, target, false);
    }

    public final boolean castSpell(World world, PlayerEntity player, boolean bypassManaAndSync)
    {
        return castSpell(world, player, null, bypassManaAndSync);
    }

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

    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        if (type == SpellType.ENDER)
        {
            world.setEntityState(player, (byte) 46);
        }

        player.swingArm(Hand.MAIN_HAND);
        return new ActionResult<>(ActionResultType.PASS, this);
    }

    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player, @Nullable SpellTarget target)
    {
        return onSpellCast(world, player);
    }

    public static int getIdFromSpell(Spell spell)
    {
        ForgeRegistry<Spell> registry = ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS);
        int id = registry.getID(spell);
        return spell == null ? 0 : id;
    }

    public static Spell getSpellById(int id)
    {
        return ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).getValue(id);
    }

    protected static class Properties
    {
        private int castTime = 0;
        private SpellType type = SpellType.NORMAL;
        private boolean whileRunning = false;

        public Properties castTime(int castTime)
        {
            this.castTime = castTime;
            return this;
        }

        public Properties type(SpellType type)
        {
            this.type = type;
            return this;
        }

        public Properties castableWhileRunning()
        {
            whileRunning = true;
            return this;
        }
    }
}
