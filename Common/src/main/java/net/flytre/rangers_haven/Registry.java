package net.flytre.rangers_haven;

import net.flytre.flytre_lib.api.config.ConfigHandler;
import net.flytre.flytre_lib.api.config.ConfigRegistry;
import net.flytre.flytre_lib.loader.LoaderAgnosticRegistry;
import net.flytre.rangers_haven.enchants.*;
import net.minecraft.enchantment.Enchantment;

import java.util.function.Supplier;

public class Registry {


    public static final ConfigHandler<Config> CONFIG = new ConfigHandler<>(new Config(), "rangers_haven");


    public static final Supplier<Enchantment> EXPLOSIVE = LoaderAgnosticRegistry.registerEnchantment(
            ExplosiveEnchantment::new,
            "rangers_haven",
            "explosive"
    );

    public static final Supplier<Enchantment> ROPED = LoaderAgnosticRegistry.registerEnchantment(
            RopedEnchant::new,
            "rangers_haven",
            "roped"
    );

    public static final Supplier<Enchantment> NIMBLE = LoaderAgnosticRegistry.registerEnchantment(
            NimbleEnchant::new,
            "rangers_haven",
            "nimble"
    );

    public static final Supplier<Enchantment> SEEKING = LoaderAgnosticRegistry.registerEnchantment(
            SeekingEnchant::new,
            "rangers_haven",
            "seeking"
    );

    public static final Supplier<Enchantment> FLECHETTES = LoaderAgnosticRegistry.registerEnchantment(
            FlechettesEnchant::new,
            "rangers_haven",
            "flechettes"
    );

    public static final Supplier<Enchantment> SHARPSHOOTER = LoaderAgnosticRegistry.registerEnchantment(
            SharpshooterEnchant::new,
            "rangers_haven",
            "sharpshooter"
    );


    public static void init() {
        ConfigRegistry.registerServerConfig(CONFIG);
    }

}
