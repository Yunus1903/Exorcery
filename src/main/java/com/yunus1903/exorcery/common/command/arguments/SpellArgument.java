package com.yunus1903.exorcery.common.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * @author Yunus1903
 * @since 04/05/2020
 */
public class SpellArgument implements ArgumentType<SpellInput>
{
    public static SpellArgument spell()
    {
        return new SpellArgument();
    }

    @Override
    public SpellInput parse(StringReader reader) throws CommandSyntaxException
    {
        SpellParser spellParser = (new SpellParser(reader)).parse();
        return new SpellInput(spellParser.getSpell());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
    {
        StringReader stringreader = new StringReader(builder.getInput());
        stringreader.setCursor(builder.getStart());
        SpellParser spellParser = new SpellParser(stringreader);

        try
        {
            spellParser.parse();
        }
        catch (CommandSyntaxException var6) { }

        return spellParser.fillSuggestions(builder);
    }

    @Override
    public Collection<String> getExamples()
    {
        return Arrays.asList("exorcery:teleport", "exorcery:fertility");
    }

    public static <S> SpellInput getSpell(CommandContext<S> context, String name)
    {
        return context.getArgument(name, SpellInput.class);
    }
}
