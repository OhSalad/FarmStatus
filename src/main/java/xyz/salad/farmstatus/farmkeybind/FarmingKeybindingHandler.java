package xyz.salad.farmstatus.farmkeybind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import static xyz.salad.farmstatus.FarmStatus.mc;

public class FarmingKeybindingHandler {
    private static final KeyBinding strafeRightKey = Minecraft.getMinecraft().gameSettings.keyBindRight;
    private static final KeyBinding strafeLeftKey = Minecraft.getMinecraft().gameSettings.keyBindLeft;

    private static boolean keyChanged = false;

    private static final int walkRight = Keyboard.KEY_D;
    private static final int walkLeft = Keyboard.KEY_A;

    private static boolean isHoldingRight = false;
    private static boolean isHoldingLeft = false;

    // Check if the player is standing on a node
    public static boolean isPlayerOnNode() {
        if (mc.thePlayer != null && mc.theWorld != null) {
            Vec3 playerVec = mc.thePlayer.getPositionVector();
            BlockPos roundedPlayerPos = new BlockPos(Math.floor(playerVec.xCoord), Math.floor(playerVec.yCoord) - 1, Math.floor(playerVec.zCoord));
            synchronized (FarmingNodes.blockPositions) {
                return FarmingNodes.blockPositions.contains(roundedPlayerPos);
            }
        }
        return false;
    }

    // Change keybinding based on player position
    public static void changeKeybindingBasedOnPosition() {
        boolean onNode = isPlayerOnNode();

        // Only proceed if the player and world are loaded and no GUI is open
        if (mc.thePlayer != null && mc.theWorld != null && mc.currentScreen == null) {
            if (onNode) {
                if (Keyboard.isKeyDown(strafeRightKey.getKeyCode()) || Keyboard.isKeyDown(strafeLeftKey.getKeyCode())) {
                    mc.thePlayer.addChatMessage(new ChatComponentText("You are at a node! Changing keybinding..."));

                    // Release movement keys before changing direction
                    KeyBinding.setKeyBindState(strafeRightKey.getKeyCode(), false);
                    KeyBinding.setKeyBindState(strafeLeftKey.getKeyCode(), false);

                    // Toggle keybindings between left and right movement
                    if (!keyChanged) {
                        strafeRightKey.setKeyCode(walkLeft);
                        strafeLeftKey.setKeyCode(walkRight);
                        keyChanged = true;
                    } else {
                        // Revert to original direction
                        strafeRightKey.setKeyCode(walkRight);
                        strafeLeftKey.setKeyCode(walkLeft);
                        keyChanged = false;
                    }

                    // Check if movement keys are still held and set state accordingly
                    isHoldingRight = Keyboard.isKeyDown(walkRight);
                    isHoldingLeft = Keyboard.isKeyDown(walkLeft);

                    KeyBinding.setKeyBindState(strafeRightKey.getKeyCode(), isHoldingRight);
                    KeyBinding.setKeyBindState(strafeLeftKey.getKeyCode(), isHoldingLeft);
                }
            } else {
                // When off-node, only release the key if it was held due to a swap, not every tick
                if (!Keyboard.isKeyDown(walkRight) && isHoldingRight) {
                    KeyBinding.setKeyBindState(strafeRightKey.getKeyCode(), false);
                    isHoldingRight = false;
                }
                if (!Keyboard.isKeyDown(walkLeft) && isHoldingLeft) {
                    KeyBinding.setKeyBindState(strafeLeftKey.getKeyCode(), false);
                    isHoldingLeft = false;
                }
            }
        }
    }
}
