package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.Managers.DataManager;
import com.yourname.practiceladder.Managers.DisplayManager;
import com.yourname.practiceladder.Managers.RankingManager;
import com.yourname.practiceladder.Model.PlayerStats;
import com.yourname.practiceladder.Model.RankProgress;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class StatsCommand implements CommandExecutor {

    private final PracticeLadder plugin;
    private final DataManager dataManager;
    private final RankingManager rankingManager;
    private final DisplayManager displayManager;

    public StatsCommand(PracticeLadder plugin, DataManager dataManager, RankingManager rankingManager, DisplayManager displayManager) {
        this.plugin = plugin;
        this.dataManager = dataManager;
        this.rankingManager = rankingManager;
        this.displayManager = displayManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can view stats.");
            return true;
        }

        UUID uuid = player.getUniqueId();
        PlayerStats stats = dataManager.getPlayerStats(uuid);

        player.sendMessage("§8§l=== Stats ===");
        player.sendMessage("§7Wins: §f" + stats.getWins());
        player.sendMessage("§7Losses: §f" + stats.getLosses());

        List<String> gamemodes = plugin.getConfig().getStringList("gamemodes");
        if (gamemodes.isEmpty()) {
            gamemodes = java.util.Arrays.asList("nethpot", "diapot", "sword");
        }

        player.sendMessage("§7\n§eRanks:");
        for (String gamemode : gamemodes) {
            RankProgress progress = rankingManager.getRankProgress(uuid, gamemode);
            String gamemodeColor = plugin.getConfig().getString("gamemodes." + gamemode + ".color", "§f");
            String gamemodeDisplay = plugin.getConfig().getString("gamemodes." + gamemode + ".display", gamemode);
            player.sendMessage(gamemodeColor + gamemodeDisplay + ": §f" + progress.getDisplayName());
        }

        return true;
    }
}
