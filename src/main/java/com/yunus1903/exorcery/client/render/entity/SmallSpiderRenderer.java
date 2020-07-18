package com.yunus1903.exorcery.client.render.entity;

import com.yunus1903.exorcery.client.render.entity.model.SmallSpiderModel;
import com.yunus1903.exorcery.common.entity.SmallSpiderEntity;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Yunus1903
 * @since 18/07/2020
 */
@OnlyIn(Dist.CLIENT)
public class SmallSpiderRenderer<T extends SmallSpiderEntity> extends MobRenderer<T, SmallSpiderModel<T>>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Exorcery.MOD_ID, "textures/entity/small_spider.png");

    public SmallSpiderRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new SmallSpiderModel<>(), 0.28F);
    }

    @Override
    protected float getDeathMaxRotation(T entityLivingBaseIn)
    {
        return 180.0F;
    }

    @Override
    public ResourceLocation getEntityTexture(SmallSpiderEntity entity)
    {
        return TEXTURE;
    }
}
