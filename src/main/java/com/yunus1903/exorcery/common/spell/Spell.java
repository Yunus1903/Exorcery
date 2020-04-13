package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.CastSpellPacket;
import com.yunus1903.exorcery.common.network.packets.SyncManaPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistry;

import javax.annotation.Nullable;

public abstract class Spell extends net.minecraftforge.registries.ForgeRegistryEntry<Spell>
{
    private float manaCost = 0f;
    private int castTime = 0;
    private SpellType type = SpellType.NORMAL;
    @Nullable
    private String translationKey;

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

    public Spell setManaCost(float cost)
    {
        manaCost = cost;
        return this;
    }

    public float getManaCost()
    {
        return manaCost;
    }

    public Spell setCastTime(int time)
    {
        castTime = time;
        return this;
    }

    public Spell setType(SpellType type)
    {
        this.type = type;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public ITextComponent getName()
    {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    @Override
    public String toString()
    {
        return ExorceryRegistry.SPELLS.getKey(this).getPath();
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
        return castSpell(world, player, false);
    }

    public final boolean castSpell(World world, PlayerEntity player, boolean bypassManaAndSync)
    {
        if (bypassManaAndSync)
        {
            onSpellCast(world, player);
            return true;
        }

        if (player instanceof ServerPlayerEntity)
        {
            if (player.isCreative())
            {
                PacketHandler.sendToPlayer((ServerPlayerEntity) player, new CastSpellPacket(this, player));
                onSpellCast(world, player);
                return true;
            }

            IMana mana = player.getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());

            if (mana.get() >= manaCost)
            {
                PacketHandler.sendToPlayer((ServerPlayerEntity) player, new SyncManaPacket(mana.reduce(manaCost), mana.getMax(), mana.getRegenerationRate()));
                PacketHandler.sendToPlayer((ServerPlayerEntity) player, new CastSpellPacket(this, player));
                onSpellCast(world, player);
                return true;
            }
            return false;
        }
        else
        {
            PacketHandler.sendToServer(new CastSpellPacket(this, player));
            return false;
        }
    }

    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        return new ActionResult<>(ActionResultType.PASS, this);
    }
}
