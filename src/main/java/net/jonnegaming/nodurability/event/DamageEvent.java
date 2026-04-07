package net.jonnegaming.nodurability.event;

import net.jonnegaming.nodurability.util.ItemDurabilityUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public final class DamageEvent implements Listener {

    /**
     * Prevent {@link ItemStack} form being damaged.
     *
     * @param event {@link PlayerItemDamageEvent}
     */
    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        if (ItemDurabilityUtil.canUse(event.getPlayer())) return;

        event.setDamage(0);
        event.setCancelled(true);
        ItemDurabilityUtil.scheduleInventoryRepair(event.getPlayer());
    }
}
