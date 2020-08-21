package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler class to inject {@link net.minecraft.loot.LootTable loottables}
 * @author Yunus1903
 * @since 17/04/2020
 */
@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID)
public final class LootHandler
{
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event)
    {
        final ResourceLocation chests = new ResourceLocation("chests");

        String name = event.getName().toString();

        if (name.startsWith(chests.toString() + "/"))
        {
            String file = name.substring(chests.getNamespace().length() + 1);
            event.getTable().addPool(getInjectPool(file));
        }
    }

    private static LootPool getInjectPool(String entryName)
    {
        return LootPool.builder()
                .addEntry(getInjectEntry(entryName, 1))
                .bonusRolls(0, 1)
                .name("exorcery_inject")
                .build();
    }

    private static LootEntry.Builder<?> getInjectEntry(String name, int weight)
    {
        ResourceLocation table = new ResourceLocation(Exorcery.MOD_ID, "inject/" + name);
        return TableLootEntry.builder(table).weight(weight);
    }
}
