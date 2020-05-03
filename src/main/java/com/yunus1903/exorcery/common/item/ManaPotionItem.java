package com.yunus1903.exorcery.common.item;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yunus1903.exorcery.common.capabilities.mana.IMana;
import com.yunus1903.exorcery.common.capabilities.mana.ManaCapability;
import com.yunus1903.exorcery.common.capabilities.mana.ManaProvider;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Yunus1903
 * @since 26/04/2020
 */
public class ManaPotionItem extends Item
{
    private final float MANA = 200F;

    public ManaPotionItem()
    {
        super(new Item.Properties()
                .group(ItemGroup.BREWING)
                .maxStackSize(1)
        );
        setRegistryName(Exorcery.MOD_ID, "mana_potion");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        if (entityLiving instanceof PlayerEntity)
        {
            IMana mana = entityLiving.getCapability(ManaProvider.MANA_CAPABILITY).orElse(new ManaCapability());

            if (mana.get() >= mana.getMax()) return stack;

            mana.add(MANA);

            if (!((PlayerEntity) entityLiving).isCreative())
            {
                stack.shrink(1);
                return new ItemStack(Items.GLASS_BOTTLE);
            }
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 32;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent((int) MANA + " Mana").applyTextStyle(TextFormatting.GRAY));
    }
}
