package xyz.salad.farmstatus.farmkeybind;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static xyz.salad.farmstatus.farmkeybind.FarmingKeybindingHandler.changeKeybindingBasedOnPosition;

public class KeybindingMethods {
    private static int tickCounter = 0;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            tickCounter++;
            if (tickCounter >= 40) {
                changeKeybindingBasedOnPosition();
                tickCounter = 0;
            }
        }
    }
}
