package xyz.salad.farmstatus;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

import static xyz.salad.farmstatus.FarmStatus.mc;

public class PlayerAPI {
    public static EntityPlayerSP player = mc.thePlayer;
    public static World world = mc.theWorld;
}
