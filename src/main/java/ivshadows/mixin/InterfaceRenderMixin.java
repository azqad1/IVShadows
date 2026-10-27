package ivshadows.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import mcinterface1211.InterfaceRender;
import net.irisshaders.iris.shadows.ShadowRenderer;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = InterfaceRender.class, remap = false)
public class InterfaceRenderMixin {

    @Redirect(
        method = "renderBuffers",
        at = @At(
            value = "FIELD",
            target = "Lmcinterface1211/InterfaceRender;projectionMatrix:Lorg/joml/Matrix4f;",
            opcode = Opcodes.GETSTATIC
        ),
        remap = false
    )
    private static Matrix4f ivshadows$shadowProjection() {
        if (ShadowRenderer.ACTIVE) {
            return RenderSystem.getProjectionMatrix();
        }

        return InterfaceRender.projectionMatrix;
    }
}
