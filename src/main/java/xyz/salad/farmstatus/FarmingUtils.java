package xyz.salad.farmstatus;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import static xyz.salad.farmstatus.FarmStatus.mc;
import static xyz.salad.farmstatus.init.MainConfig.farmingFailsafe;


public class FarmingUtils {
    private static final float ROTATION_THRESHOLD = 10.0f; // 10 degrees
    private static final int STILLNESS_THRESHOLD = 60; // 3 seconds at 20 ticks per second

    public static double lastPosX;
    public static double lastPosY;
    public static double lastPosZ;
    public static float lastYaw;
    public static float lastPitch;
    public static int stillnessCounter = 0;

    public static long startTime = System.currentTimeMillis();

    public static boolean checkStillness(EntityPlayerSP player) {
        if (!hasPlayerMoved(player)) {
            stillnessCounter++;
            return stillnessCounter >= STILLNESS_THRESHOLD;
        } else {
            resetStillnessCounter();
            return false;
        }
    }

    public static boolean checkRotation(EntityPlayerSP player) {
        // Check if player camera rotation exceeds the threshold
        return hasPlayerRotated(player);
    }

    public static boolean hasPlayerMoved(EntityPlayerSP player) {
        // Determine if player position has changed
        return player.posX != lastPosX || player.posY != lastPosY || player.posZ != lastPosZ;
    }

    public static boolean hasPlayerRotated(EntityPlayerSP player) {
        float yawDiff = Math.abs(player.rotationYaw - lastYaw);
        float pitchDiff = Math.abs(player.rotationPitch - lastPitch);
        // Normalize the yaw difference if it crosses 360-degree boundary
        if (yawDiff > 180.0f) {
            yawDiff = 360.0f - yawDiff;
        }
        return yawDiff >= ROTATION_THRESHOLD || pitchDiff >= ROTATION_THRESHOLD;
    }


    public static void resetStillnessCounter() {
        stillnessCounter = 0;
    }

    public static void updateLastPositionAndRotation(EntityPlayerSP player) {
        // Update the last known position and rotation for the next tick
        lastPosX = player.posX;
        lastPosY = player.posY;
        lastPosZ = player.posZ;
        lastYaw = player.rotationYaw;
        lastPitch = player.rotationPitch;
    }
    public static boolean isStandingOnBedrock(EntityPlayer player){
        int blockX = (int) Math.floor(player.posX);
        int blockY = (int) Math.floor(player.posY) - 1;
        int blockZ = (int) Math.floor(player.posZ);

        Block blockBelow = player.worldObj.getBlockState(new BlockPos(blockX, blockY, blockZ)).getBlock();
        return blockBelow == Blocks.bedrock;

    }
    public static void farmingFailsafe(){
        if(!farmingFailsafe)
            startTime = System.currentTimeMillis();
        farmingFailsafe = !farmingFailsafe;
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN +"Farming failsafe is now: " + EnumChatFormatting.GOLD + farmingFailsafe));
    }

}

