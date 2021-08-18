package net.flytre.rangers_haven;

import net.fabricmc.api.ModInitializer;
import net.flytre.flytre_lib.api.config.ConfigHandler;
import net.flytre.flytre_lib.api.config.ConfigRegistry;
import net.flytre.rangers_haven.enchants.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RangersHaven implements ModInitializer {

    public static final Enchantment EXPLOSIVE = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("rangers_haven", "explosive"),
            new ExplosiveEnchantment()
    );

    public static final Enchantment ROPED = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("rangers_haven", "roped"),
            new RopedEnchant()
    );

    public static final Enchantment NIMBLE = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("rangers_haven", "nimble"),
            new NimbleEnchant()
    );

    public static final Enchantment SEEKING = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("rangers_haven", "seeking"),
            new SeekingEnchant()
    );

    public static final Enchantment FLECHETTES = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("rangers_haven", "flechettes"),
            new FlechettesEnchant()
    );

    public static final Enchantment SHARPSHOOTER = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("rangers_haven", "sharpshooter"),
            new SharpshooterEnchant()
    );

    public static final ConfigHandler<Config> CONFIG = new ConfigHandler<>(new Config(), "rangers_haven");


    @Override
    public void onInitialize() {
        ConfigRegistry.registerServerConfig(CONFIG);
    }
}
