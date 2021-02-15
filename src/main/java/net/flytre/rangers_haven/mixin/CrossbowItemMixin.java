package net.flytre.rangers_haven.mixin;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @Shadow
    private static List<ItemStack> getProjectiles(ItemStack crossbow) {
        throw new AssertionError();
    }


    @Shadow
    private static float[] getSoundPitches(Random random) {
        throw new AssertionError();
    }

    @Shadow
    private static void postShoot(World world, LivingEntity entity, ItemStack stack) {
    }

    @Shadow
    private static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
    }

    @ModifyVariable(method = "loadProjectiles", at = @At(value = "STORE", ordinal = 1))
    private static int setProjectiles(int j, LivingEntity shooter, ItemStack projectile) {
        int i = getMultishotLevel(projectile);
        return j + (i == 0 ? 0 : i - 1);
    }

    private static int getMultishotLevel(ItemStack stack) {
        if (!stack.isEmpty()) {
            Identifier identifier = Registry.ENCHANTMENT.getId(Enchantments.MULTISHOT);
            ListTag listTag = stack.getEnchantments();
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                Identifier identifier2 = Identifier.tryParse(compoundTag.getString("id"));
                if (identifier2 != null && identifier2.equals(identifier)) {
                    return Math.max(0, compoundTag.getInt("lvl"));
                }
            }

        }
        return 0;
    }


    @Inject(method = "shootAll", at = @At("HEAD"), cancellable = true)
    private static void rangershaven$shootOverride(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence, CallbackInfo ci) {
        List<ItemStack> list = getProjectiles(stack);
        float[] fs = getSoundPitches(entity.getRandom());

        for (int i = 0; i < list.size(); ++i) {
            ItemStack itemStack = list.get(i);
            boolean bl = entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.creativeMode;
            if (!itemStack.isEmpty()) {
                float breadth = Math.min(15 + 2 * list.size(), 180);
                float simulated = list.size() > 1 ? (float) (-breadth + ((breadth * 2) * (i / (list.size() - 1.0)))) : 0.0f;
                int pitchIndex = (simulated == 0.0 ? 0 : (simulated < 0 ? 1 : 2));
                shoot(world, entity, hand, stack, itemStack, fs[pitchIndex], bl, speed, divergence, simulated);
            }
        }
        postShoot(world, entity, stack);
    }
}
