package net.flytre.rangers_haven.enchants;

import net.flytre.rangers_haven.RangersHaven;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class ExplosiveEnchantment extends Enchantment {
    public ExplosiveEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.translationKey = "enchantments.rangers_haven.explosive";
    }

    @Override
    public int getMinPower(int level) {
        return (level + 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != RangersHaven.FLECHETTES && other != RangersHaven.ROPED;
    }

}
