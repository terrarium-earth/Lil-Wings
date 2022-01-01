package dev.willowworks.lilwings.entity.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;

import java.util.List;

public class ButterflyBreedGoal extends BreedGoal {

    public ButterflyBreedGoal(Animal pAnimal, double pSpeedModifier) {
        this(pAnimal, pSpeedModifier, pAnimal.getClass());
    }

    public ButterflyBreedGoal(Animal pAnimal, double pSpeedModifier, Class<? extends Animal> pPartnerClass) {
        super(pAnimal, pSpeedModifier, pPartnerClass);
    }

    @Override
    public boolean canUse() {
        if (!this.animal.isInLove()) {
            return false;
        } else {
            this.partner = this.getFreePartner();
            return this.partner != null;
        }
    }

    private Animal getFreePartner() {
        List<Entity> list = level.getEntities(this.animal, this.animal.getBoundingBox().inflate(8), entity -> entity.getType() == this.animal.getType());
        double d0 = Double.MAX_VALUE;
        Animal animal = null;

        for (Entity entity : list) {
            if (!(entity instanceof Animal entityAnimal)) continue;

            if (this.animal.canMate(entityAnimal) && this.animal.distanceToSqr(entityAnimal) < d0) {
                animal = entityAnimal;
                d0 = this.animal.distanceToSqr(entityAnimal);
            }
        }

        return animal;
    }
}
