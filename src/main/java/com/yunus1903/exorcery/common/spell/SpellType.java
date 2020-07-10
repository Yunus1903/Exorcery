package com.yunus1903.exorcery.common.spell;

import net.minecraft.util.text.TextFormatting;

/**
 * @author Yunus1903
 */
public enum SpellType
{
    NORMAL("normal", TextFormatting.WHITE, false),
    NATURAL("natural", TextFormatting.DARK_GREEN, false),
    FIRE("fire", TextFormatting.DARK_RED, true),
    ENDER("ender", TextFormatting.DARK_PURPLE, true),
    ICE("ice", TextFormatting.AQUA, false),
    MAGIC("magic", TextFormatting.LIGHT_PURPLE, true);

    private final String name;
    private final TextFormatting color;
    private final boolean frightensAnimals;

    SpellType(String name, TextFormatting color, boolean frightensAnimals)
    {
        this.name = name;
        this.color = color.isColor() ? color : TextFormatting.WHITE;
        this.frightensAnimals = frightensAnimals;
    }

    public String getName()
    {
        return name;
    }

    public TextFormatting getColor()
    {
        return color;
    }

    public boolean getFrightensAnimals()
    {
        return frightensAnimals;
    }
}
