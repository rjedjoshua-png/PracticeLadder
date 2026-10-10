package com.yourname.practiceladder.Listeners;

import com.yourname.practiceladder.Managers.DuelManager;
import com.yourname.practiceladder.Managers.MenuManager;
import com.yourname.practiceladder.Managers.QueueManager;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;

public class MenuClickListener implements Listener {

    private final PracticeLadder plugin;
    private final QueueManager queueManager;
    private final DuelManager duelManager;
    private final MenuManager menuManager;

    public MenuClickListener(PracticeLadder plugin, QueueManager queueManager, DuelManager duelManager, MenuManager menuManager) {
        this.plugin = plugin;
        this.queueManager = queueManager;
        this.duelManager = duelManager;
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        String title = event.getView().getTitle();
        event.setCancelled(true);

        if (title.equalsIgnoreCase("§8§lPractice Menu")) {
            handleMainMenuClick(player, event.getSlot());
        } else if (title.equalsIgnoreCase("§8§lSelect Gamemode")) {
            handleGamemodeMenuClick(player, event.getSlot());
        } else if (title.equalsIgnoreCase("§8§lSelect Kit")) {
            handleKitMenuClick(player, event.getSlot());
        } else if (title.equalsIgnoreCase("§8§lDuel Settings")) {
            handleSettingsMenuClick(player, event.getSlot());
        } else if (title.equalsIgnoreCase("§8§lChallenge Player")) {
            handlePlayerMenuClick(player, event.getSlot());
        }
    }

    private void handleMainMenuClick(Player player, int slot) {
        switch (slot) {
            case 10:
                player.openInventory(menuManager.createGamemodeMenu());
                break;
            case 12:
                player.openInventory(menuManager.createPlayerMenu(
                        Bukkit.getOnlinePlayers().stream()
                                .filter(p -> !p.equals(player))
                                .toList(), "nethpot"));
                break;
            case 14:
                player.openInventory(menuManager.createKitMenu());
                break;
            case 16:
                player.closeInventory();
                player.performCommand("stats");
                break;
            case 21:
                player.closeInventory();
                player.performCommand("bot nethpot");
                break;
            case 23:
                player.openInventory(menuManager.createSettingsMenu());
                break;
        }
    }

    private void handleGamemodeMenuClick(Player player, int slot) {
        List<String> gamemodes = plugin.getConfig().getStringList("gamemodes");
        if (gamemodes.isEmpty()) {
            gamemodes = Arrays.asList("nethpot", "diapot", "sword", "crystal", "uhc", "smp", "cart", "axe", "mace", "diasmp");
        }

        int index = 0;
        int menuSlot = 10;
        for (String gamemode : gamemodes) {
            if (menuSlot == slot) {
                player.closeInventory();
                queueManager.joinQueue(player, gamemode);
                return;
            }
            menuSlot++;
            if ((menuSlot - 10) % 9 == 0) menuSlot += 2;
        }
    }

    private void handleKitMenuClick(Player player, int slot) {
        String kit = switch (slot) {
            case 10 -> "vanilla";
            case 12 -> "sumo";
            case 14 -> "archer";
            case 16 -> "no_debuff";
            default -> null;
        };

        if (kit != null) {
            player.closeInventory();
            plugin.getKitManager().giveKit(player, kit);
            player.sendMessage("§aSelected kit: " + kit);
        }
    }

    private void handleSettingsMenuClick(Player player, int slot) {
        switch (slot) {
            case 11 -> player.sendMessage("§aRanked mode enabled.");
            case 13 -> player.sendMessage("§aUnranked mode enabled.");
            case 20 -> player.sendMessage("§aBest of 3 selected.");
            case 24 -> player.sendMessage("§aBest of 5 selected.");
        }
        player.closeInventory();
    }

    private void handlePlayerMenuClick(Player player, int slot) {
        var players = Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .toList();

        if (slot >= players.size()) return;

        Player target = players.get(slot);
        player.closeInventory();
        duelManager.sendRequest(player, target);
    }
}
