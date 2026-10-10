package com.yourname.practiceladder.Listeners;

import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PracticeLadder plugin;

    public PlayerJoinListener(PracticeLadder plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("§aWelcome! Use /practice for help.");
    }
}