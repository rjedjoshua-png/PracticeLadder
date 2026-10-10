package com.yourname.practiceladder.Managers;

import com.yourname.practiceladder.Model.RankProgress;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MenuManager {

    private final PracticeLadder plugin;
    private final RankingManager rankingManager;

    public MenuManager(PracticeLadder plugin, RankingManager rankingManager) {
        this.plugin = plugin;
        this.rankingManager = rankingManager;
    }

    public Inventory createMainMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, "§8§lPractice Menu");

        inv.setItem(10, makeItem(Material.DIAMOND_SWORD, "§bQueue", "§7Join a gamemode queue"));
        inv.setItem(12, makeItem(Material.GOLDEN_SWORD, "§6Duel", "§7Challenge a player"));
        inv.setItem(14, makeItem(Material.CHEST, "§aKits", "§7Choose your kit"));
        inv.setItem(16, makeItem(Material.PAPER, "§eStats", "§7View profile and ladder info"));
        inv.setItem(21, makeItem(Material.IRON_SWORD, "§fBot Duel", "§7Practice against a bot"));
        inv.setItem(23, makeItem(Material.REDSTONE, "§dSettings", "§7Ranked / Unranked / Best of X"));

        return inv;
    }

    public Inventory createGamemodeMenu() {
        Inventory inv = Bukkit.createInventory(null, 45, "§8§lSelect Gamemode");

        List<String> gamemodes = new ArrayList<>();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("gamemodes");

        if (section != null && !section.getKeys(false).isEmpty()) {
            gamemodes.addAll(section.getKeys(false));
        } else {
            gamemodes = Arrays.asList("nethpot", "diapot", "sword", "crystal", "uhc", "smp", "cart", "axe", "mace", "diasmp");
        }

        int slot = 10;
        for (String gamemode : gamemodes) {
            String display = plugin.getConfig().getString("gamemodes." + gamemode + ".display", gamemode);
            String color = plugin.getConfig().getString("gamemodes." + gamemode + ".color", "§f");
            inv.setItem(slot, makeItem(Material.DIAMOND_SWORD, color + display, "§7Queue for " + display));
            slot++;
            if ((slot - 10) % 9 == 0) slot += 2;
        }

        return inv;
    }

    public Inventory createKitMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, "§8§lSelect Kit");

        inv.setItem(10, makeItem(Material.DIAMOND_SWORD, "§fVanilla Kit", "§7Sword + bow"));
        inv.setItem(12, makeItem(Material.STONE_SWORD, "§7Sumo Kit", "§7Knockback duel"));
        inv.setItem(14, makeItem(Material.BOW, "§cArcher Kit", "§7Bow fight"));
        inv.setItem(16, makeItem(Material.GOLDEN_APPLE, "§5No Debuff Kit", "§7Practice build"));

        return inv;
    }

    public Inventory createSettingsMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, "§8§lDuel Settings");

        inv.setItem(11, makeItem(Material.GOLD_BLOCK, "§6Ranked", "§7Counts toward ranking"));
        inv.setItem(13, makeItem(Material.STONE, "§8Unranked", "§7Practice only"));
        inv.setItem(20, makeItem(Material.REDSTONE, "§cBest of 3", "§7First to 2"));
        inv.setItem(24, makeItem(Material.REDSTONE_BLOCK, "§4Best of 5", "§7First to 3"));

        return inv;
    }

    public Inventory createPlayerMenu(List<Player> players, String gamemode) {
        Inventory inv = Bukkit.createInventory(null, 54, "§8§lChallenge Player");

        int slot = 0;
        for (Player p : players) {
            if (slot >= 54) break;
            UUID uuid = p.getUniqueId();
            RankProgress progress = rankingManager.getRankProgress(uuid, gamemode);
            String rankLabel = "[" + progress.getRank().getName() + progress.getLevel() + "]";
            String gamemodeColor = plugin.getConfig().getString("gamemodes." + gamemode + ".color", "§f");
            String gamemodeDisplay = plugin.getConfig().getString("gamemodes." + gamemode + ".display", gamemode);

            String displayName = rankLabel + " " + gamemodeColor + "[" + gamemodeDisplay + "] §f" + p.getName();
            inv.setItem(slot, makeItem(Material.PLAYER_HEAD, displayName, "§7Click to challenge"));
            slot++;
        }

        return inv;
    }

    private ItemStack makeItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(Arrays.asList(lore));
            item.setItemMeta(meta);
        }
        return item;
    }
}
