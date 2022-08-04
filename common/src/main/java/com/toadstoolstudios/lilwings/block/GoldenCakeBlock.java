package com.toadstoolstudios.lilwings.block;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class GoldenCakeBlock extends BaseCakeBlock {

    public GoldenCakeBlock(int hunger, float saturation) {
        super(hunger, saturation);
    }

    @Override
    public void onPlayerEat(Player player) {
        super.onPlayerEat(player);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5 * 20, 2));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 5 * 20));
    }
}
