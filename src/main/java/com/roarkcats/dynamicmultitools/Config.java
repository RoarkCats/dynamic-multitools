package com.roarkcats.dynamicmultitools;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue MULTITOOL_SPEED_MULTIPLIER = BUILDER
            .comment("Multiplier for the mining speed of all new multitools.")
            .comment("Applied to the tier's defined speed as a penalty, values higher than 1.0 will be beneficial.")
            .defineInRange("multitool_speed_multiplier", 0.8, 0.0, 1024);

    public static final ModConfigSpec.DoubleValue MULTITOOL_DURABILITY_MULTIPLIER = BUILDER
            .comment("Multiplier for the durability of all new multitools.")
            .comment("Applied to the tier's defined durability as a buff.")
            .defineInRange("multitool_durability_multiplier", 1.25, 0.0, 1024);

    public static final ModConfigSpec.BooleanValue AXE_MULTITOOLS_DISABLE_SHIELD = BUILDER
            .comment("Controls if multitools made with an axe can disable shields when used as a weapon.")
            .define("axe_multitools_disable_shield", false);

    public static final ModConfigSpec.BooleanValue LOG_TIERS = BUILDER
            .comment("Whether to log all detected vanilla tool tiers and dynamic tiers on server setup.")
            .define("log_tiers", true);

    static final ModConfigSpec SPEC = BUILDER.build();


    // Helper
    public static float getConfigFloat(ModConfigSpec.DoubleValue config) {
        if (SPEC.isLoaded()) return (float) config.getAsDouble();
        else return config.getDefault().floatValue();
    }
}
