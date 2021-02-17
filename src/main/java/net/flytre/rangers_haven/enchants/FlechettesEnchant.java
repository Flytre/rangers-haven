package net.flytre.rangers_haven.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class FlechettesEnchant extends Enchantment {

    public FlechettesEnchant() {
        super(Rarity.VERY_RARE, EnchantmentTarget.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.translationKey = "enchantments.rangers_haven.flechettes";
    }

    @Override
    public int getMinPower(int level) {
        return 26;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
