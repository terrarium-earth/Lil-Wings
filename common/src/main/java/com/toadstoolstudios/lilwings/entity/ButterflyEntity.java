package com.toadstoolstudios.lilwings.entity;


import com.toadstoolstudios.lilwings.LilWings;
import com.toadstoolstudios.lilwings.entity.goals.ButterflyBreedGoal;
import com.toadstoolstudios.lilwings.entity.goals.FindFlowerGoal;
import com.toadstoolstudios.lilwings.entity.goals.GraylingFlowerGoal;
import com.toadstoolstudios.lilwings.item.ButterflyNetItem;
import com.toadstoolstudios.lilwings.registry.LilWingsBlocks;
import com.toadstoolstudios.lilwings.registry.LilWingsEntities;
import com.toadstoolstudios.lilwings.registry.LilWingsItems;
import com.toadstoolstudios.lilwings.registry.entity.Butterfly;
import com.toadstoolstudios.lilwings.registry.entity.GraylingType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Supplier;

public class ButterflyEntity extends AnimalEntity implements Flutterer, IAnimatable {

    private static final TrackedData<Integer> DATA_COLOR_TYPE = DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DATA_CATCH_AMOUNT = DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.butterfly.idle", true);
    private final AnimationFactory factory = new AnimationFactory(this);

    private final Butterfly butterfly;
    private int flowerCooldown = MathHelper.nextInt(random, 400, 600);
    private BlockPos savedFlowerPos;
    public final boolean isInJar;

    private int otherCooldown = MathHelper.nextInt(random, 300, 600);
    private BlockPos savedOtherPos;
    private GraylingType colorType;

    public ButterflyEntity(EntityType<? extends ButterflyEntity> entityType, World level) {
        this(entityType, level, false);
    }

