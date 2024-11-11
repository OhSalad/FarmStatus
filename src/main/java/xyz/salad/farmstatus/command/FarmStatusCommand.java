package xyz.salad.farmstatus.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import net.minecraft.util.ChatComponentText;

import static xyz.salad.farmstatus.FarmStatus.mc;

@Command(value = "farmstatus", description = "Toggle Farmstatus")
public class FarmStatusCommand {

    @Main
    private void main() {
        mc.thePlayer.addChatMessage(new ChatComponentText("Hello World!"));
    }
}

