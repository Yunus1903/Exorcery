package com.yunus1903.exorcery.common.item;

import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.MagicalPotionEntityInteractPacket;
import com.yunus1903.exorcery.init.ExorceryPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Yunus1903
 * @since 09/07/2020
 */
public class MagicalPotionItem extends ExorceryItem
{
    public MagicalPotionItem()
    {
        super("magical_potion", new Item.Properties().maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            RayTraceResult result = Minecraft.getInstance().objectMouseOver;
            if (result.getType() == RayTraceResult.Type.ENTITY && result instanceof EntityRayTraceResult && ((EntityRayTraceResult) result).getEntity() instanceof LivingEntity)
            {
                PacketHandler.sendToServer(new MagicalPotionEntityInteractPacket(((EntityRayTraceResult) result).getEntity().getEntityId()));
            }
        });

        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        if (entityLiving instanceof PlayerEntity)
        {
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
        tooltip.add(new TranslationTextComponent("item.exorcery.magical_potion.tooltip.line1").applyTextStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("item.exorcery.magical_potion.tooltip.line2").applyTextStyle(TextFormatting.GRAY));
    }

    public static void entityInteract(ItemStack stack, ServerPlayerEntity player, Entity target)
    {
        if (target instanceof CowEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_COW);
        else if (target instanceof CreeperEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_CREEPER);
        else if (target instanceof HorseEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_HORSE);
        else if (target instanceof LlamaEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_LLAMA);
        else if (target instanceof PandaEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_PANDA);
        else if (target instanceof PigEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_PIG);
        else if (target instanceof PolarBearEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_POLAR_BEAR);
        else if (target instanceof SheepEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_SHEEP);
        else if (target instanceof SkeletonEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_SKELETON);
        else if (target instanceof VillagerEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_VILLAGER);
        else if (target instanceof ZombieEntity) makeMorphPotion(stack, player, target, ExorceryPotions.POLYMORPH_ZOMBIE);
    }

    private static void makeMorphPotion(ItemStack stack, ServerPlayerEntity player, Entity target, Potion potion)
    {
        stack.shrink(1);
        player.addItemStackToInventory(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion));
        target.remove();
    }
}
