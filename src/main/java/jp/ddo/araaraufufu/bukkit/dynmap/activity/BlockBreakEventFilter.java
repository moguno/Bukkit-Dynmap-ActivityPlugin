package jp.ddo.araaraufufu.bukkit.dynmap.activity;

import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: moguno
 * Date: 2013/10/18
 * Time: 0:24
 * To change this template use File | Settings | File Templates.
 */
public class BlockBreakEventFilter {
    int blockTypeId;
    int data;
    Activity activity;
    protected static ArrayList<BlockBreakEventFilter> filterList;
    protected static Activity defaultActivity;

    public static void add(int aBlockTypeId, int aData, Activity aActivity) {
        if (filterList == null) {
            filterList = new ArrayList<BlockBreakEventFilter>();
        }

        filterList.add(new BlockBreakEventFilter(aBlockTypeId, aData, aActivity));
    }

    public static void setDefaultActivity(Activity aActivity) {
        defaultActivity = aActivity;
    }

    protected BlockBreakEventFilter(int aBlockTypeId, int aData, Activity aActivity) {
        blockTypeId = aBlockTypeId;
        data = aData;
        activity = aActivity;
    }

    protected static void filter(BlockBreakEvent event) {
        boolean result = false;

        for (BlockBreakEventFilter filter : filterList) {

            if (filter.doFilter(event)) {
                return;
            }
        }

        if (defaultActivity != null) {
            defaultActivity.fire(event.getPlayer());
        }
    }

    public boolean doFilter(BlockBreakEvent event) {
        boolean result = false;

        if (event.getBlock().getTypeId() == blockTypeId) {
            if (data == -1 || event.getBlock().getData() == data) {
                result = true;
            }
        }

        if (result) {
            activity.fire(event.getPlayer());
        }

        return result;
    }
}
