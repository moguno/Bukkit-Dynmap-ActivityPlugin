package com.dinnerbone.bukkit.sample;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/18
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
public class EntityDamageEventFilter {
    EntityDamageEvent.DamageCause cause;
    Activity activity;
    protected static ArrayList<EntityDamageEventFilter> filterList;
    protected static Activity defaultActivity;

    public static void add(EntityDamageEvent.DamageCause aCause, Activity aActivity) {
        if (filterList == null) {
            filterList = new ArrayList<EntityDamageEventFilter>();
        }

        filterList.add(new EntityDamageEventFilter(aCause, aActivity));
    }

    public static void setDefaultActivity(Activity aActivity) {
        defaultActivity = aActivity;
    }

    protected EntityDamageEventFilter(EntityDamageEvent.DamageCause aCause, Activity aActivity) {
        cause = aCause;
        activity = aActivity;
    }

    protected static void filter(EntityDamageEvent event) {
        boolean result = false;

        for (EntityDamageEventFilter filter : filterList) {

            if (filter.doFilter(event)) {
                return;
            }
        }

        if (defaultActivity != null) {
            defaultActivity.fire((Player)event.getEntity());
        }
    }

    public boolean doFilter(EntityDamageEvent event) {
        boolean result = true;

        result &= (event.getEntity().getType().equals(EntityType.PLAYER));

        result &= (cause == null) || (event.getCause().equals(cause));

        if (result) {
            activity.fire((Player)event.getEntity());
        }

        return result;
    }
}