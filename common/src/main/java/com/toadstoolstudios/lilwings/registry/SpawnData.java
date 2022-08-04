package com.toadstoolstudios.lilwings.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public record SpawnData(EntityType<?> entityType, MobCategory group, int weight, int min, int max) {}
