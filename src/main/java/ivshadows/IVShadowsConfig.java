package ivshadows;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class IVShadowsConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue SHADOW_DISTORTION_OVERRIDE = BUILDER
        .comment(
            "Replaces the shadow-space distortion constant computed inside the active shader pack's shaders.",
            "Shadow distortion warps shadow-map space so that resolution is concentrated near the player. It also",
            "warps long shadows, which is most visible when the sun is low and shadows stretch out.",
            "",
            "-1.0 = disabled. The pack's own value is used and no shader source is modified.",
            " 0.0 = no distortion. Shadow geometry becomes exact, at the cost of lower near-field resolution.",
            " 0.3-0.5 = reduced distortion, keeping some near-field detail.",
            "",
            "Both the shader that writes the shadow map and the shaders that read it are changed to the same value,",
            "so they always stay consistent with each other.",
            "",
            "Takes effect after a shader reload (F3+R) or after rejoining the world.")
        .defineInRange("shadowDistortionOverride", 0.0D, -1.0D, 0.95D);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private IVShadowsConfig() {
    }

    public static double shadowDistortionOverride() {
        if (!SPEC.isLoaded()) {
            return 0.0D;
        }

        return SHADOW_DISTORTION_OVERRIDE.get();
    }
}
