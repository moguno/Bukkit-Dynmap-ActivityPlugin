package com.dinnerbone.bukkit.sample;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/18
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public class EntityDamageByEntityEventFilter {
    int damagerTypeId;
    int damageeTypeId;
    EntityDamageEvent.DamageCause cause;
    Activity activity;
    protected static ArrayList<EntityDamageByEntityEventFilter> filterList;
    protected static Activity defaultActivity;

    public static void add(int aDamagerTypeId, int aDamageeTypeId, EntityDamageEvent.DamageCause aCause, Activity aActivity) {
        if (filterList == null) {
            filterList = new ArrayList<EntityDamageByEntityEventFilter>();
        }

        filterList.add(new EntityDamageByEntityEventFilter(aDamagerTypeId, aDamageeTypeId, aCause, aActivity));
    }

    public static void setDefaultActivity(Activity aActivity) {
        defaultActivity = aActivity;
    }

    protected EntityDamageByEntityEventFilter(int aDamagerTypeId, int aDamageeTypeId, EntityDamageEvent.DamageCause aCause, Activity aActivity) {
        damagerTypeId = aDamagerTypeId;
        damageeTypeId = aDamageeTypeId;
        cause = aCause;
        activity = aActivity;
    }

    protected static void filter(EntityDamageByEntityEvent event) {
        boolean result = false;

        for (EntityDamageByEntityEventFilter filter : filterList) {

            if (filter.doFilter(event)) {
                return;
            }
        }

        if (defaultActivity != null) {
            defaultActivity.fire((Player)event.getDamager());
        }
    }

    public boolean doFilter(EntityDamageByEntityEvent event) {
        boolean result = true;

        result &= (damagerTypeId == -1) || (event.getDamager().getType().getTypeId() == damagerTypeId);

        result &= (damageeTypeId == -1) || (event.getEntity().getType().getTypeId() == damageeTypeId);

        result &= (cause == null) || (event.getCause().equals(cause));

        result &= (damagerTypeId == EntityType.PLAYER.getTypeId()) || (damageeTypeId == EntityType.PLAYER.getTypeId());

        if (result) {
            Player player = null;

            if (damagerTypeId == EntityType.PLAYER.getTypeId()) {
                player = (Player)event.getDamager();
            } else if (damageeTypeId == EntityType.PLAYER.getTypeId()) {
                player = (Player)event.getEntity();
            }

            activity.fire(player);
        }

        return result;
    }
}