    public ButterflyEntity(EntityType<? extends ButterflyEntity> entityType, World level, boolean isInJar) {
        super(entityType, level);
        this.butterfly = Butterfly.getButterfly(entityType);
        this.moveControl = new FlightMoveControl(this, 20, true);

        if (butterfly.maxHealth() <= 0)
            setInvulnerable(true);

        if (!level.isClient()) {
            if (butterfly.breedingItem() != null) {
                this.goalSelector.add(2, new TemptGoal(this, 1.08f, Ingredient.ofItems(butterfly.breedingItem()), false));
            }

            if (entityType == LilWingsEntities.GRAYLING_BUTTERFLY.entityType().get()) {
                this.goalSelector.add(4, new GraylingFlowerGoal(this));
            } else {
                this.goalSelector.add(4, new FindFlowerGoal(this));
            }
        }

        this.isInJar = isInJar;
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(DATA_CATCH_AMOUNT, 0);
        this.getDataTracker().startTracking(DATA_COLOR_TYPE, GraylingType.NORMAL.ordinal());
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(0, new FlyGoal(this, 1.0f));
        this.goalSelector.add(1, new ButterflyBreedGoal(this, 1.0f));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.08f));
        this.goalSelector.add(5, new EscapeDangerGoal(this, 1.15f));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        boolean canCatch = butterfly.netItem() == null ? handStack.isOf(LilWingsItems.BUTTERFLY_NET.get()) || handStack.isOf(LilWingsItems.ENDERFLY_NET.get()) : handStack.isOf(butterfly.netItem().get());
        if (world.isClient() && !canCatch && handStack.getItem() instanceof ButterflyNetItem) {
            player.sendMessage(new TranslatableText("entity.lilwings.interactions.error.invalid_net"), true);
        }

        if (!world.isClient() && hand == Hand.MAIN_HAND && canCatch && butterfly.catchAmount() > 0 && handStack.getItem() instanceof ButterflyNetItem) {
            //int slot = player.getInventory().findSlotMatchingItem(LilWingsBlocks.BUTTERFLY_JAR_ITEM.get().getDefaultInstance());

            if(handStack.getOrCreateNbt().contains("butterfly")) {
                player.sendMessage(new TranslatableText("entity.lilwings.interactions.error.full_net"), true);
                return ActionResult.FAIL;
            }

            int catchAmount = this.getDataTracker().get(DATA_CATCH_AMOUNT) + 1;
            this.getDataTracker().set(DATA_CATCH_AMOUNT, catchAmount);


            if (catchAmount >= butterfly.catchAmount()) {
                if (butterfly.catchEffect() != null) butterfly.catchEffect().onCatch(player, this, catchAmount);
                handStack.damage(1, player, player1 -> player1.sendToolBreakStatus(hand));
                NbtCompound tag = handStack.getOrCreateNbt();

                NbtCompound butterflyTag = new NbtCompound();
                this.writeNbt(butterflyTag);
                tag.put("butterfly", butterflyTag);
                tag.putString("butterflyId", EntityType.getId(getType()).toString());
                handStack.setNbt(tag);

                player.sendMessage(new TranslatableText("entity.lilwings.interactions.success"), true);
                this.remove(RemovalReason.DISCARDED);
                return ActionResult.SUCCESS;
            }
        }

        if(handStack.isOf(LilWingsItems.COTTON_BALL.get())) {
            int index = this.getColorType().ordinal();
            Item cottonBall = this.butterfly.cottonBallsItems()[index].get();
            handStack.decrement(1);
            player.getInventory().offerOrDrop(new ItemStack(cottonBall));
            return ActionResult.SUCCESS;
        }

        if(handStack.isOf(Items.BUCKET) && butterfly.equals(LilWingsEntities.CLOUDY_PUFF_BUTTERFLY)) {
            handStack.decrement(1);
            player.getInventory().offerOrDrop(new ItemStack(LilWingsItems.JELLY_BUCKET.get()));
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    public int getCatchAmount() {
        return this.getDataTracker().get(DATA_CATCH_AMOUNT);
    }

    public void setCatchAmount(int amount) {
        this.getDataTracker().set(DATA_CATCH_AMOUNT, amount);
    }

    public void setColorType(GraylingType type) {
        this.getDataTracker().set(DATA_COLOR_TYPE, type.ordinal());
        this.colorType = type;
    }

    public void setColorTypeNoUpdate(GraylingType type) {
        this.colorType = type;
    }

    public GraylingType getColorType() {
        return GraylingType.values()[getDataTracker().get(DATA_COLOR_TYPE)];
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        setCatchAmount(tag.getInt("catchAmount"));

        if (tag.contains("colorType"))
            setColorType(GraylingType.valueOf(tag.getString("colorType")));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("catchAmount", getCatchAmount());
        tag.putString("colorType", getColorType().name());
    }

    @Override
    public void tick() {
        super.tick();

        if (butterfly.particleType() != null) {
            if (this.random.nextFloat() < butterfly.particleSpawnChance()) {
                spawnParticle(butterfly.particleType(), this.getX() - 0.3, this.getX() + 0.3, this.getZ() - 0.3f, this.getZ() + 0.3f, this.getBodyY(0.5));
            }
        }
    }

    private void spawnParticle(DefaultParticleType type, double p_27781_, double x1, double x2, double z1, double z2) {
        world.addParticle(type, MathHelper.lerp(random.nextDouble(), p_27781_, x1), z2, MathHelper.lerp(random.nextDouble(), x2, z1), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!world.isClient()) {
            if (flowerCooldown > 0)
                flowerCooldown--;
        }
    }

    @Override
    public EntityNavigation createNavigation(World pLevel) {
        BirdNavigation flyingpathnavigation = new BirdNavigation(this, pLevel) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };

        flyingpathnavigation.setCanPathThroughDoors(false);
        flyingpathnavigation.setCanSwim(true);
        flyingpathnavigation.setCanEnterOpenDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public boolean isBreedingItem(ItemStack pStack) {
        return butterfly.breedingItem() != null && pStack.isOf(butterfly.breedingItem());
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverLevel, PassiveEntity ageableMob) {
        return (PassiveEntity) this.getType().create(serverLevel);
    }

    @Override
    protected boolean hasWings() {
        return true;
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity pPlayer) {
        return false;
    }

    @Override
    public boolean handleFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public void fall(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (isInAir() || this.isInJar) {
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
