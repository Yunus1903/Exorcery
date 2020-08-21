package com.yunus1903.exorcery.common.capabilities.morph;

import com.yunus1903.exorcery.core.Exorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Yunus1903
 * @since 24/06/2020
 */
public class MorphStorage implements Capability.IStorage<IMorph>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IMorph> capability, IMorph instance, Direction side)
    {
        ListNBT list = new ListNBT();

        instance.getMorphedEntities().forEach((a, b) ->
        {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("entity", IntNBT.valueOf(a.getEntityId()));
            compoundNBT.put("entityType", StringNBT.valueOf(Objects.requireNonNull(ForgeRegistries.ENTITIES.getKey(b)).toString()));
            list.add(compoundNBT);
        });

        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("list", list);

        return compoundNBT;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readNBT(Capability<IMorph> capability, IMorph instance, Direction side, INBT nbt)
    {
        CompoundNBT c = (CompoundNBT) nbt;
        ListNBT list = (ListNBT) c.get("list");
        HashMap<LivingEntity, EntityType<? extends LivingEntity>> entities = new HashMap<>();

        assert list != null;
        list.forEach(a ->
        {
            CompoundNBT compoundNBT = (CompoundNBT) a;

            AtomicReference<LivingEntity> entity = new AtomicReference<>();

            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> entity.set((LivingEntity) (Minecraft.getInstance().world != null ? Minecraft.getInstance().world.getEntityByID(compoundNBT.getInt("entity")) : null)));

            DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> Exorcery.instance.server.getWorlds().forEach(world ->
                    {
                        Entity e = world.getEntityByID(compoundNBT.getInt("entity"));
                        if (e instanceof LivingEntity)
                        {
                            entity.set((LivingEntity) e);
                        }
                    }));

            EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(compoundNBT.getString("entityType")));
            entities.put(entity.get(), entityType);
        });

        instance.setMorphedEntities(entities);
    }
}
