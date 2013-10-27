
package jp.ddo.araaraufufu.bukkit.dynmap.activity;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

/**
 * Sample plugin for Bukkit
 *
 * @author Dinnerbone
 */
public class ActivityPlugin extends JavaPlugin {
    private final SampleBlockListener blockListener = new SampleBlockListener();
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    private DynmapAPI api;
    private boolean stop = false;


    /**
     * Sample block listener
     * @author Dinnerbone
     */
    public class SampleBlockListener implements Listener {
        @EventHandler
        public void onBlockBreak(BlockBreakEvent event) {
            BlockBreakEventFilter.filter(event);
        }

        @EventHandler
        public void onEntityDamage(EntityDamageEvent event) {
            if (event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEventFilter.filter((EntityDamageByEntityEvent)event);
            } else if (event instanceof EntityDamageEvent) {
                EntityDamageEventFilter.filter(event);
            }
        }

        @EventHandler
        public void onEntityExplode(EntityExplodeEvent event) {
            EntityExplodeEventFilter.filter(event);
        }

        @EventHandler
        public void onPlayerEggThrow(PlayerEggThrowEvent event) {
            PlayerEggThrowEventFilter.filter(event);
        }

        @EventHandler
        public void onEntityDeath(EntityDeathEvent event) {
            if (event instanceof PlayerDeathEvent) {
                PlayerDeathEventFilter.filter((PlayerDeathEvent)event);
            }
        }

        @EventHandler
        public void onCreatureSpawn(CreatureSpawnEvent event) {
            CreatureSpawnEventFilter.filter(event);
        }
    }

    private class Chat implements Runnable {
        public void run() {
            if(!stop) {
                getServer().getScheduler().scheduleSyncDelayedTask(ActivityPlugin.this, new Chat(), 5);
            }
        }
    }

    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here

        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        getLogger().info("Goodbye world!");
    }

    @Override
    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(blockListener, this);

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );

        Plugin dynmap = pm.getPlugin("dynmap");
        if(dynmap == null) {
            getLogger().log(Level.SEVERE, "Cannot find dynmap!");
            return;
        }
        api = (DynmapAPI)dynmap; /* Get API */

        LowPrioActivity.api = api;

        BlockBreakEventFilter.add(17, -1, new LowPrioActivity("木を切っています。"));
        BlockBreakEventFilter.add(18, -1, new LowPrioActivity("葉っぱをむしっています。"));
        BlockBreakEventFilter.add(14, -1, new LowPrioActivity("金鉱石を見つけました。"));
        BlockBreakEventFilter.add(15, -1, new LowPrioActivity("鉄鉱石を見つけました。"));
        BlockBreakEventFilter.add(82, -1, new LowPrioActivity("粘土を見つけました。"));
        BlockBreakEventFilter.add(21, -1, new LowPrioActivity("ラピスラズリを見つけました。"));
        BlockBreakEventFilter.add(56, -1, new LowPrioActivity("ダイアモンドを見つけました！"));
        BlockBreakEventFilter.add(129, -1, new LowPrioActivity("エメラルドを見つけました！"));
        BlockBreakEventFilter.add(49, -1, new LowPrioActivity("黒曜石を回収しました！"));

        BlockBreakEventFilter.add(12, -1, new LowPrioActivity("砂遊びをしています。"));
        BlockBreakEventFilter.add(31, -1, new LowPrioActivity("草を刈っています。"));
        BlockBreakEventFilter.add(83, -1, new LowPrioActivity("さとうきびを刈っています。"));
        BlockBreakEventFilter.add(37, -1, new LowPrioActivity("黄色いお花を摘みました。"));
        BlockBreakEventFilter.add(38, -1, new LowPrioActivity("赤いお花を摘みました。"));
        BlockBreakEventFilter.add(46, -1, new LowPrioActivity("TNTを回収しました。"));
        BlockBreakEventFilter.add(52, -1, new LowPrioActivity("スポナーを破壊しました！！"));
        BlockBreakEventFilter.add(66, -1, new LowPrioActivity("線路をはがしています。"));
        BlockBreakEventFilter.add(102, -1, new LowPrioActivity("窓ガラスを壊して回っています。"));
        BlockBreakEventFilter.add(141, -1, new LowPrioActivity("ニンジンを引っこ抜いています。"));
        BlockBreakEventFilter.add(142, -1, new LowPrioActivity("ジャガイモを掘り起こしています。"));
        BlockBreakEventFilter.add(59, -1, new LowPrioActivity("小麦を刈っています。"));
        BlockBreakEventFilter.add(127, -1, new LowPrioActivity("ココア豆をもぎました。"));

        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.ZOMBIE.getTypeId(), null, new LowPrioActivity("ゾンビと戦っています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.SPIDER.getTypeId(), null, new LowPrioActivity("クモと戦っています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.CAVE_SPIDER.getTypeId(), null, new LowPrioActivity("毒グモと戦っています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.SKELETON.getTypeId(), null, new LowPrioActivity("スケルトンと戦っています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.CREEPER.getTypeId(), null, new LowPrioActivity("クリーパーと勇敢に戦っています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.SILVERFISH.getTypeId(), null, new LowPrioActivity("シルバーフィッシュと戦っています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.ENDERMAN.getTypeId(), null, new LowPrioActivity("無謀にもエンダーマンに戦いを挑んでいます。"));

        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.SQUID.getTypeId(), null, new LowPrioActivity("イカをいじめています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.CHICKEN.getTypeId(), null, new LowPrioActivity("にわとりをいじめています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.COW.getTypeId(), null, new LowPrioActivity("牛をいじめています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.PIG.getTypeId(), null, new LowPrioActivity("ブタに罵声を浴びせかけています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.SHEEP.getTypeId(), null, new LowPrioActivity("羊をいじめています。"));
        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), EntityType.SLIME.getTypeId(), null, new LowPrioActivity("スライムに１ポイントのダメージをあたえた！"));

        EntityDamageByEntityEventFilter.add(EntityType.PLAYER.getTypeId(), -1, null, new LowPrioActivity("何かと戦っています。"));

        EntityExplodeEventFilter.add(EntityType.CREEPER.getTypeId(), new LowPrioActivity("匠がバクハツ！！！>_<"));

        PlayerEggThrowEventFilter.add(new LowPrioActivity("ヒヨコが生まれました。ビッグダディ！"));
        PlayerEggThrowEventFilter.setDefaultActivity(new LowPrioActivity("卵を投げています。"));

        PlayerDeathEventFilter.add(new LowPrioActivity("死んでしまいました・・・。"));

        EntityDamageEventFilter.add(EntityDamageEvent.DamageCause.FALL, new LowPrioActivity("足をくじきました・・・。"));

        CreatureSpawnEventFilter.add(EntityType.CHICKEN.getTypeId(), CreatureSpawnEvent.SpawnReason.BREEDING, new LowPrioActivity("ヒヨコが生まれました。"));
        CreatureSpawnEventFilter.add(EntityType.COW.getTypeId(), CreatureSpawnEvent.SpawnReason.BREEDING, new LowPrioActivity("子牛が生まれました。"));
        CreatureSpawnEventFilter.add(EntityType.PIG.getTypeId(), CreatureSpawnEvent.SpawnReason.BREEDING, new LowPrioActivity("子ブタが生まれました。"));
        CreatureSpawnEventFilter.add(EntityType.SHEEP.getTypeId(), CreatureSpawnEvent.SpawnReason.BREEDING, new LowPrioActivity("子羊が生まれました。"));
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}
