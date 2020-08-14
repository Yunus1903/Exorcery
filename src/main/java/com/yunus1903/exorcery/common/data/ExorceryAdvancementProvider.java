package com.yunus1903.exorcery.common.data;

import com.yunus1903.exorcery.common.ExorceryTags;
import com.yunus1903.exorcery.core.Exorcery;
import com.yunus1903.exorcery.init.ExorceryItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

/**
 * @author Yunus1903
 * @since 14/08/2020
 */
public class ExorceryAdvancementProvider extends AdvancementProvider
{
    public ExorceryAdvancementProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer)
    {
        {
            Advancement root = register(consumer, new ResourceLocation(Exorcery.MOD_ID, "exorcery/root"), Advancement.Builder.builder()
                    .withDisplay(ExorceryItems.SPELL_SCROLL_EMPTY,
                            new TranslationTextComponent("advancements.exorcery.root.title"),
                            new TranslationTextComponent("advancements.exorcery.root.description"),
                            new ResourceLocation("textures/block/andesite.png"),
                            FrameType.TASK,
                            true,
                            true,
                            false)
                    .withCriterion("has_spell_scroll", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ExorceryTags.Items.SPELL_SCROLLS).build())));

            Advancement mana = register(consumer, new ResourceLocation(Exorcery.MOD_ID, "exorcery/mana"), Advancement.Builder.builder()
                    .withParent(root)
                    .withDisplay(ExorceryItems.MANA_POTION,
                            new TranslationTextComponent("advancements.exorcery.mana.title"),
                            new TranslationTextComponent("advancements.exorcery.mana.description"),
                            null,
                            FrameType.TASK,
                            false,
                            false,
                            false)
                    .withCriterion("has_mana_potion", InventoryChangeTrigger.Instance.forItems(ExorceryItems.MANA_POTION)));
        }
    }
}
