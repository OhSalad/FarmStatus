package com.github.ohsalad.farmstatus;

import net.minecraft.client.Minecraft;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FarmingCommand extends CommandBase {
    protected static boolean startFarming = false;
    protected static long startTime = 0;
    @SubscribeEvent
    public void onCommandInput(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        // Handle command input directly from chat
        if (message.startsWith("/startfarming ")) {
            processCommand(Minecraft.getMinecraft().thePlayer, message.split(" "));
            startFarming = true;
        }
    }

    @Override
    public String getCommandName() {
        return "startfarming";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Starts farming!";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(sender!= null){
            startTime = System.currentTimeMillis();
            startFarming = !startFarming;
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN +"startFarming is now " + EnumChatFormatting.DARK_AQUA + startFarming));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // No permission level required
    }
}
