package com.toadstoolstudios.lilwings.client.patron;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PatreonFlutteringButterfly implements IAnimatable {

    private final ResourceLocation texture;
    private final PatreonFlutteringModel model = new PatreonFlutteringModel();
    private final AnimationFactory factory = new AnimationFactory(this);

    public PatreonFlutteringButterfly(ResourceLocation texture) {
        this.texture = texture;
    }

    static {
        //noinspection unchecked
        AnimationController.addModelFetcher((IAnimatable object) -> object instanceof PatreonFlutteringButterfly butterfly ? (IAnimatableModel) butterfly.getModel() : null);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "flight_controller", 0, event -> {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.patreon_butterfly.fly", true));
            return PlayState.CONTINUE;
        }));
    }

    public PatreonFlutteringModel getModel() {
        return model;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}
