package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PracticeCommand implements CommandExecutor {

    private final PracticeLadder plugin;

    public PracticeCommand(PracticeLadder plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        player.sendMessage("§e=== Practice Ladder ===");
        player.sendMessage("§7Use /queue <ladder> to join a queue");
        player.sendMessage("§7Available ladders: vanilla, sumo, archer, no_debuff");
        player.sendMessage("§7Use /stats to view your stats");
        player.sendMessage("§7Use /rank to view your rank progression");
        return true;
    }
}