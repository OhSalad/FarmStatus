package xyz.salad.farmstatus;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.salad.farmstatus.init.MainConfig;

import static xyz.salad.farmstatus.FarmingUtils.*;
import static xyz.salad.farmstatus.PlayerAPI.player;
import static xyz.salad.farmstatus.init.MainConfig.startTime;


public class FarmingFailsafe {
    int ticksPlaying = 0;
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (MainConfig.farmingFailsafe) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.thePlayer == null || event.phase != TickEvent.Phase.END) {
                return;
            }
            if(System.currentTimeMillis() - startTime >= 3000 ){

                if ((checkStillness(player) || checkRotation(player)) || isStandingOnBedrock(player) && ticksPlaying == 0) {
                    ticksPlaying = 60;
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED +"YOU ARE BEING MACRO CHECKED!! ACT NORMAL"));
                }
                if (ticksPlaying > 0) {
                    player.playSound("fireworks.blast", 10.0F, 10.0F);
                    ticksPlaying--;
                    if (ticksPlaying == 0) {
                        resetStillnessCounter();
                    }
                }
                updateLastPositionAndRotation(player);
            }
        }
    }

}
