package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Yunus1903
 * @since 13/04/2020
 */
public class TeleportSpell extends Spell
{
    public TeleportSpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "teleport")
                .setCastTime(SpellConfig.teleportCastTime)
                .setType(SpellType.ENDER); // TODO: add ender particles
    }

    @Override
    public void calculateManaCost(PlayerEntity player)
    {
        if (targetLocation == null) setManaCost(Float.MAX_VALUE);
        else setManaCost((float) Math.sqrt(player.getDistanceSq(new Vec3d(targetLocation))) * SpellConfig.teleportManaCostMultiplier);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setTargetEntity(Minecraft mc)
    {
        RayTraceResult result = mc.player.pick(67, mc.getRenderPartialTicks(), true);

        if (result.getType() == RayTraceResult.Type.BLOCK && result instanceof BlockRayTraceResult)
        {
            // TODO: find better way of doing this

            targetLocation = ((BlockRayTraceResult) result).getPos();
            if (!mc.world.isAirBlock(targetLocation)) targetLocation = targetLocation.add(0, 1, 0);
            if (!mc.world.isAirBlock(targetLocation)) targetLocation = targetLocation.add(0, -2, 0);
            if (!mc.world.isAirBlock(targetLocation)) targetLocation = targetLocation.add(0, 1, 0);
        }
        else targetLocation = null;
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        if (targetLocation != null)
        {
            if (!world.isRemote())
            {
                player.setPositionAndUpdate(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ());
                world.playMovingSound(null, player, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
            }
            return super.onSpellCast(world, player);
        }
        return new ActionResult<>(ActionResultType.FAIL, this);
    }
}
