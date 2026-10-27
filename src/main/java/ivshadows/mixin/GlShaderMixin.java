package ivshadows.mixin;

import ivshadows.shader.ShadowDistortionPatcher;
import net.irisshaders.iris.gl.shader.GlShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = GlShader.class, remap = false)
public class GlShaderMixin {

    @ModifyArg(
        method = "<init>(Lnet/irisshaders/iris/gl/shader/ShaderType;Ljava/lang/String;Ljava/lang/String;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/irisshaders/iris/gl/shader/GlShader;createShader(Lnet/irisshaders/iris/gl/shader/ShaderType;Ljava/lang/String;Ljava/lang/String;)I"
        ),
        index = 2,
        remap = false
    )
    private static String ivshadows$patchShadowDistortion(String source) {
        return ShadowDistortionPatcher.patch(source);
    }
}
