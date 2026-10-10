package com.yourname.practiceladder.Managers;

import com.yourname.practiceladder.Model.RankProgress;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DisplayManager {

    private final PracticeLadder plugin;
    private final RankingManager rankingManager;

    public DisplayManager(PracticeLadder plugin, RankingManager rankingManager) {
        this.plugin = plugin;
        this.rankingManager = rankingManager;
    }

    public String getGamemodeColor(String gamemode) {
        return plugin.getConfig().getString("gamemodes." + gamemode.toLowerCase() + ".color", "§f");
    }

    public String getGamemodeDisplay(String gamemode) {
        return plugin.getConfig().getString("gamemodes." + gamemode.toLowerCase() + ".display", gamemode);
    }

    public String getRankLabel(UUID uuid, String gamemode) {
        RankProgress progress = rankingManager.getRankProgress(uuid, gamemode);
        String rank = progress.getRank().getLabel() + progress.getLevel();
        return "[" + rank + "]";
    }

    public String getGamemodeTag(String gamemode) {
        String color = getGamemodeColor(gamemode);
        String display = getGamemodeDisplay(gamemode);
        return color + "[" + display + "]";
    }

    public String getPlayerDisplayName(Player player, String gamemode) {
        UUID uuid = player.getUniqueId();
        String rankLabel = getRankLabel(uuid, gamemode);
        String gamemodeTag = getGamemodeTag(gamemode);
        String playerName = "§f" + player.getName();
        
        return rankLabel + " " + gamemodeTag + " " + playerName;
    }

    public String getPlayerDisplayNameSimple(Player player, String gamemode) {
        UUID uuid = player.getUniqueId();
        String rankLabel = getRankLabel(uuid, gamemode);
        String playerName = "§f" + player.getName();
        
        return rankLabel + " " + playerName;
    }
}
