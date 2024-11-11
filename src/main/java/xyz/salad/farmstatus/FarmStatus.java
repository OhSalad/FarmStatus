package xyz.salad.farmstatus;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.salad.farmstatus.command.FarmStatusCommand;
import xyz.salad.farmstatus.init.MainConfig;


//todo: add modID constants
@Mod(modid = "farmstatus", useMetadata=true)
public class FarmStatus {

    public static MainConfig config;
    public static Minecraft mc = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        config = new MainConfig();
        CommandManager.register(new FarmStatusCommand());
    }

}

