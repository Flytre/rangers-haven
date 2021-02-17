package net.flytre.rangers_haven.mixin;

import net.flytre.rangers_haven.RangersHaven;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.PiercingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiercingEnchantment.class)
public class PiercingEnchantmentMixin {

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    public void rangers_haven$canAcceptPiercingSharpshooter(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if (other == RangersHaven.SHARPSHOOTER)
            cir.setReturnValue(false);
    }
}
