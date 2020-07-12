package com.yunus1903.exorcery.common.item;

import com.yunus1903.exorcery.common.capabilities.spells.ISpells;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsCapability;
import com.yunus1903.exorcery.common.capabilities.spells.SpellsProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncSpellsPacket;
import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Yunus1903
 * @since 15/04/2020
 */
public final class SpellScrollItem extends ExorceryItem
{
    private Spell spell;

    public SpellScrollItem()
    {
        super("spell_scroll_empty", new Item.Properties().maxStackSize(16));
    }

    public SpellScrollItem(Spell spell)
    {
        super("spell_scroll_" + spell.getRegistryName().getPath(), new Item.Properties().maxStackSize(1));
        this.spell = spell;
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
                playerIn.sendMessage(new TranslationTextComponent("chat.exorcery.spell_known"), Util.DUMMY_UUID);
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
            playerIn.sendMessage(new TranslationTextComponent("chat.exorcery.spell_learned"), Util.DUMMY_UUID);
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (spell != null)
        {
            tooltip.add(spell.getName().copyRaw().func_240699_a_(spell.getType().getColor()));
            if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT))
            {
                String translationKey = "spell." + spell.getRegistryName().getNamespace() + "." + spell.getRegistryName().getPath() + ".description";
                ITextComponent line1 = new TranslationTextComponent(translationKey + ".line1");
                ITextComponent line2 = new TranslationTextComponent(translationKey + ".line2");

                if (!line1.getString().equals(translationKey + ".line1") || line1.getString().isEmpty())
                {
                    tooltip.add(new StringTextComponent(""));
                    tooltip.add(line1.copyRaw().func_240699_a_(TextFormatting.GRAY));
                    if (!line2.getString().equals(translationKey + ".line2") || line2.getString().isEmpty())
                        tooltip.add(line2.copyRaw().func_240699_a_(TextFormatting.GRAY));
                }
            }
            else
            {
                tooltip.add(new StringTextComponent(""));
                tooltip.add(new TranslationTextComponent("gui.exorcery.spell_selector.spell_description").func_240699_a_(TextFormatting.GOLD));
            }
        }
    }

    public boolean hasSpell()
    {
        return spell != null;
    }
}
