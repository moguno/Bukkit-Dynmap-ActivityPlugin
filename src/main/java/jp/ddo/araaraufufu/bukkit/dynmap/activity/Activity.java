package jp.ddo.araaraufufu.bukkit.dynmap.activity;

import org.bukkit.entity.Player;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/18
 * Time: 0:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class Activity {
    String message;

    public Activity(String aMessage) {
        message = aMessage;
    }

    public abstract void fire(Player player);
}
