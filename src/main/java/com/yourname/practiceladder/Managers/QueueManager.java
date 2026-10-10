package com.yourname.practiceladder.Managers;

import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.entity.Player;

import java.util.*;

public class QueueManager {

    private final PracticeLadder plugin;
    private final ArenaManager arenaManager;
    private final MatchManager matchManager;

    private final Map<String, Queue<Player>> ladderQueues = new HashMap<>();
    private final Map<UUID, String> queuedPlayers = new HashMap<>();

    public QueueManager(PracticeLadder plugin, ArenaManager arenaManager, MatchManager matchManager) {
        this.plugin = plugin;
        this.arenaManager = arenaManager;
        this.matchManager = matchManager;
    }

    public void joinQueue(Player player, String ladder) {
        if (queuedPlayers.containsKey(player.getUniqueId())) {
            player.sendMessage("§cYou are already in the queue.");
            return;
        }

        String normalized = ladder.toLowerCase();
        ladderQueues.computeIfAbsent(normalized, k -> new ArrayDeque<>());

        Queue<Player> queue = ladderQueues.get(normalized);
        queue.add(player);
        queuedPlayers.put(player.getUniqueId(), normalized);

        player.sendMessage("§aYou joined the " + normalized + " queue.");

        matchPlayers(normalized);
    }

    public void leaveQueue(Player player) {
        String ladder = queuedPlayers.remove(player.getUniqueId());
        if (ladder == null) return;

        Queue<Player> queue = ladderQueues.get(ladder);
        if (queue != null) {
            queue.remove(player);
        }

        player.sendMessage("§cYou left the queue.");
    }

    public void matchPlayers(String ladder) {
        Queue<Player> queue = ladderQueues.get(ladder);
        if (queue == null || queue.size() < 2) return;

        Player p1 = queue.poll();
        Player p2 = queue.poll();

        queuedPlayers.remove(p1.getUniqueId());
        queuedPlayers.remove(p2.getUniqueId());

        if (p1 == null || p2 == null) return;

        matchManager.startMatch(p1, p2, ladder);
    }
}