package xyz.salad.farmstatus.init;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class MainConfig extends Config {

    public MainConfig() {
        super(new Mod("FarmStatus", ModType.SKYBLOCK), "config.json");
        initialize();
    }

}
