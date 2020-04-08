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
            .setManaCost(25f)
            .setCastTime(0)
            .setType(SpellType.DEBUG);
    }

    @Override
    public ActionResult<Spell> onSpellCast(World world, PlayerEntity player)
    {
        Exorcery.LOGGER.debug("SpellTest is working!");

        world.playSound(player, player.getPosition(), SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.NEUTRAL, 5, 5);

        if (world.isRemote())
        {
            Exorcery.LOGGER.debug("SpellCast on Logical Client");
        }

        return super.onSpellCast(world, player);
    }
}
