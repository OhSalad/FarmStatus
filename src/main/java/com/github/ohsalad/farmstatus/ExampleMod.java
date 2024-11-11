package com.github.ohsalad.farmstatus;

import com.mojang.realmsclient.client.Ping;
import ibxm.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.github.ohsalad.farmstatus.FarmingCommand.startFarming;
import static com.github.ohsalad.farmstatus.FarmingCommand.startTime;
import static com.github.ohsalad.farmstatus.FarmingUtils.*;
import static net.minecraftforge.client.ForgeHooksClient.playSound;

//constants:

@Mod(modid = "farmstatus", useMetadata=true)
public class ExampleMod{
    int ticksPlaying = 0;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this); // Register the event listener
        // Register the client-side command
        ClientCommandHandler.instance.registerCommand(new FarmingCommand());
    }
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new FarmingCommand());
    }
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (startFarming) {
            Minecraft mc = Minecraft.getMinecraft();

            // Ensure player is in the game and not null
            if (mc.thePlayer == null || event.phase != TickEvent.Phase.END) {
                return;
            }
            if(System.currentTimeMillis() - startTime >= 3000 ){
            EntityPlayerSP player = mc.thePlayer;

            // Check if the player is still or rotated
            if ((checkStillness(player) || checkRotation(player)) || isStandingOnBedrock(player) && ticksPlaying == 0) {
                // Start playing sound for 3 seconds
                ticksPlaying = 60;
            }
            // If the timer is active, play the sound
            if (ticksPlaying > 0) {
                player.playSound("fireworks.blast", 1.0F, 1.0F);
                ticksPlaying--;
                // Reset stillness counter when the timer ends
                if (ticksPlaying == 0) {
                    resetStillnessCounter();
                }
            }
            updateLastPositionAndRotation(player);
        }
    }
        }
    }

