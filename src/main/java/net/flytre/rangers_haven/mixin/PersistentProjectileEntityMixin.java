package net.flytre.rangers_haven.mixin;

import net.flytre.rangers_haven.RangerProjectile;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin implements RangerProjectile {

    private int explosionLevel = 0;
    private int roped = 0;
    private int seeking = 0;
    private int flechettes = 0;
    private int sharpshooter = 0;

    private Vec3d startPos = new Vec3d(0, 0, 0);


    @Inject(method = "tick", at = @At("HEAD"))
    public void rangershaven$setStartPos(CallbackInfo ci) {
        if (startPos.x == 0 && startPos.y == 0 && startPos.z == 0) {
            ProjectileEntity instance = (ProjectileEntity) (Object) this;
            startPos = instance.getPos();
        }
    }

    @Override
    public int getFlechettes() {
        return flechettes;
    }

    @Override
    public void setFlechettes(int flechettes) {
        this.flechettes = flechettes;
    }

    @Override
    public int getSharpshooter() {
        return sharpshooter;
    }

    @Override
    public void setSharpshooter(int sharpshooter) {
        this.sharpshooter = sharpshooter;
    }

    @Override
    public int getRopedLevel() {
        return roped;
    }

    @Override
    public void setRopedLevel(int roped) {
        this.roped = roped;
    }

    @Override
    public int getSeekingLevel() {
        return seeking;
    }

    @Override
    public void setSeekingLevel(int seeking) {
        this.seeking = seeking;
    }

    @Override
    public int getExplosionLevel() {
        return explosionLevel;
    }

    @Override
    public void setExplosionLevel(int level) {
        this.explosionLevel = level;
    }

    @Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
    public void rangershaven$toTag(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("explosionLevel", explosionLevel);
        tag.putInt("roped", roped);
        tag.putInt("seeking", seeking);
        tag.putInt("flechettes", flechettes);
        tag.putInt("sharpshooter", sharpshooter);
        tag.putDouble("startX", startPos.x);
        tag.putDouble("startY", startPos.y);
        tag.putDouble("startZ", startPos.z);

    }

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    public void rangershaven$fromTag(CompoundTag tag, CallbackInfo ci) {
        this.explosionLevel = tag.getInt("explosionLevel");
        this.roped = tag.getInt("roped");
        this.seeking = tag.getInt("seeking");
        this.flechettes = tag.getInt("flechettes");
        this.sharpshooter = tag.getInt("sharpshooter");
        double x = tag.getDouble("startX"), y = tag.getDouble("startY"), z = tag.getDouble("startZ");
        this.startPos = new Vec3d(x, y, z);
    }

    @ModifyVariable(method = "onEntityHit", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/math/MathHelper;ceil(D)I"))
    public int rangershaven$modifySharpshooterDamage(int in) {
        ProjectileEntity instance = (ProjectileEntity) (Object) this;
        if (getSharpshooter() > 0) {
            Vec3d horiz = new Vec3d(startPos.x,instance.getPos().y,startPos.z);
            double dis = horiz.distanceTo(instance.getPos());
            in *= Math.pow(1.00 + 0.005 * getSharpshooter(), dis);
        }
        return in;
    }


}
