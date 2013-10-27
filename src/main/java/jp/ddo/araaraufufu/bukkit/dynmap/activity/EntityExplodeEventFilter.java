package jp.ddo.araaraufufu.bukkit.dynmap.activity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/20
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public class EntityExplodeEventFilter {
    int entity;
    Activity activity;
    protected static ArrayList<EntityExplodeEventFilter> filterList;
    protected static Activity defaultActivity;
    protected static final double range = 20.0;

    public static void add(int aEntity, Activity aActivity) {
        if (filterList == null) {
            filterList = new ArrayList<EntityExplodeEventFilter>();
        }

        filterList.add(new EntityExplodeEventFilter(aEntity, aActivity));
    }

    public static void setDefaultActivity(Activity aActivity) {
        defaultActivity = aActivity;
    }

    protected EntityExplodeEventFilter(int aEntity, Activity aActivity) {
        entity = aEntity;
        activity = aActivity;
    }

    protected static Player getNearbyPlayer(Entity entity, double aRange) {
        for (Entity entity1 : entity.getNearbyEntities(aRange, aRange, aRange)) {
            if (entity1 instanceof Player) {
                return (Player)entity1;
            }
        }

        return null;
    }

    protected static void filter(EntityExplodeEvent event) {
        for (EntityExplodeEventFilter filter : filterList) {

            if (filter.doFilter(event)) {
                return;
            }
        }

        if (defaultActivity != null) {
            Player player = getNearbyPlayer(event.getEntity(), range);
            if (player != null) {
                defaultActivity.fire(player);
            }
        }
    }

    public boolean doFilter(EntityExplodeEvent event) {
        boolean result = true;

        result &= (event.getEntity().getType().getTypeId() == entity);

        Player player = getNearbyPlayer(event.getEntity(), range);

        result &= (player != null);

        if (result) {
            activity.fire(player);
        }

        return result;
    }
}