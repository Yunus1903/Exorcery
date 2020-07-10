package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.common.infusion.InfusionRecipeRegistry;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

import java.util.Objects;

/**
 * @author Yunus1903
 * @since 10/07/2020
 */
public class InfusionSpell extends Spell
{
    private Item item;

    public InfusionSpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "infusion")
                .setCastTime(SpellConfig.infusionCastTime)
                .setType(SpellType.MAGIC);
    }

    @Override
    public void calculateManaCost(PlayerEntity player)
    {
        ItemStack stack = player.getHeldItemMainhand();
        item = stack.getItem();
        setManaCost(InfusionRecipeRegistry.getManaCost(stack));
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        ItemStack stack = player.getHeldItemMainhand();
        Objects.requireNonNull(item, "Item is null");
        if (stack.getItem() != item)
        {
            Exorcery.LOGGER.error("Something went wrong!");
            return new ActionResult<>(ActionResultType.FAIL, this);
        }

        if (InfusionRecipeRegistry.isValidInput(stack))
        {
            ItemStack output = InfusionRecipeRegistry.getOutput(stack);
            stack.shrink(1);
            player.addItemStackToInventory(output);
        }

        return super.onSpellCast(world, player);
    }
}
