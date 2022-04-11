package dev.willowworks.lilwings.entity.goals;

import dev.willowworks.lilwings.entity.ButterflyEntity;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Stolen from Resourceful Bees <3
 */
public class FindFlowerGoal extends Goal {

    public static final ArrayList<BlockPos> positionOffsets = new ArrayList<>();
    protected final ButterflyEntity butterfly;

    protected int ticks;
    protected int pollinationTicks;
    protected boolean running = false;
    protected boolean completed = false;
    protected Vec3d nextTarget;
    protected Vec3d boundingBox;

    static {
        for (int i = 0; (double) i <= 5; i = i > 0 ? -i : 1 - i) {
            for (int j = 0; (double) j < 5; ++j) {
                for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        positionOffsets.add(new BlockPos(k, i - 1, l));
                    }
                }
            }
        }
    }

    public FindFlowerGoal(ButterflyEntity butterfly) {
        this.butterfly = butterfly;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public void tick() {
        super.tick();
        ticks++;

        if (ticks >= 600) this.clearTasks();
        else handleFlower();

        if (pollinationTicks > getPollinationTime())
            completed = true;
    }

    @Override
    public void start() {
        super.start();
        pollinationTicks = 0;
        completed = false;
        running = true;
    }

    @Override
    public void stop() {
        super.stop();
        running = false;
        butterfly.getNavigation().stop();
        updateCooldown(200);
    }

    @Override
    public boolean canStart() {
        if (butterfly.getFlowerCooldown() > 0 || butterfly.world.isRaining()) {
            return false;
        } else {
            Optional<BlockPos> flowerPos = findFlower();
            if (flowerPos.isPresent()) {
                moveToFlower(flowerPos.get());
                return true;
            } else {
                updateCooldown(MathHelper.nextInt(butterfly.getRandom(), 400, 500));
            }
        }

        return false;
    }


    @Override
    public boolean shouldContinue() {
        return running && getSavedPos() != null && !this.completed;
    }

    protected void handleFlower() {
        if (getSavedPos() != null) {
            Vec3d bottom = Vec3d.ofBottomCenter(getSavedPos()).add(0, 0.6f, 0);
            if (boundingBox != null) {
                bottom = boundingBox;
            }

            if (bottom.distanceTo(butterfly.getPos()) > 1) {
                this.nextTarget = bottom;
                this.moveToNextTarget();
            } else {
                if (nextTarget == null) {
                    this.nextTarget = bottom;
                }

                pollinateFlower(bottom);
            }
        }
    }

    private void moveToNextTarget() {
        butterfly.getMoveControl().moveTo(this.nextTarget.getX(), this.nextTarget.getY(), this.nextTarget.getZ(), (float) 0.5);
    }

    private double getRandomOffset() {
        return (butterfly.getRandom().nextFloat() * 2.0D - 1.0D) * 0.33333334D;
    }

    private void pollinateFlower(Vec3d vector3d) {
        boolean closeToTarget = butterfly.getPos().distanceTo(this.nextTarget) <= 0.1D;
        boolean shouldMoveToNewTarget = true;
        if (!closeToTarget && this.ticks > 600) {
            this.clearTasks();
        } else {
            if (closeToTarget) {
                if (butterfly.getRandom().nextInt(25) == 0) {
                    this.nextTarget = new Vec3d(vector3d.getX() + this.getRandomOffset(), vector3d.getY(), vector3d.getZ() + this.getRandomOffset());
                    butterfly.getNavigation().stop();
                } else {
                    shouldMoveToNewTarget = false;
                }

                butterfly.getLookControl().lookAt(vector3d.getX(), vector3d.getY(), vector3d.getZ());
            }

            if (shouldMoveToNewTarget) {
                this.moveToNextTarget();
            }

            ++this.pollinationTicks;
        }
    }

    public Optional<BlockPos> findFlower() {
        BlockPos beePos = butterfly.getBlockPos();
        BlockPos.Mutable flowerPos = beePos.mutableCopy();

        for (BlockPos blockPos : positionOffsets) {
            flowerPos.set(beePos, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (getFlowerBlockPredicate().test(flowerPos)) {
                return Optional.of(flowerPos);
            }
        }

        return Optional.empty();
    }

    public void clearTasks() {
        butterfly.setSavedFlowerPos(null);
        boundingBox = null;
    }

    protected void moveToFlower(BlockPos flowerPos) {
        butterfly.setSavedFlowerPos(flowerPos);
        butterfly.getNavigation().startMovingTo(flowerPos.getX() + 0.5D, flowerPos.getY() + 0.5D, flowerPos.getZ() + 0.5D, 1.2D);
    }

    public int getPollinationTime() {
        return 400;
    }

    protected void updateCooldown(int amount) {
        butterfly.setFlowerCooldown(amount);
    }

    protected BlockPos getSavedPos() {
        return butterfly.getSavedFlowerPos();
    }

    public Predicate<BlockPos> getFlowerBlockPredicate() {
        return pos -> {
            if (!butterfly.world.isInBuildLimit(butterfly.getBlockPos())) return false;
            BlockState state = butterfly.world.getBlockState(pos);
            if (state.isAir()) return false;
            return state.isIn(BlockTags.FLOWERS);
        };
    }
}
