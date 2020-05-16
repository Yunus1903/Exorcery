package com.yunus1903.exorcery.client.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.io.*;

/**
 * @author Yunus1903
 * @since 15/05/2020
 */
@OnlyIn(Dist.CLIENT)
public class KeybindingHandler
{
    private static final String FILE_NAME = "exorcery_keybindings.json";

    private final Minecraft mc;
    private final File file;

    private JsonObject main = new JsonObject();

    public KeybindingHandler(Minecraft mc)
    {
        this.mc = mc;
        file = new File(FILE_NAME);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                Exorcery.LOGGER.error(e.getMessage());
            }
        }

        try
        {
            readFromFile();
        }
        catch (FileNotFoundException e)
        {
            Exorcery.LOGGER.error(e.getMessage());
        }
    }

    private void readFromFile() throws FileNotFoundException
    {
        JsonElement json = (new JsonParser()).parse(new FileReader(file));
        if (!json.isJsonNull()) main = (JsonObject) json;
    }

    private void writeToFile() throws IOException
    {
        FileWriter writer = new FileWriter(file);
        writer.write(main.toString());
        writer.flush();
        writer.close();
    }

    @Nullable
    private JsonObject getKeys()
    {
        if (mc.isSingleplayer() && mc.player != null)
        {
            String worldName = mc.getIntegratedServer().getWorld(mc.player.dimension).getWorldInfo().getWorldName();
            if (!main.has(worldName)) main.add(worldName, new JsonObject());
            return main.getAsJsonObject(worldName);
        }
        else if (mc.getCurrentServerData() != null)
        {
            String serverIP = mc.getCurrentServerData().serverIP;
            if (!main.has(serverIP)) main.add(serverIP, new JsonObject());
            return main.getAsJsonObject(serverIP);
        }
        return null;
    }

    @Nullable
    public InputMappings.Input getKey(Spell spell)
    {
        try
        {
            readFromFile();
        }
        catch (FileNotFoundException e)
        {
            Exorcery.LOGGER.error(e.getMessage());
            return null;
        }

        JsonObject obj = getKeys();
        if (obj == null || obj.isJsonNull()) return null;

        if (obj.has(spell.toString()))
        {
            JsonPrimitive key = getKeys().getAsJsonPrimitive(spell.toString());
            if (key == null || key.isJsonNull()) return null;
            return InputMappings.getInputByCode(key.getAsInt(), 0);
        }

        return null;
    }

    public void setKey(Spell spell, int keyCode)
    {
        try
        {
            readFromFile();
        }
        catch (FileNotFoundException e)
        {
            Exorcery.LOGGER.error(e.getMessage());
            return;
        }

        JsonObject obj = getKeys();
        if (obj == null || obj.isJsonNull()) return;

        obj.addProperty(spell.toString(), keyCode);

        try
        {
            writeToFile();
        }
        catch (IOException e)
        {
            Exorcery.LOGGER.error(e.getMessage());
        }
    }
}
