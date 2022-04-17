package com.toadstoolstudios.lilwings.platform.services;

import com.toadstoolstudios.lilwings.entity.ButterflyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.EntityType;

import java.util.function.Supplier;

public interface IClientHelper {
    <T extends ButterflyEntity> void registerEntityRenderers(Supplier<EntityType<T>> supplier, EntityRendererFactory<T> factory);
}
