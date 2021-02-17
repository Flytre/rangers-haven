package net.flytre.rangers_haven.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.flytre.rangers_haven.RangersHaven;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Unique
    private static final Style SHIFT_STYLE = Style.EMPTY.withColor(Formatting.DARK_GRAY).withItalic(true);

    @Unique
    private static final Style TOOLTIP_STYLE = Style.EMPTY.withColor(Formatting.GRAY);


    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean hasEnchantments();

    @Environment(EnvType.CLIENT)
    @Inject(method = "getTooltip", at = @At("TAIL"))
    public void rangers_haven$appendBowTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        Item me = getItem();

        if (!(me instanceof BowItem))
            return;

        List<Text> text = cir.getReturnValue();
        if (hasEnchantments())
            if (!Screen.hasShiftDown())
                text.add(new TranslatableText("tooltip.rangers_haven.shift").setStyle(SHIFT_STYLE));
            else {
                text.add(new LiteralText(" "));
                addToolTip(text, Enchantments.POWER, i -> 25 * i + "");
                addToolTip(text, Enchantments.FLAME, i -> 5 + "");
                addToolTip(text, Enchantments.PUNCH, i -> 3 * i + "");
                addToolTip(text, Enchantments.INFINITY, null);
                addToolTip(text, Enchantments.PIERCING, i -> (i + 1) + "");
                addToolTip(text, RangersHaven.SHARPSHOOTER, i -> "" + i * 0.5);
                addToolTip(text, RangersHaven.FLECHETTES, i -> 3 + "");
                addToolTip(text, RangersHaven.EXPLOSIVE, i -> (1.5 + (i * 0.5)) + "");
                addToolTip(text, RangersHaven.NIMBLE, i -> MathHelper.clamp(5 * i, 0, 95) + "");
                addToolTip(text, RangersHaven.ROPED, i -> 10 * i + "");
                addToolTip(text, RangersHaven.SEEKING, i -> 0.5 * i + "");
            }

    }

    private void addToolTip(List<Text> list, Enchantment enchantment, Function<Integer, String> levelToString) {
        ItemStack stack = (ItemStack) (Object) this;
        int level = EnchantmentHelper.getLevel(enchantment, stack);
        if (level > 0) {
            TranslatableText text = levelToString != null ? new TranslatableText(enchantment.getTranslationKey() + ".lore", levelToString.apply(level)) : new TranslatableText(enchantment.getTranslationKey() + ".lore");
            List<StringVisitable> strings = MinecraftClient.getInstance().textRenderer.getTextHandler().wrapLines(text, 190, TOOLTIP_STYLE);
            for (StringVisitable string : strings) {
                list.add(new LiteralText(string.getString()).setStyle(TOOLTIP_STYLE));
            }
        }
    }


}
