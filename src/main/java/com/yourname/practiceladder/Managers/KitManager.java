package com.yourname.practiceladder.Managers;

import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitManager {

    private final PracticeLadder plugin;

    public KitManager(PracticeLadder plugin) {
        this.plugin = plugin;
    }

    public void giveKit(Player player, String kitName) {
        player.getInventory().clear();

        if (kitName.equalsIgnoreCase("vanilla")) {
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
            player.getInventory().addItem(new ItemStack(Material.BOW));
            player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        }
    }
}