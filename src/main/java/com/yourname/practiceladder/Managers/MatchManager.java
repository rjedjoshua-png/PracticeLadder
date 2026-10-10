package com.yourname.practiceladder.Managers;

import com.yourname.practiceladder.Model.Arena;
import com.yourname.practiceladder.Model.Match;
import com.yourname.practiceladder.Model.PlayerStats;
import com.yourname.practiceladder.Model.RankProgress;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MatchManager {

    private final PracticeLadder plugin;
    private final ArenaManager arenaManager;
    private final RankingManager rankingManager;
    private final DataManager dataManager;

    private final Map<UUID, Match> activeMatches = new HashMap<>();

    public MatchManager(PracticeLadder plugin, ArenaManager arenaManager, RankingManager rankingManager, DataManager dataManager) {
        this.plugin = plugin;
        this.arenaManager = arenaManager;
        this.rankingManager = rankingManager;
        this.dataManager = dataManager;
    }

    public void startMatch(Player player1, Player player2, String ladder) {
        Arena arena = arenaManager.getAvailableArena();
        if (arena == null) {
            player1.sendMessage("§cNo arena available.");
            player2.sendMessage("§cNo arena available.");
            return;
        }

        arena.setInUse(true);

        player1.teleport(arena.getSpawn1());
        player2.teleport(arena.getSpawn2());

        Match match = new Match(player1, player2, ladder, arena);
        activeMatches.put(player1.getUniqueId(), match);
        activeMatches.put(player2.getUniqueId(), match);

        player1.sendMessage("§aMatch started against " + player2.getName() + " in " + ladder + ".");
        player2.sendMessage("§aMatch started against " + player1.getName() + " in " + ladder + ".");
    }

    public void endMatch(Player winner, Player loser, String ladder) {
        Match match = activeMatches.remove(winner.getUniqueId());
        if (match == null) return;

        activeMatches.remove(loser.getUniqueId());

        Arena arena = match.getArena();
        arena.setInUse(false);

        // Update winner stats
        PlayerStats winnerStats = dataManager.getPlayerStats(winner.getUniqueId());
        winnerStats.addWin();
        dataManager.savePlayerStats(winner.getUniqueId(), winnerStats);

        // Update loser stats
        PlayerStats loserStats = dataManager.getPlayerStats(loser.getUniqueId());
        loserStats.addLoss();
        dataManager.savePlayerStats(loser.getUniqueId(), loserStats);

        // Add progression
        rankingManager.addWinProgression(winner.getUniqueId(), ladder);
        rankingManager.addLossProgression(loser.getUniqueId(), ladder);

        // Get new rank info
        RankProgress winnerRank = rankingManager.getRankProgress(winner.getUniqueId(), ladder);
        RankProgress loserRank = rankingManager.getRankProgress(loser.getUniqueId(), ladder);

        winner.sendMessage("§a✓ You won! " + winnerRank.getDisplayName());
        loser.sendMessage("§c✗ You lost. " + loserRank.getDisplayName());

        resetPlayer(winner);
        resetPlayer(loser);
    }

    public void resetPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
        player.setFireTicks(0);
        player.setExhaustion(0.0F);
    }
}