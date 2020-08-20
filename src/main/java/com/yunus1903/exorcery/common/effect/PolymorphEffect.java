package com.yunus1903.exorcery.common.effect;

import com.yunus1903.exorcery.common.capabilities.morph.IMorph;
import com.yunus1903.exorcery.common.capabilities.morph.MorphCapability;
import com.yunus1903.exorcery.common.capabilities.morph.MorphProvider;
import com.yunus1903.exorcery.common.network.PacketHandler;
import com.yunus1903.exorcery.common.network.packets.SyncMorphPacket;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * {@link Effect} that causes you to morph into a {@link EntityType entity}
 * @author Yunus1903
 * @since 06/05/2020
 */
public class PolymorphEffect extends Effect
{
    private final EntityType<? extends LivingEntity> entityType;

    public PolymorphEffect(EntityType<? extends LivingEntity> entityType)
    {
        this(entityType, 0x000000);
    }

    public PolymorphEffect(EntityType<? extends LivingEntity> entityType, int color)
    {
        super(EffectType.NEUTRAL, color);
        this.setRegistryName(Exorcery.MOD_ID, "polymorph_" + entityType.getRegistryName().getPath());
        this.entityType = entityType;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (!entityLivingBaseIn.world.isRemote())
        {
            IMorph morph = entityLivingBaseIn.world.getCapability(MorphProvider.MORPH_CAPABILITY).orElse(new MorphCapability());

            if (entityLivingBaseIn.getActivePotionEffect(this).getDuration() <= 1)
            {
                morph.stopMorph(entityLivingBaseIn);
            }
            else
            {
                morph.morph(entityLivingBaseIn, entityType);
            }

            PacketHandler.sendToNearby(entityLivingBaseIn.world, entityLivingBaseIn, new SyncMorphPacket(morph.getMorphedEntities()));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return Exorcery.instance.server != null;
    }

    @Override
    protected String getOrCreateDescriptionId()
    {
        return "effect.exorcery.polymorph";
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return (new TranslationTextComponent(entityType.getTranslationKey())).appendString(" ").append(new TranslationTextComponent(getName()));
    }

    public EntityType<? extends LivingEntity> getEntityType()
    {
        return entityType;
    }
}
