package jp.ddo.araaraufufu.bukkit.dynmap.activity;

import org.bukkit.event.player.PlayerEggThrowEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/20
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
public class PlayerEggThrowEventFilter {
    Activity activity;
    protected static ArrayList<PlayerEggThrowEventFilter> filterList;
    protected static Activity defaultActivity;

    public static void add(Activity aActivity) {
        if (filterList == null) {
            filterList = new ArrayList<PlayerEggThrowEventFilter>();
        }

        filterList.add(new PlayerEggThrowEventFilter(aActivity));
    }

    public static void setDefaultActivity(Activity aActivity) {
        defaultActivity = aActivity;
    }

    protected PlayerEggThrowEventFilter(Activity aActivity) {
        activity = aActivity;
    }

    protected static void filter(PlayerEggThrowEvent event) {
        if (event.isHatching()) {
            for (PlayerEggThrowEventFilter filter : filterList) {

                if (filter.doFilter(event)) {
                    return;
                }
            }
        }

        if (defaultActivity != null) {
            defaultActivity.fire(event.getPlayer());
        }
    }

    public boolean doFilter(PlayerEggThrowEvent event) {
        activity.fire(event.getPlayer());

        return true;
    }
}