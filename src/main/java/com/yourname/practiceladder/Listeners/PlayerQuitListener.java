package com.yourname.practiceladder.Listeners;

import com.yourname.practiceladder.Managers.MatchManager;
import com.yourname.practiceladder.Managers.QueueManager;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PracticeLadder plugin;
    private final QueueManager queueManager;
    private final MatchManager matchManager;

    public PlayerQuitListener(PracticeLadder plugin, QueueManager queueManager, MatchManager matchManager) {
        this.plugin = plugin;
        this.queueManager = queueManager;
        this.matchManager = matchManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        queueManager.leaveQueue(player);
    }
}