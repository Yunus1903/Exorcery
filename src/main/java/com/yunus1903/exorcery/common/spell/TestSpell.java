package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class TestSpell extends Spell
{
    public TestSpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "spell_test")
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

        //world.playSound(player, player.getPosition(), SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.NEUTRAL, 5, 5);
        //world.playSound(player, player.getPosition(), SoundHandler.SPELL_CHANTING, SoundCategory.VOICE, 5, 1);
        //world.playMovingSound(player, player, SoundHandler.SPELL_CHANTING, SoundCategory.VOICE, 1, 1);
        //ForgeEventFactory.onPlaySoundAtEntity(player, SoundHandler.SPELL_CHANTING, SoundCategory.VOICE, 5, 1);

        return super.onSpellCast(world, player);
    }
}
