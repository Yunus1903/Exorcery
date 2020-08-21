package com.yunus1903.exorcery.core;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT;

/**
 * Client-side only proxy class
 * @author Yunus1903
 */
@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy
{
    public static final KeyBinding KEY_SPELL_SELECTOR = new KeyBinding("key.exorcery.spell_selector", GLFW_KEY_GRAVE_ACCENT , "key.categories.misc");

    static
    {
        ClientRegistry.registerKeyBinding(KEY_SPELL_SELECTOR);
    }
}
