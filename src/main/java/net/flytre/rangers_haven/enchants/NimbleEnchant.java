package net.flytre.rangers_haven.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class NimbleEnchant extends Enchantment {

    public NimbleEnchant() {
        super(Rarity.COMMON, EnchantmentTarget.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.translationKey = "enchantments.rangers_haven.nimble";
    }

    @Override
    public int getMinPower(int level) {
        return level * 5 - 5;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 6;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
