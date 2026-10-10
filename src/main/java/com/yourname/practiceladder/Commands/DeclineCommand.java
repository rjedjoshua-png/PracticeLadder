package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.Managers.MatchManager;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeclineCommand implements CommandExecutor {

    private final PracticeLadder plugin;
    private final MatchManager matchManager;

    public DeclineCommand(PracticeLadder plugin, MatchManager matchManager) {
        this.plugin = plugin;
        this.matchManager = matchManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can decline a duel.");
            return true;
        }

        player.sendMessage("§cDuel declined.");
        return true;
    }
}