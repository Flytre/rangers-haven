package net.flytre.rangers_haven;

import net.fabricmc.api.ModInitializer;
import net.flytre.flytre_lib.config.ConfigHandler;
import net.flytre.flytre_lib.config.ConfigRegistry;
import net.flytre.rangers_haven.enchants.ExplosiveEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RangersHaven implements ModInitializer {

    public static final Enchantment EXPLOSIVE = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("rangers_haven", "explosive"),
            new ExplosiveEnchantment()
    );

    public static final ConfigHandler<Config> CONFIG = new ConfigHandler<>(new Config(), "rangers_haven");


    @Override
    public void onInitialize() {
        ConfigRegistry.registerServerConfig(CONFIG);
    }
}
