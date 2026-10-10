package com.yourname.practiceladder.Listeners;

import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    private final PracticeLadder plugin;

    public InventoryListener(PracticeLadder plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Optional: implement inventory handling
    }
}