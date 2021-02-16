package net.flytre.rangers_haven.mixin;

import net.flytre.rangers_haven.RangerProjectile;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin implements RangerProjectile {

    private int explosionLevel = 0;
    private int roped = 0;
    private int seeking = 0;

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
    }

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    public void rangershaven$fromTag(CompoundTag tag, CallbackInfo ci) {
        this.explosionLevel = tag.getInt("explosionLevel");
        this.roped = tag.getInt("roped");
        this.seeking = tag.getInt("seeking");
    }


}
