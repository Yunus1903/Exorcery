package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.common.config.SpellConfig;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * @author Yunus1903
 * @since 03/05/2020
 */
public class FireballSpell extends Spell
{
    public FireballSpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "fireball")
                .setManaCost(SpellConfig.fireballManaCost)
                .setCastTime(SpellConfig.fireballCastTime)
                .setType(SpellType.FIRE)
                .setWhileRunning(true);
    }

    @Override
    protected ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        if (!world.isRemote())
        {
            Vec3d target = player.getLookVec();
            SmallFireballEntity smallFireballEntity = new SmallFireballEntity(
                    world,
                    player.getPosX() + target.x / 2,
                    player.getPosYEye() - 0.2,
                    player.getPosZ() + target.z / 2,
                    target.x,
                    target.y,
                    target.z
            );
            world.addEntity(smallFireballEntity);
        }
        return super.onSpellCast(world, player);
    }
}
