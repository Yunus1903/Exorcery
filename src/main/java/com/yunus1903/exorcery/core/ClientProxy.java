package com.yunus1903.exorcery.core;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;

/**
 * @author Yunus1903
 */
@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy
{
    public static final KeyBinding KEY_SPELL_SELECTOR = new KeyBinding("key.exorcery.spell_selector", GLFW_KEY_GRAVE_ACCENT , "key.categories.misc");
    public static final KeyBinding KEY_DEBUG_KEY = new KeyBinding("key.exorcery.debug", GLFW_KEY_H, "key.categories.misc");

    static
    {
        ClientRegistry.registerKeyBinding(KEY_SPELL_SELECTOR);
        ClientRegistry.registerKeyBinding(KEY_DEBUG_KEY);
    }
}
