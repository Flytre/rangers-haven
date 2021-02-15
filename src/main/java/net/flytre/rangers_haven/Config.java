package net.flytre.rangers_haven;

public class Config {

    private final boolean should_explosive_enchant_damage_blocks;

    public Config() {
        should_explosive_enchant_damage_blocks = true;
    }

    public boolean explosiveEnchantDamagesBlocks() {
        return should_explosive_enchant_damage_blocks;
    }

}
