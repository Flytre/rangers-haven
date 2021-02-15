package net.flytre.rangers_haven.mixin;

import net.flytre.rangers_haven.Config;
import net.flytre.rangers_haven.RangerProjectile;
import net.flytre.rangers_haven.RangersHaven;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileMixin extends Entity {

    public ProjectileMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    @Nullable
    public abstract Entity getOwner();

    @Inject(method = "onEntityHit", at = @At("HEAD"))
    public void rangershaven$entityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        spawnExplosion(entityHitResult.getEntity().getBlockPos());
    }

    @Inject(method = "onBlockHit", at = @At("HEAD"))
    public void rangershaven$blockHit(BlockHitResult blockHitResult, CallbackInfo ci) {
        spawnExplosion(blockHitResult.getBlockPos());
    }

    private void spawnExplosion(BlockPos blockPos) {
        if (!(this instanceof RangerProjectile))
            return;
        RangerProjectile me = (RangerProjectile) this;

        if (me.getExplosionLevel() <= 0)
            return;

        Config config = RangersHaven.CONFIG.getConfig();
        this.world.createExplosion(getOwner(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1 + me.getExplosionLevel(), config.explosiveEnchantDamagesBlocks() ? Explosion.DestructionType.BREAK : Explosion.DestructionType.NONE);
        me.setExplosionLevel(-1);
    }
}
