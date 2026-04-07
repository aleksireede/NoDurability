package net.jonnegaming.nodurability;

import net.jonnegaming.nodurability.event.AutoRepairEvent;
import net.jonnegaming.nodurability.event.CombustEvent;
import net.jonnegaming.nodurability.event.DamageEvent;
import net.jonnegaming.nodurability.util.ItemDurabilityUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * NoDurability - Removes durability from the game
 *
 * @author arian
 * @version b1
 */
public final class NoDurability extends JavaPlugin {

    private final PluginManager pm = this.getServer().getPluginManager();
    private static NoDurability instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.initConfig();

        pm.registerEvents(new DamageEvent(), this);
        pm.registerEvents(new AutoRepairEvent(), this);
        pm.registerEvents(new CombustEvent(), this);

        final long playerRepairIntervalTicks = Math.max(1L, this.getConfig().getLong("player-auto-repair-interval-ticks", 1L));
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                ItemDurabilityUtil.repairInventory(player);
            }
        }, 0L, playerRepairIntervalTicks);

        final long hopperRepairIntervalTicks = Math.max(1L, this.getConfig().getLong("hopper-auto-repair-interval-ticks", 20L));
        getServer().getScheduler().runTaskTimer(this, ItemDurabilityUtil::repairLoadedHoppers, 0L, hopperRepairIntervalTicks);

        if (pm.getPlugin("PlaceholderAPI") != null)
            new NoDurabilityPAPIExtension(this);

    }

    private void initConfig() {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    /**
     * Gets the instance of the plugin.
     *
     * @return instance
     */
    public static NoDurability get() {
        if (instance != null) {
            return instance;
        } else {
            throw new NullPointerException("The instance of NoDurability is null!");
        }
    }

    public boolean shouldPreventItemCombustion() {
        if (this.getConfig().contains("prevent-item-combustion")) {
            return this.getConfig().getBoolean("prevent-item-combustion");
        }

        return !this.getConfig().getBoolean("combust-items", true);
    }
}
