package com.yunus1903.exorcery.common.spell;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class TestSpell extends Spell
{
    public TestSpell()
    {
        this.setRegistryName(Exorcery.MOD_ID, "spell_test")
            .setManaCost(25F)
            .setCastTime(100)
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
        }

        world.playSound(player, player.getPosition(), SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.NEUTRAL, 5, 5);

        return super.onSpellCast(world, player);
    }
}
