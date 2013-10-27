package jp.ddo.araaraufufu.bukkit.dynmap.activity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/20
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class CreatureSpawnEventFilter {
    int entityTypeId;
    CreatureSpawnEvent.SpawnReason reason;
    Activity activity;
    protected static ArrayList<CreatureSpawnEventFilter> filterList;
    protected static Activity defaultActivity;
    protected static final double range = 20.0;

    public static void add(int aEntityTypeId, CreatureSpawnEvent.SpawnReason aReason, Activity aActivity) {
        if (filterList == null) {
            filterList = new ArrayList<CreatureSpawnEventFilter>();
        }

        filterList.add(new CreatureSpawnEventFilter(aEntityTypeId, aReason, aActivity));
    }

    public static void setDefaultActivity(Activity aActivity) {
        defaultActivity = aActivity;
    }

    protected CreatureSpawnEventFilter(int aEntityTypeId, CreatureSpawnEvent.SpawnReason aReason, Activity aActivity) {
        entityTypeId = aEntityTypeId;
        reason = aReason;
        activity = aActivity;
    }

    protected static void filter(CreatureSpawnEvent event) {
        for (CreatureSpawnEventFilter filter : filterList) {
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

    protected static Player getNearbyPlayer(Entity entity, double aRange) {
        for (Entity entity1 : entity.getNearbyEntities(aRange, aRange, aRange)) {
            if (entity1 instanceof Player) {
                return (Player)entity1;
            }
        }

        return null;
    }

    public boolean doFilter(CreatureSpawnEvent event) {
        boolean result = true;

        result &= (entityTypeId == -1) || (event.getEntity().getType().getTypeId() == entityTypeId);

        result &= (reason == null) || (event.getSpawnReason().equals(reason));

        if (result) {
            Player player = getNearbyPlayer(event.getEntity(), range);

            if (player != null) {
                activity.fire(player);
            }
        }

        return result;
    }
}