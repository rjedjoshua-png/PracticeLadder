package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.Managers.QueueManager;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueueCommand implements CommandExecutor {

    private final PracticeLadder plugin;
    private final QueueManager queueManager;

    public QueueCommand(PracticeLadder plugin, QueueManager queueManager) {
        this.plugin = plugin;
        this.queueManager = queueManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§cUsage: /queue <ladder>");
            return true;
        }

        queueManager.joinQueue(player, args[0]);
        return true;
    }
}