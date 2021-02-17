package net.flytre.rangers_haven.mixin;

import net.flytre.rangers_haven.Config;
import net.flytre.rangers_haven.RangerProjectile;
import net.flytre.rangers_haven.RangersHaven;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileMixin extends Entity {

    @Unique
    private double lY;


    public ProjectileMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    @Nullable
    public abstract Entity getOwner();

    @Shadow
    public abstract void tick();

    @Inject(method = "tick", at = @At("HEAD"))
    public void rangers_haven$particleTrails(CallbackInfo ci) {
        if (!(this instanceof RangerProjectile))
            return;

        if (world.isClient)
            return;

        ServerWorld world = (ServerWorld) this.world;

        RangerProjectile me = (RangerProjectile) this;
        if (me.getExplosionLevel() > 0) {
            world.spawnParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 3, 0, 0, 0, 0);
        }
        if (me.getRopedLevel() > 0) {
            world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, getX(), getY(), getZ(), 3, 0, 0, 0, 0);
        }

        if (me.getSeekingLevel() > 0) {
            Entity target = getTarget(((RangerProjectile) this).getSeekingLevel() / 2.0);
            if (target != null) {
                applyHoming(target, me.getSeekingLevel());
                me.setSeekingLevel(0);
            }
        }
        if (me.getFlechettes() > 0 && lY > getY()) {
            Entity owner = getOwner();

            for (int i = 0; i < me.getFlechettes() * 3; i++) {
                ArrowEntity copy = owner instanceof LivingEntity ? new ArrowEntity(world, (LivingEntity) owner) : new ArrowEntity(world, getX(), getY(), getZ());
                copy.copyPositionAndRotation(this);
                Vec3d normal = getVelocity().normalize();
                copy.setVelocity(normal.x, normal.y, normal.z, (float) getVelocity().length(), 10);
                copy.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                world.spawnEntity(copy);
            }
            me.setFlechettes(0);

        }
        lY = getY();
    }

    @Inject(method = "onEntityHit", at = @At("HEAD"))
    public void rangers_haven$entityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        spawnExplosion(entityHitResult.getEntity().getBlockPos());
        ropedPull(entityHitResult.getEntity().getBlockPos());
    }

    @Inject(method = "onBlockHit", at = @At("HEAD"))
    public void rangers_haven$blockHit(BlockHitResult blockHitResult, CallbackInfo ci) {
        spawnExplosion(blockHitResult.getBlockPos());
        ropedPull(blockHitResult.getBlockPos());

    }

    private void ropedPull(BlockPos blockPos) {
        if (!(this instanceof RangerProjectile))
            return;
        RangerProjectile me = (RangerProjectile) this;

        if (me.getRopedLevel() <= 0)
            return;
        Entity entity = getOwner();
        if (entity == null)
            return;
        Vec3d diff = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()).add(-entity.getX(), -entity.getY(), -entity.getZ());
        diff = diff.multiply(me.getRopedLevel() / 14.0);
        entity.addVelocity(diff.x, diff.y / 1.5, diff.z);
        entity.velocityDirty = true;
        entity.velocityModified = true;
        me.setRopedLevel(-1);
    }

    private void spawnExplosion(BlockPos blockPos) {
        if (!(this instanceof RangerProjectile))
            return;
        RangerProjectile me = (RangerProjectile) this;

        if (me.getExplosionLevel() <= 0)
            return;

        Config config = RangersHaven.CONFIG.getConfig();
        this.world.createExplosion(getOwner(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.5f + me.getExplosionLevel() * 0.5f, config.explosiveEnchantDamagesBlocks() ? Explosion.DestructionType.BREAK : Explosion.DestructionType.NONE);
        me.setExplosionLevel(-1);
    }

    private Entity getTarget(double rad) {
        TargetPredicate predicate = new TargetPredicate();
        Entity owner = getOwner();
        if (owner == null)
            return null;
        if (!(owner instanceof HostileEntity))
            predicate.setPredicate(i -> !(i == getOwner()) && !(i instanceof PassiveEntity) && !(i instanceof GolemEntity));
        else
            predicate.setPredicate(i -> !(i == getOwner()) && !(i instanceof HostileEntity));
        return world.getClosestEntity(LivingEntity.class, predicate, null, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().expand(rad));
    }

    public void applyHoming(Entity target, int level) {
        double mult = MathHelper.clamp(3.05 - 0.5 * level, 0, 4);
        Vec3d pos = new Vec3d(target.getX(), target.getEyeY(), target.getZ());
        Vec3d difference = pos.subtract(this.getX(), this.getEyeY(), this.getZ());
        Vec3d normalizedTargetVector = difference.normalize(); //Normalizing makes the magnitude = 1
        Vec3d normalizedArrowVector = this.getVelocity().normalize();
        Vec3d newPath = normalizedTargetVector.add(normalizedArrowVector.multiply(mult, mult, mult)).normalize();
        double speed = this.getVelocity().length();
        this.setVelocity(newPath.multiply(speed, speed, speed));
    }
}
