package xyz.salad.farmstatus;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.salad.farmstatus.farmkeybind.KeybindingMethods;
import xyz.salad.farmstatus.init.MainConfig;
import xyz.salad.farmstatus.rendering.FarmingNodeRendering;


//todo: add modID constants
@Mod(modid = "farmstatus", useMetadata=true)
public class FarmStatus {

    public static MainConfig config;
    public static Minecraft mc = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        config = new MainConfig();
        MinecraftForge.EVENT_BUS.register(new FarmingFailsafe());
        MinecraftForge.EVENT_BUS.register(new KeybindingMethods());
        MinecraftForge.EVENT_BUS.register(new FarmingNodeRendering());

    }

}

