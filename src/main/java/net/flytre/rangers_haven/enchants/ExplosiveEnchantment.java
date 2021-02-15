package net.flytre.rangers_haven.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class ExplosiveEnchantment extends Enchantment {
    public ExplosiveEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.translationKey = "enchantments.rangers_haven.explosive";
    }

    public int getMinPower(int level) {
        return (level + 1) * 9;
    }

    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }


    public int getMinLevel() {
        return 1;
    }

    public int getMaxLevel() {
        return 3;
    }
}
