package com.toadstoolstudios.lilwings.entity.goals;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;

public class ButterflyBreedGoal extends AnimalMateGoal {

    public ButterflyBreedGoal(AnimalEntity pAnimal, double pSpeedModifier) {
        this(pAnimal, pSpeedModifier, pAnimal.getClass());
    }

    public ButterflyBreedGoal(AnimalEntity pAnimal, double pSpeedModifier, Class<? extends AnimalEntity> pPartnerClass) {
        super(pAnimal, pSpeedModifier, pPartnerClass);
    }

    @Override
    public boolean canStart() {
        if (!this.animal.isInLove()) {
            return false;
        } else {
            this.mate = this.findMate();
            return this.mate != null;
        }
    }

    private AnimalEntity findMate() {
        List<Entity> list = world.getOtherEntities(this.animal, this.animal.getBoundingBox().expand(8), entity -> entity.getType() == this.animal.getType());
        double d0 = Double.MAX_VALUE;
        AnimalEntity animal = null;

        for (Entity entity : list) {
            if (!(entity instanceof AnimalEntity entityAnimal)) continue;

            if (this.animal.canBreedWith(entityAnimal) && this.animal.squaredDistanceTo(entityAnimal) < d0) {
                animal = entityAnimal;
                d0 = this.animal.squaredDistanceTo(entityAnimal);
            }
        }

        return animal;
    }
}
