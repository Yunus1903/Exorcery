package com.yunus1903.exorcery.common.spell;

import net.minecraft.util.text.TextFormatting;

/**
 * @author Yunus1903
 */
public enum SpellType
{
    NORMAL("normal", TextFormatting.WHITE),
    NATURAL("natural", TextFormatting.DARK_GREEN),
    FIRE("fire", TextFormatting.DARK_RED),
    ENDER("ender", TextFormatting.DARK_PURPLE),
    DEBUG("debug", TextFormatting.DARK_BLUE);

    private final String name;
    private final TextFormatting color;

    private SpellType(String name, TextFormatting color)
    {
        this.name = name;
        this.color = color.isColor() ? color : TextFormatting.WHITE;
    }

    public String getName()
    {
        return name;
    }

    public TextFormatting getColor()
    {
        return color;
    }
}
