package xyz.salad.farmstatus.rendering;

import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import xyz.salad.farmstatus.farmkeybind.FarmingNodes;

import java.util.ArrayList;
import java.util.List;

import static xyz.salad.farmstatus.farmkeybind.FarmingNodes.blockPositions;
import static xyz.salad.farmstatus.rendering.BlockRendering.highlightBlock;

public class FarmingNodeRendering {
    // Local cached copy of blockPositions to avoid frequent synchronization during rendering
    private static final List<BlockPos> cachedBlockPositions = new ArrayList<>();
    private static boolean blocksLoaded = false; // Flag to track if blocks are loaded

    public static void highlightAllBlocks() {
        for (BlockPos block : cachedBlockPositions) {
            highlightBlock(block);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onWorldRender(RenderWorldLastEvent event) {
        synchronized (blockPositions) {
            cachedBlockPositions.clear();
            cachedBlockPositions.addAll(blockPositions);
        }
        highlightAllBlocks();
    }

    @SubscribeEvent
    public void onWorldLoad(TickEvent.ClientTickEvent event) {
        if (!blocksLoaded && event.phase == TickEvent.Phase.START) {
            blocksLoaded = true;
            synchronized (blockPositions) {
                blockPositions.clear();
            }
            FarmingNodes.loadBlocksFromFile();
        }
    }

    // Reset the blocksLoaded flag when the player leaves the world
    @SubscribeEvent
    public void onWorldUnload(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            blocksLoaded = false;
        }
    }
}
