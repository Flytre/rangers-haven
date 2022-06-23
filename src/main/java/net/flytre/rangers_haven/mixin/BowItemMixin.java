package net.flytre.rangers_haven.mixin;

import net.flytre.rangers_haven.RangerProjectile;
import net.flytre.rangers_haven.RangersHaven;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class BowItemMixin {


    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void rangers_haven$applyExplosiveLevel(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci, PlayerEntity playerEntity, boolean bl, ItemStack itemStack, int i, float f, boolean bl2, ArrowItem arrowItem, PersistentProjectileEntity persistentProjectileEntity) {

        int l = EnchantmentHelper.getLevel(RangersHaven.EXPLOSIVE, stack);
        if (l > 0)
            ((RangerProjectile) persistentProjectileEntity).setExplosionLevel(l);
        l = EnchantmentHelper.getLevel(RangersHaven.ROPED, stack);
        if (l > 0)
            ((RangerProjectile) persistentProjectileEntity).setRopedLevel(l);
        l = EnchantmentHelper.getLevel(RangersHaven.SEEKING, stack);
        if (l > 0)
            ((RangerProjectile) persistentProjectileEntity).setSeekingLevel(l);
        l = EnchantmentHelper.getLevel(Enchantments.PIERCING, stack);
        if (l > 0)
            persistentProjectileEntity.setPierceLevel((byte) l);
        l = EnchantmentHelper.getLevel(RangersHaven.FLECHETTES, stack);
        if (l > 0)
            ((RangerProjectile) persistentProjectileEntity).setFlechettes(l);
        l = EnchantmentHelper.getLevel(RangersHaven.SHARPSHOOTER, stack);
        if (l > 0)
            ((RangerProjectile) persistentProjectileEntity).setSharpshooter(l);
    }



    @ModifyVariable(method = "onStoppedUsing", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/BowItem;getPullProgress(I)F"))
    public float rangers_haven$modifyPullProgress(float f, ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int l = EnchantmentHelper.getLevel(RangersHaven.NIMBLE, stack);
        float percent = MathHelper.clamp(1.0f - 0.05f * l, 0.05f, 1);
        return MathHelper.clamp(f / percent, 0, 1);
    }

}
