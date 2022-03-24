package dev.willowworks.lilwings.entity;

import dev.willowworks.lilwings.entity.goals.ButterflyBreedGoal;
import dev.willowworks.lilwings.entity.goals.FindFlowerGoal;
import dev.willowworks.lilwings.entity.goals.GraylingFlowerGoal;
import dev.willowworks.lilwings.item.ButterflyNetItem;
import dev.willowworks.lilwings.registry.LilWingsBlocks;
import dev.willowworks.lilwings.registry.LilWingsEntities;
import dev.willowworks.lilwings.registry.LilWingsItems;
import dev.willowworks.lilwings.registry.entity.Butterfly;
import dev.willowworks.lilwings.registry.entity.GraylingType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ButterflyEntity extends Animal implements FlyingAnimal, IAnimatable {

    private static final EntityDataAccessor<Integer> DATA_COLOR_TYPE = SynchedEntityData.defineId(ButterflyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_CATCH_AMOUNT = SynchedEntityData.defineId(ButterflyEntity.class, EntityDataSerializers.INT);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.butterfly.idle", true);
    private final AnimationFactory factory = new AnimationFactory(this);

    private final Butterfly butterfly;
    private int flowerCooldown = Mth.nextInt(random, 400, 600);
    private BlockPos savedFlowerPos;

    private int otherCooldown = Mth.nextInt(random, 300, 600);
    private BlockPos savedOtherPos;
    private GraylingType colorType;

    public ButterflyEntity(EntityType<? extends ButterflyEntity> entityType, Level level) {
        super(entityType, level);
        this.butterfly = Butterfly.BUTTERFLIES.get(this.getType().getRegistryName());
        this.moveControl = new FlyingMoveControl(this, 20, true);

        if (butterfly.maxHealth() <= 0)
            setInvulnerable(true);

        if (!level.isClientSide()) {
            if (butterfly.breedingItem() != null) {
                this.goalSelector.addGoal(2, new TemptGoal(this, 1.08f, Ingredient.of(butterfly.breedingItem()), false));
            }

            if (entityType == LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get()) {
                this.goalSelector.addGoal(4, new GraylingFlowerGoal(this));
            } else {
                this.goalSelector.addGoal(4, new FindFlowerGoal(this));
            }
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_CATCH_AMOUNT, 0);
        this.getEntityData().define(DATA_COLOR_TYPE, GraylingType.NORMAL.ordinal());
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomFlyingGoal(this, 1.0f));
        this.goalSelector.addGoal(1, new ButterflyBreedGoal(this, 1.0f));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.08f));
        this.goalSelector.addGoal(5, new PanicGoal(this, 1.15f));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        boolean canCatch = butterfly.netItem() == null ? handStack.is(LilWingsItems.BUTTERFLY_NET.get()) || handStack.is(LilWingsItems.ENDERFLY_NET.get()) : handStack.is(butterfly.netItem().get());
        if (level.isClientSide() && !canCatch && handStack.getItem() instanceof ButterflyNetItem) {
            player.displayClientMessage(new TranslatableComponent("lilwings.error.invalid_net"), true);
        }

        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND && canCatch && butterfly.catchAmount() > 0) {
            int slot = player.getInventory().findSlotMatchingItem(LilWingsBlocks.BUTTERFLY_JAR_ITEM.get().getDefaultInstance());
            if (slot < 0) {
                player.displayClientMessage(new TranslatableComponent("lilwings.error.no_jar"), true);
                return InteractionResult.FAIL;
            }

            handStack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(hand));
            int catchAmount = this.getEntityData().get(DATA_CATCH_AMOUNT) + 1;
            this.getEntityData().set(DATA_CATCH_AMOUNT, catchAmount);

            if (butterfly.catchEffect() != null)
                butterfly.catchEffect().onCatch(player, this, catchAmount);

            if (catchAmount >= butterfly.catchAmount()) {
                ItemStack stack = new ItemStack(LilWingsBlocks.BUTTERFLY_JAR_ITEM.get());
                CompoundTag tag = stack.getOrCreateTag();

                if (tag.contains("butterfly")) {
                    player.displayClientMessage(new TranslatableComponent("lilwings.error.jar_full"), true);
                    return InteractionResult.FAIL;
                }

                CompoundTag butterflyTag = new CompoundTag();
                this.saveWithoutId(butterflyTag);
                tag.put("butterfly", butterflyTag);
                tag.putString("butterflyId", this.getType().getRegistryName().toString());
                stack.setTag(tag);

                player.getInventory().removeItem(slot, 1);
                if (!player.getInventory().add(stack)) {
                    player.drop(stack, true);
                }

                player.displayClientMessage(new TranslatableComponent("lilwings.success.butterfly_caught"), true);
                this.remove(RemovalReason.DISCARDED);
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    public int getCatchAmount() {
        return this.getEntityData().get(DATA_CATCH_AMOUNT);
    }

    public void setCatchAmount(int amount) {
        this.getEntityData().set(DATA_CATCH_AMOUNT, amount);
    }

    public void setColorType(GraylingType type) {
        this.getEntityData().set(DATA_COLOR_TYPE, type.ordinal());
        this.colorType = type;
    }

    public void setColorTypeNoUpdate(GraylingType type) {
        this.colorType = type;
    }

    public GraylingType getColorType() {
        return getColorType(false);
    }

    public GraylingType getColorType(boolean local) {
        return local ? colorType : GraylingType.values()[getEntityData().get(DATA_COLOR_TYPE)];
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setCatchAmount(tag.getInt("catchAmount"));

        if (tag.contains("colorType"))
            setColorType(GraylingType.valueOf(tag.getString("colorType")));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("catchAmount", getCatchAmount());
        tag.putString("colorType", getColorType().name());
    }

    @Override
    public void tick() {
        super.tick();

        if (butterfly.particleType() != null) {
            if (this.random.nextFloat() < butterfly.particleSpawnChance()) {
                spawnParticle(butterfly.particleType(), this.getX() - 0.3, this.getX() + 0.3, this.getZ() - 0.3f, this.getZ() + 0.3f, this.getY(0.5));
            }
        }
    }

    private void spawnParticle(SimpleParticleType type, double p_27781_, double x1, double x2, double z1, double z2) {
        level.addParticle(type, Mth.lerp(random.nextDouble(), p_27781_, x1), z2, Mth.lerp(random.nextDouble(), x2, z1), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level.isClientSide()) {
            if (flowerCooldown > 0)
                flowerCooldown--;
        }
    }

    @Override
    public PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };

        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return butterfly.breedingItem() != null && pStack.is(butterfly.breedingItem());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return (AgeableMob) this.getType().create(serverLevel);
    }

    @Override
    protected boolean isFlapping() {
        return true;
    }

    @Override
    public boolean isFlying() {
        return !this.isOnGround();
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (isFlying()) {
            event.getController().setAnimation(IDLE_ANIMATION);
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public int getFlowerCooldown() {
        return flowerCooldown;
    }

    public BlockPos getSavedFlowerPos() {
        return savedFlowerPos;
    }

    public void setFlowerCooldown(int flowerCooldown) {
        this.flowerCooldown = flowerCooldown;
    }

    public void setSavedFlowerPos(BlockPos savedFlowerPos) {
        this.savedFlowerPos = savedFlowerPos;
    }

    public void setOtherCooldown(int otherCooldown) {
        this.otherCooldown = otherCooldown;
    }

    public void setSavedOtherPos(BlockPos savedOtherPos) {
        this.savedOtherPos = savedOtherPos;
    }

    public int getOtherCooldown() {
        return otherCooldown;
    }

    public BlockPos getSavedOtherPos() {
        return savedOtherPos;
    }

    public Butterfly getButterfly() {
        return butterfly;
    }
}
