package com.yunus1903.exorcery.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ExorcerySpider - Yunus1903
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SmallSpiderModel<T extends Entity> extends EntityModel<T>
{
    public ModelRenderer Body;
    public ModelRenderer LegRF;
    public ModelRenderer LegRM;
    public ModelRenderer LegRB;
    public ModelRenderer LegLF;
    public ModelRenderer LegLM;
    public ModelRenderer LegLB;

    public SmallSpiderModel()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LegRF = new ModelRenderer(this, 8, 0);
        this.LegRF.setRotationPoint(-1.0F, 0.0F, -1.0F);
        this.LegRF.addBox(-2.0F, -0.25F, 0.0F, 2.5F, 0.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LegRF, 0.0F, -0.5235987755982988F, -0.3490658503988659F);
        this.LegLB = new ModelRenderer(this, 16, 0);
        this.LegLB.setRotationPoint(1.0F, 0.0F, 1.6F);
        this.LegLB.addBox(-2.0F, -0.25F, 0.0F, 2.5F, 0.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LegLB, 0.0F, 2.6179938779914944F, 0.3490658503988659F);
        this.LegLF = new ModelRenderer(this, 16, 0);
        this.LegLF.setRotationPoint(1.0F, 0.0F, -1.0F);
        this.LegLF.addBox(-2.0F, -0.25F, -0.91F, 2.5F, 0.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LegLF, 0.0F, -2.6179938779914944F, 0.3490658503988659F);
        this.LegLM = new ModelRenderer(this, 16, 0);
        this.LegLM.setRotationPoint(1.0F, 0.0F, 0.3F);
        this.LegLM.addBox(-2.5F, -0.25F, -0.5F, 2.6F, 0.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LegLM, 0.0F, -3.141592653589793F, 0.3490658503988659F);
        this.LegRB = new ModelRenderer(this, 8, 0);
        this.LegRB.setRotationPoint(-1.0F, 0.0F, 1.6F);
        this.LegRB.addBox(-2.0F, -0.25F, -1.0F, 2.5F, 0.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LegRB, 0.0F, 0.5235987755982988F, -0.3490658503988659F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.Body.addBox(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.LegRM = new ModelRenderer(this, 8, 0);
        this.LegRM.setRotationPoint(-1.0F, 0.0F, 0.3F);
        this.LegRM.addBox(-2.5F, -0.25F, -0.5F, 2.6F, 0.5F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LegRM, 0.0F, 0.0F, -0.3490658503988659F);
        this.Body.addChild(this.LegRF);
        this.Body.addChild(this.LegLB);
        this.Body.addChild(this.LegLF);
        this.Body.addChild(this.LegLM);
        this.Body.addChild(this.LegRB);
        this.Body.addChild(this.LegRM);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
    {
        ImmutableList.of(this.Body).forEach((modelRenderer) ->
        {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.LegRB.rotateAngleY = ((float)Math.PI / 4F);
        this.LegLB.rotateAngleY = (-(float)Math.PI / 4F);
        this.LegRM.rotateAngleY = ((float)Math.PI / 8F);
        this.LegLM.rotateAngleY = (-(float)Math.PI / 8F);
        this.LegRF.rotateAngleY = (-(float)Math.PI / 8F);
        this.LegLF.rotateAngleY = ((float)Math.PI / 8F);

        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;

        this.LegRB.rotateAngleY += f3;
        this.LegLB.rotateAngleY += -f3 - 135F;
        this.LegRM.rotateAngleY += f4;
        this.LegLM.rotateAngleY += -f4 - 135F;
        this.LegRF.rotateAngleY += f5;
        this.LegLF.rotateAngleY += -f5 - 135F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
