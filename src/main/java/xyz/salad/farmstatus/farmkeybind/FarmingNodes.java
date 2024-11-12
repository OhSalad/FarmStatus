package xyz.salad.farmstatus.farmkeybind;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static xyz.salad.farmstatus.FarmStatus.mc;

public class FarmingNodes {
    public static final List<BlockPos> blockPositions = Collections.synchronizedList(new ArrayList<>());

    public static void addBlock(World world, Vec3 pos) {
        if (mc.thePlayer!= null && mc.theWorld!= null) {
            BlockPos roundedPos = new BlockPos(Math.floor(pos.xCoord), Math.floor(pos.yCoord) - 1, Math.floor(pos.zCoord));
            synchronized (blockPositions) {
                if (blockPositions.contains(roundedPos)) {
                    System.out.println("Block already exists in the list. Removing the block instead..");
                    blockPositions.remove(roundedPos);
                    updateFile();
                    return;
                }
                blockPositions.add(roundedPos);
            }
            updateFile();
        } else {
            System.out.println("Error adding a block! World is null.");
        }
    }

    private static void updateFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = new JsonArray();

        synchronized (blockPositions) {
            for (BlockPos pos : blockPositions) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("x", pos.getX());
                jsonObject.addProperty("y", pos.getY());
                jsonObject.addProperty("z", pos.getZ());
                jsonArray.add(jsonObject);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FarmingNodes.json", false))) {
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadBlocksFromFile() {
        try (FileReader reader = new FileReader("FarmingNodes.json")) {
            JsonElement jsonElement = new JsonParser().parse(reader);
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                synchronized (blockPositions) {
                    blockPositions.clear();
                    for (JsonElement element : jsonArray) {
                        JsonObject jsonObject = element.getAsJsonObject();
                        int x = jsonObject.get("x").getAsInt();
                        int y = jsonObject.get("y").getAsInt();
                        int z = jsonObject.get("z").getAsInt();
                        blockPositions.add(new BlockPos(x, y, z));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addFarmingNode() {
        System.out.println("addFarmingNode executed!");
        if (mc.thePlayer!= null && mc.theWorld!= null) {
            Vec3 pos = mc.thePlayer.getPositionVector();
            addBlock(mc.theWorld, pos);
        } else {
            System.out.println("Error adding a block! Player or world is null.");
        }
    }

}