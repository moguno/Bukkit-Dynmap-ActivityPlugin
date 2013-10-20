package com.dinnerbone.bukkit.sample;

import org.bukkit.entity.Player;
import org.dynmap.DynmapAPI;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/18
 * Time: 0:26
 * To change this template use File | Settings | File Templates.
 */
public class LowPrioActivity extends Activity {
    public static DynmapAPI api;

    public LowPrioActivity(String aMessage) {
        super(aMessage);
    }

    public void fire(Player player) {
        if (api != null) {
            api.postPlayerMessageToWeb(player, message);
        }
    }
}
