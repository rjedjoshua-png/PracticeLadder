package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.Managers.DataManager;
import com.yourname.practiceladder.Managers.RankingManager;
import com.yourname.practiceladder.Model.PlayerStats;
import com.yourname.practiceladder.Model.RankProgress;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StatsCommand implements CommandExecutor {

    private final PracticeLadder plugin;
    private final DataManager dataManager;
    private final RankingManager rankingManager;

    public StatsCommand(PracticeLadder plugin, DataManager dataManager, RankingManager rankingManager) {
        this.plugin = plugin;
        this.dataManager = dataManager;
        this.rankingManager = rankingManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can view stats.");
            return true;
        }

        UUID uuid = player.getUniqueId();
        PlayerStats stats = dataManager.getPlayerStats(uuid);

        player.sendMessage("§e=== Your Stats ===");
        player.sendMessage("§7Wins: §f" + stats.getWins());
        player.sendMessage("§7Losses: §f" + stats.getLosses());
        
        if (stats.getWins() + stats.getLosses() > 0) {
            double winrate = (double) stats.getWins() / (stats.getWins() + stats.getLosses()) * 100;
            player.sendMessage("§7Win Rate: §f" + String.format("%.1f%%", winrate));
        }

        player.sendMessage("§7\n§eRanks:");
        RankProgress vanillaRank = rankingManager.getRankProgress(uuid, "vanilla");
        player.sendMessage("§7Vanilla: §f" + vanillaRank.getDisplayName());
        
        RankProgress sumoRank = rankingManager.getRankProgress(uuid, "sumo");
        player.sendMessage("§7Sumo: §f" + sumoRank.getDisplayName());
        return true;
    }
}