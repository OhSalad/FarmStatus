package xyz.salad.farmstatus.init;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.KeyBind;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.data.OptionSize;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import xyz.salad.farmstatus.FarmingUtils;
import xyz.salad.farmstatus.farmkeybind.FarmingNodes;

public class MainConfig extends Config {
    public static long startTime;
    public MainConfig() {
        super(new Mod("FarmStatus", ModType.SKYBLOCK), "config.json");
        initialize();
        registerKeyBind(failsafeKeybinding, FarmingUtils::farmingFailsafe);
        registerKeyBind(farmingNodeKeybinding, FarmingNodes::addFarmingNode);
    }

    @KeyBind(
            name = "Farming failsafe keyBind"
    )        OneKeyBind failsafeKeybinding = new OneKeyBind(UKeyboard.KEY_LSHIFT, UKeyboard.KEY_S);

    @KeyBind(
            name = "Add Farming Node"
    )        OneKeyBind farmingNodeKeybinding = new OneKeyBind(UKeyboard.KEY_LSHIFT, UKeyboard.KEY_ADD);

// using OneKeyBind to set the default key combo to Shift+S
    @Switch(
            name = "failsafeToggle toggle",
            size = OptionSize.DUAL,
            subcategory = "Switches"
    )
    public static boolean farmingFailsafe = false;

}
