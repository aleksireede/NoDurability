package net.jonnegaming.nodurability.event;

import net.jonnegaming.nodurability.util.ItemDurabilityUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public final class AutoRepairEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ItemDurabilityUtil.scheduleInventoryRepair(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        ItemDurabilityUtil.scheduleInventoryRepair(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        ItemDurabilityUtil.scheduleInventoryRepair(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onShearEntity(PlayerShearEntityEvent event) {
        ItemDurabilityUtil.scheduleInventoryRepair(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        ItemDurabilityUtil.scheduleInventoryRepair(event.getPlayer());
    }

    @EventHandler
    public void onHeldItemChange(PlayerItemHeldEvent event) {
        ItemDurabilityUtil.scheduleInventoryRepair(event.getPlayer());
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        ItemDurabilityUtil.scheduleInventoryRepair(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            ItemDurabilityUtil.scheduleInventoryRepair(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemDurabilityUtil.scheduleInventoryRepair(player);
        }
    }
}
