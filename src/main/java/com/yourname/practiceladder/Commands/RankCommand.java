package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.Managers.RankingManager;
import com.yourname.practiceladder.Model.RankProgress;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RankCommand implements CommandExecutor {

    private final PracticeLadder plugin;
    private final RankingManager rankingManager;

    public RankCommand(PracticeLadder plugin, RankingManager rankingManager) {
        this.plugin = plugin;
        this.rankingManager = rankingManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can view rank progression.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§cUsage: /rank <ladder>");
            player.sendMessage("§7Ladders: vanilla, sumo, archer, no_debuff");
            return true;
        }

        String ladder = args[0].toLowerCase();
        UUID uuid = player.getUniqueId();

        RankProgress rank = rankingManager.getRankProgress(uuid, ladder);
        player.sendMessage("§e=== " + ladder.toUpperCase() + " Rank ===");
        player.sendMessage("§7Current Rank: §f" + rank.getDisplayName());
        player.sendMessage("§7Progress to next: §f" + rank.getProgression() + "%");
        return true;
    }
}