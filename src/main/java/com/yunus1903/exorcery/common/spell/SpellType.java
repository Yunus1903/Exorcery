package com.yunus1903.exorcery.common.spell;

import net.minecraft.util.text.TextFormatting;

/**
 * Type enum of {@link Spell}
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

    /**
     * Constructor for {@link SpellType}
     * @param name Name of the type
     * @param color Color of the type
     * @param frightensAnimals If the {@link Spell spell} with this type frightens {@link net.minecraft.entity.passive.AnimalEntity animals} when casted
     */
    SpellType(String name, TextFormatting color, boolean frightensAnimals)
    {
        this.name = name;
        this.color = color.isColor() ? color : TextFormatting.WHITE;
        this.frightensAnimals = frightensAnimals;
    }

    /**
     * @return The name of this {@link SpellType type}
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The color of this {@link SpellType type}
     */
    public TextFormatting getColor()
    {
        return color;
    }

    /**
     * @return {@code true} if the {@link Spell spell} with this {@link SpellType type} frightens {@link net.minecraft.entity.passive.AnimalEntity animals} when casted
     */
    public boolean getFrightensAnimals()
    {
        return frightensAnimals;
    }
}
