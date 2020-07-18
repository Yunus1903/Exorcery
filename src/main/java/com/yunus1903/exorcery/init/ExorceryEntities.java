package com.yunus1903.exorcery.init;

import com.yunus1903.exorcery.common.entity.SmallSpiderEntity;
import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ExorceryEntities
{
    private static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<>();
    public static final EntityType<SmallSpiderEntity> SMALL_SPIDER = (EntityType<SmallSpiderEntity>) register(EntityType.Builder.create(SmallSpiderEntity::new, EntityClassification.MONSTER)
            .size(0.3F, 0.2F)
            .build("small_spider")
            .setRegistryName(Exorcery.MOD_ID, "small_spider"));

    private static EntityType<?> register(EntityType<?> entityType)
    {
        ENTITY_TYPES.add(entityType);
        return entityType;
    }

    @SubscribeEvent
    public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event)
    {
        for (EntityType<?> entityType : ENTITY_TYPES)
        {
            event.getRegistry().register(entityType);
        }
    }

}
