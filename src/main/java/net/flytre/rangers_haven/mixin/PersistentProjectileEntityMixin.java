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


    public int getExplosionLevel() {
        return explosionLevel;
    }


    public void setExplosionLevel(int level) {
        this.explosionLevel = level;
    }

    @Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
    public void rangershaven$toTag(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("explosionLevel", explosionLevel);
    }

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    public void rangershaven$fromTag(CompoundTag tag, CallbackInfo ci) {
        this.explosionLevel = tag.getInt("explosionLevel");
    }


}
