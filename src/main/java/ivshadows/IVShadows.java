package ivshadows;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod("ivshadows")
public final class IVShadows {

    public IVShadows(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, IVShadowsConfig.SPEC);
    }
}
