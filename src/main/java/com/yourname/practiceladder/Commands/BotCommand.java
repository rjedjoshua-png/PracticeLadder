package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.Managers.MatchManager;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BotCommand implements CommandExecutor {

    private final PracticeLadder plugin;
    private final MatchManager matchManager;

    public BotCommand(PracticeLadder plugin, MatchManager matchManager) {
        this.plugin = plugin;
        this.matchManager = matchManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can duel bots.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§cUsage: /bot <ladder>");
            player.sendMessage("§7Ladders: vanilla, sumo, archer, no_debuff");
            return true;
        }

        String ladder = args[0].toLowerCase();
        Player botPlayer = Bukkit.getPlayer("Bot"); // Dummy bot player or NPC

        if (botPlayer == null) {
            player.sendMessage("§cBot is not available at the moment.");
            return true;
        }

        player.sendMessage("§aStarting bot duel in " + ladder + "...");
        matchManager.startMatch(player, botPlayer, ladder);
        return true;
    }
}
