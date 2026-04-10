package net.jonnegaming.nodurability.util;

import net.jonnegaming.nodurability.NoDurability;
import org.bukkit.Material;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public final class ItemDurabilityUtil {

    public static final String USE_PERMISSION = "nodurability.use";

    private ItemDurabilityUtil() {
    }

    public static boolean canUse(@NotNull Player player) {
        return player.hasPermission(USE_PERMISSION);
    }

    public static boolean shouldApplyUnbreakable(@NotNull ItemStack item) {
        final Material type = item.getType();
        final String name = type.name();

        return name.endsWith("_SWORD")
                || name.endsWith("_AXE")
                || name.endsWith("_HOE")
                || name.endsWith("_PICKAXE")
                || name.endsWith("_SHOVEL")
                || name.endsWith("_HELMET")
                || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS")
                || name.endsWith("_BOOTS")
                || type == Material.BOW
                || type == Material.CROSSBOW
                || type == Material.TRIDENT
                || type == Material.SHEARS
                || type == Material.SHIELD
                || type == Material.FLINT_AND_STEEL
                || type == Material.CARROT_ON_A_STICK
                || type == Material.WARPED_FUNGUS_ON_A_STICK
                || type == Material.FISHING_ROD
                || type == Material.BRUSH
                || type == Material.ELYTRA
                || name.equals("SPARKLER")
                || name.equals("GLOW_STICK");
    }

    public static boolean repairItem(ItemStack item) {
        if (item == null) {
            return false;
        }

        final ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable damageable)) {
            return false;
        }

        boolean changed = false;
        if (damageable.getDamage() != 0) {
            damageable.setDamage(0);
            changed = true;
        }

        // First statement will check if item is a type that should have unbreakable tag and does not have it already
        // Second will check items that should not have it but do and remove the tag.
        if (shouldApplyUnbreakable(item) && !meta.isUnbreakable()) {
            meta.setUnbreakable(true);
            changed = true;
        } else if (!shouldApplyUnbreakable(item) && meta.isUnbreakable()) {
            meta.setUnbreakable(false);
            changed = true;
        }

        if (changed) {
            item.setItemMeta(meta);
        }

        return changed;
    }

    public static void repairInventory(@NotNull Player player) {
        final ItemStack[] contents = player.getInventory().getContents();
        boolean changed = false;

        for (final ItemStack item : contents) {
            if (item == null) {
                continue;
            }

            changed |= repairItem(item);
        }

        if (changed) {
            player.getInventory().setContents(contents);
            player.updateInventory();
        }

    }

    public static boolean repairInventory(@NotNull Inventory inventory) {
        final ItemStack[] contents = inventory.getContents();
        boolean changed = false;

        for (final ItemStack item : contents) {
            changed |= repairItem(item);
        }

        if (changed) {
            inventory.setContents(contents);
        }

        return changed;
    }

    public static void repairLoadedHoppers() {

        for (World world : NoDurability.get().getServer().getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (BlockState state : chunk.getTileEntities()) {
                    if (!(state instanceof Hopper hopper)) {
                        continue;
                    }

                    if (repairInventory(hopper.getInventory())) {
                        hopper.update();
                    }
                }
            }
        }

    }

    public static void scheduleInventoryRepair(@NotNull Player player) {
        NoDurability.get().getServer().getScheduler().runTask(NoDurability.get(), () -> repairInventory(player));
    }
}
