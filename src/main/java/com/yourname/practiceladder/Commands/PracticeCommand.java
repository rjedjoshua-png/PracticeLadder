package com.yourname.practiceladder.Commands;

import com.yourname.practiceladder.Managers.MenuManager;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PracticeCommand implements CommandExecutor {

    private final PracticeLadder plugin;
    private final MenuManager menuManager;

    public PracticeCommand(PracticeLadder plugin, MenuManager menuManager) {
        this.plugin = plugin;
        this.menuManager = menuManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can open the practice menu.");
            return true;
        }

        player.openInventory(menuManager.createMainMenu());
        return true;
    }
}
