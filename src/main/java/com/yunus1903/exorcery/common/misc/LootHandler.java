package com.yunus1903.exorcery.common.misc;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Exorcery.MOD_ID)
public final class LootHandler
{
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event)
    {
        String prefix = "minecraft:chests/";
        String name = event.getName().toString();

        if (name.startsWith(prefix))
        {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            switch (file)
            {
                case "village/village_plains_house":
                case "buried_treasure":
                case "end_city_treasure":
                case "igloo_chest":
                //case "simple_dungeon":
                case "stronghold_library":
                    event.getTable().addPool(getInjectPool(file));
                    break;
                default:
                    break;
            }
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

    private static LootEntry.Builder getInjectEntry(String name, int weight)
    {
        ResourceLocation table = new ResourceLocation(Exorcery.MOD_ID, "inject/" + name);
        return TableLootEntry.builder(table).weight(weight);
    }
}
