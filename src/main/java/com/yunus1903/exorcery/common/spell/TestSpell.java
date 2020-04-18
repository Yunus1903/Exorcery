package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

/**
 * @author Yunus1903
 */
public class TestSpell extends Spell
{
    public TestSpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "test")
            .setManaCost(25F)
            .setCastTime(0)
            .setType(SpellType.DEBUG);
    }

    // Keep in mind, this runs both on the client and the server
    @Override
    public ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        Exorcery.LOGGER.debug("SpellTest is working!");

        if (world.isRemote())
        {
            Exorcery.LOGGER.debug("SpellCast on Logical Client");
            //world.addParticle(ParticleTypes., player.getPosX(), player.getPosY(), player.getPosZ(), 0, 0, 0);
        }

        return super.onSpellCast(world, player);
    }
}
