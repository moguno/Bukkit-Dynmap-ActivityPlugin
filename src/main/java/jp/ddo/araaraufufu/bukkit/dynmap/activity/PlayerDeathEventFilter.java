package jp.ddo.araaraufufu.bukkit.dynmap.activity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/20
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class PlayerDeathEventFilter {
    Activity activity;
    protected static ArrayList<PlayerDeathEventFilter> filterList;
    protected static Activity defaultActivity;

    public static void add(Activity aActivity) {
        if (filterList == null) {
            filterList = new ArrayList<PlayerDeathEventFilter>();
        }

        filterList.add(new PlayerDeathEventFilter(aActivity));
    }

    public static void setDefaultActivity(Activity aActivity) {
        defaultActivity = aActivity;
    }

    protected PlayerDeathEventFilter(Activity aActivity) {
        activity = aActivity;
    }

    protected static void filter(PlayerDeathEvent event) {

        for (PlayerDeathEventFilter filter : filterList) {
            if (filter.doFilter(event)) {
                return;
            }
        }

        if (defaultActivity != null) {
            defaultActivity.fire((Player)event.getEntity());
        }
    }

    public boolean doFilter(PlayerDeathEvent event) {
        activity.fire((Player)event.getEntity());

        return true;
    }
}