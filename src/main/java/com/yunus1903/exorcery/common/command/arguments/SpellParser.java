package com.yunus1903.exorcery.common.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.yunus1903.exorcery.common.misc.ExorceryRegistry;
import com.yunus1903.exorcery.common.spell.Spell;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Yunus1903
 * @since 04/05/2020
 */
public class SpellParser
{
    public static final DynamicCommandExceptionType SPELL_INVALID = new DynamicCommandExceptionType(function ->
            new TranslationTextComponent("argument.exorcery.spell.invalid", function));

    private final StringReader reader;
    private Spell spell;

    private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> suggestionsBuilder = SuggestionsBuilder::buildFuture;

    public SpellParser(StringReader reader)
    {
        this.reader = reader;
    }

    public Spell getSpell()
    {
        return spell;
    }

    public SpellParser parse() throws CommandSyntaxException
    {
        suggestionsBuilder = this::suggestSpell;

        int i = reader.getCursor();
        ResourceLocation resourceLocation = ResourceLocation.read(reader);
        spell = ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).getValue(resourceLocation);
        if (spell == null)
        {
            reader.setCursor(i);
            throw SPELL_INVALID.createWithContext(reader, resourceLocation.toString());
        }
        else
        {
            suggestionsBuilder = SuggestionsBuilder::buildFuture;
        }

        return this;
    }

    public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder builder)
    {
        return this.suggestionsBuilder.apply(builder.createOffset(this.reader.getCursor()));
    }

    private CompletableFuture<Suggestions> suggestSpell(SuggestionsBuilder builder)
    {
        return ISuggestionProvider.suggestIterable(ExorceryRegistry.getForgeRegistry(ExorceryRegistry.SPELLS).getKeys(), builder);
    }
}
