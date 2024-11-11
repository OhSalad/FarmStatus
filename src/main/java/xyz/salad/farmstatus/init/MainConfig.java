package xyz.salad.farmstatus.init;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.KeyBind;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.data.OptionSize;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;


import static xyz.salad.farmstatus.FarmStatus.mc;
import static xyz.salad.farmstatus.PlayerAPI.player;

public class MainConfig extends Config {
    public static long startTime;
    public MainConfig() {
        super(new Mod("FarmStatus", ModType.SKYBLOCK), "config.json");
        initialize();
        registerKeyBind(keyBind, MainConfig::farmingFailsafe);

    }
    public static void farmingFailsafe(){
        if(!farmingFailsafe)
            startTime = System.currentTimeMillis();
        farmingFailsafe = !farmingFailsafe;
        player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN +"Farming failsafe is now: " + EnumChatFormatting.GOLD + farmingFailsafe));
    }

    @KeyBind(
            name = "Farming failsafe keyBind"
    )        OneKeyBind keyBind = new OneKeyBind(UKeyboard.KEY_LSHIFT, UKeyboard.KEY_S);

// using OneKeyBind to set the default key combo to Shift+S
    @Switch(
            name = "failsafeToggle toggle",
            size = OptionSize.DUAL,
            subcategory = "Switches"
    )
    public static boolean farmingFailsafe = false;
}
