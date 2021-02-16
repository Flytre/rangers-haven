package net.flytre.rangers_haven.mixin;

import net.flytre.rangers_haven.RangersHaven;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ModelPredicateProviderRegistry.class)
public class NimblePredicateMixin {

    @ModifyVariable(method = "register(Lnet/minecraft/item/Item;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/item/ModelPredicateProvider;)V", at = @At("HEAD"))
    private static ModelPredicateProvider modify(ModelPredicateProvider dummy, Item item, Identifier id, ModelPredicateProvider provider) {
        if (!id.equals(new Identifier("pull")))
            return provider;

        return (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                int l = EnchantmentHelper.getLevel(RangersHaven.NIMBLE, itemStack);
                float mult = MathHelper.clamp(1.0f - 0.05f * l, 0.05f, 1);
                return livingEntity.getActiveItem() != itemStack ? 0.0F : (float) (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / (20.0F * mult);
            }
        };
    }
}
