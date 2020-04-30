package com.yunus1903.exorcery.common.item;

import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncSpellsPacket;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Yunus1903
 * @since 15/04/2020
 */
public final class SpellScrollItem extends Item
{
    private Spell spell;

    public SpellScrollItem()
    {
        super(new Item.Properties()
                .group(ItemGroup.MISC)
                .maxStackSize(16)
        );
        setRegistryName(Exorcery.MOD_ID, "spell_scroll_empty");
    }

    public SpellScrollItem(Spell spell)
    {
        super(new Item.Properties()
                .group(ItemGroup.MISC)
                .maxStackSize(1)
        );
        this.spell = spell;
        setRegistryName(Exorcery.MOD_ID, "spell_scroll_" + spell.getRegistryName().getPath());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (spell == null) return ActionResult.resultPass(itemStack);

        ISpells spells = playerIn.getCapability(SpellsProvider.SPELLS_CAPABILITY).orElse(new SpellsCapability());

        if (spells.getSpells().contains(spell))
        {
            if (worldIn.isRemote)
            {
                playerIn.sendMessage(new TranslationTextComponent("chat.exorcery.spell_known"));
            }
            return new ActionResult<>(ActionResultType.FAIL, itemStack);
        }

        if (!worldIn.isRemote() && playerIn instanceof  ServerPlayerEntity)
        {
            spells.addSpell(spell);
            PacketHandler.sendToPlayer((ServerPlayerEntity) playerIn, new SyncSpellsPacket(spells.getSpells()));

            if (!playerIn.isCreative()) playerIn.inventory.deleteStack(itemStack);
        }
        else
        {
            playerIn.sendMessage(new TranslationTextComponent("chat.exorcery.spell_learned"));
            playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        }
        return new ActionResult<>(ActionResultType.CONSUME, itemStack);
    }

    @Override
    protected String getDefaultTranslationKey()
    {
        if (spell == null) return super.getDefaultTranslationKey();
        return "item.exorcery.spell_scroll";
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (spell != null) tooltip.add(spell.getName().applyTextStyle(spell.getType().getColor()));
    }
}
