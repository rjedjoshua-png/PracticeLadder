package com.yourname.practiceladder.Listeners;

import com.yourname.practiceladder.Managers.MatchManager;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private final PracticeLadder plugin;
    private final MatchManager matchManager;

    public DamageListener(PracticeLadder plugin, MatchManager matchManager) {
        this.plugin = plugin;
        this.matchManager = matchManager;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        // Optional: implement damage logic
    }
}