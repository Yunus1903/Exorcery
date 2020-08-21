package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.common.spell.Spell;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.*;

/**
 * Custom registries
 * @author Yunus1903
 */
public final class ExorceryRegistry
{
    private static final int MAX_ID = Integer.MAX_VALUE - 1;

    /**
     * Custom registry for {@link Spell spells}
     */
    public static IForgeRegistry<Spell> SPELLS;

    public static void createRegistries()
    {
        SPELLS = makeRegistry(new ResourceLocation(Exorcery.MOD_ID, "spells"), Spell.class).create();
    }

    private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(ResourceLocation name, Class<T> type)
    {
        return new RegistryBuilder<T>().setName(name).setType(type).setMaxID(MAX_ID).legacyName(name).addCallback(new RegistryCallback<T>());
    }

    @SuppressWarnings("unchecked")
    public static <V extends IForgeRegistryEntry<V>> ForgeRegistry<V> getForgeRegistry(IForgeRegistry<V> reg)
    {
        return reg.getSlaveMap(RegistryCallback.ID, ForgeRegistry.class);
    }

    public static class RegistryCallback<V extends IForgeRegistryEntry<V>> implements IForgeRegistry.CreateCallback<V>
    {
        public static final ResourceLocation ID = new ResourceLocation(Exorcery.MOD_ID, "registry_delegate");
        @Override
        public void onCreate(IForgeRegistryInternal<V> owner, RegistryManager stage)
        {
            owner.setSlaveMap(ID, owner);
        }
    }
}
