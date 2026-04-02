package net.jonnegaming.nodurability.command;

import net.jonnegaming.nodurability.NoDurability;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NoDurabilityCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(NoDurability.get().getConfig().getString("lang.only-a-player"));
            return true;
        }

        if (args.length == 1) {
            final Material material = Material.matchMaterial(args[0]);
            if (material == null) {
                return true;
            }

            boolean resetAny = false;
            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null || item.getType() != material) {
                    continue;
                }

                if (resetDurability(item)) {
                    resetAny = true;
                }
            }

            if (resetAny) {
                sender.sendMessage(NoDurability.get().getConfig().getString("lang.reset-durability-item"));
            }
            return true;
        }

        boolean resetAny = false;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) {
                continue;
            }

            if (resetDurability(item)) {
                resetAny = true;
            }
        }

        if (resetAny) {
            sender.sendMessage(NoDurability.get().getConfig().getString("lang.reset-durability-message"));
        }

        return true;
    }

    private boolean resetDurability(@NotNull ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable damageable)) {
            return false;
        }

        damageable.setDamage(0);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> materials = new ArrayList<>();
            for (Material material : Material.values()) {
                materials.add(material.name());
            }
            return materials;
        }
        return Collections.emptyList();
    }
}
