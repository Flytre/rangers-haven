package net.flytre.rangers_haven.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.PiercingEnchantment;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    public void rangers_haven$isAcceptable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PiercingEnchantment) {
            if (stack.getItem() instanceof BowItem) {
                cir.setReturnValue(true);
            }
        }
    }
}
