package com.toadstoolstudios.lilwings.block;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;

public class GoldenCakeBlock extends BaseCakeBlock {

    public GoldenCakeBlock(int hunger, float saturation) {
        super(hunger, saturation);
    }

    @Override
    public void onPlayerEat(PlayerEntity player) {
        super.onPlayerEat(player);
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 5 * 20, 2));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 5 * 20));
    }
}
