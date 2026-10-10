package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.Managers.MatchManager;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {

    private final PracticeLadder plugin;
    private final MatchManager matchManager;

    public DuelCommand(PracticeLadder plugin, MatchManager matchManager) {
        this.plugin = plugin;
        this.matchManager = matchManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can duel.");
            return true;
        }

        if (args.length != 2) {
            player.sendMessage("§cUsage: /duel <player> <ladder>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§cPlayer not found.");
            return true;
        }

        String ladder = args[1];
        player.sendMessage("§aYou challenged " + target.getName() + " to a " + ladder + " duel.");
        target.sendMessage("§6" + player.getName() + " challenged you to a " + ladder + " duel. Type /accept or /decline.");
        return true;
    }
}