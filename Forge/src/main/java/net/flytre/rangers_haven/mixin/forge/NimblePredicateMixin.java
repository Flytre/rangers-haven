package net.flytre.rangers_haven.mixin.forge;

import net.flytre.rangers_haven.Registry;
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

    @ModifyVariable(method = "register", at = @At("HEAD"), argsOnly = true)
    private static ModelPredicateProvider rangers_haven$modifyBowAnimation(ModelPredicateProvider dummy, Item item, Identifier id, ModelPredicateProvider provider) {
        if (!id.equals(new Identifier("pull")))
            return provider;

        return (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                int l = EnchantmentHelper.getLevel(Registry.NIMBLE.get(), stack);
                float mult = MathHelper.clamp(1.0f - 0.05f * l, 0.05f, 1);
                return entity.getActiveItem() != stack ? 0.0F : (float) (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / (20.0F * mult);
            }
        };
    }
}
