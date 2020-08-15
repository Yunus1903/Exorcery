package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.common.util.BlockSpellTarget;
import com.yunus1903.exorcery.common.util.SpellTarget;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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
                .setType(SpellType.ENDER);
    }

    @Override
    public void calculateManaCost(PlayerEntity player)
    {
        if (getTarget() != null && getTarget().getType() == SpellTarget.Type.BLOCK)
        {
            BlockSpellTarget target = (BlockSpellTarget) getTarget();
            setManaCost((float) Math.sqrt(player.getDistanceSq(target.getPos().getX(), target.getPos().getY(), target.getPos().getZ())) * SpellConfig.teleportManaCostMultiplier);
        }
        else setManaCost(Float.MAX_VALUE);
    }

    @Nullable
    @Override
    public SpellTarget determineTarget(Minecraft mc)
    {
        RayTraceResult result = mc.player.pick(67, mc.getRenderPartialTicks(), true);

        if (result.getType() == RayTraceResult.Type.BLOCK)
        {
            // TODO: find better way of doing this

            BlockPos target = ((BlockRayTraceResult) result).getPos();
            if (!mc.world.isAirBlock(target)) target = target.add(0, 1, 0);
            if (!mc.world.isAirBlock(target)) target = target.add(0, -2, 0);
            if (!mc.world.isAirBlock(target)) target = target.add(0, 1, 0);
            return new BlockSpellTarget(((BlockRayTraceResult) result).getFace(), target);
        }
        return new BlockSpellTarget(true, null, null);
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        if (getTarget() != null && getTarget().getType() == SpellTarget.Type.BLOCK)
        {
            BlockSpellTarget target = (BlockSpellTarget) getTarget();
            world.setEntityState(player, (byte) 46);
            if (!world.isRemote())
            {
                player.setPositionAndUpdate(target.getPos().getX(), target.getPos().getY(), target.getPos().getZ());
                world.playMovingSound(null, player, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
            }
            return super.onSpellCast(world, player);
        }
        return new ActionResult<>(ActionResultType.FAIL, this);
    }
}
