package com.yourname.practiceladder.Model;

import org.bukkit.configuration.file.FileConfiguration;

public class PlayerStats {

    private final FileConfiguration config;

    public PlayerStats(FileConfiguration config) {
        this.config = config;
    }

    public int getWins() {
        return config.getInt("wins");
    }

    public int getLosses() {
        return config.getInt("losses");
    }

    public void setWins(int wins) {
        config.set("wins", wins);
    }

    public void setLosses(int losses) {
        config.set("losses", losses);
    }

    public void addWin() {
        setWins(getWins() + 1);
    }

    public void addLoss() {
        setLosses(getLosses() + 1);
    }

    public RankProgress getRankProgress(String ladder) {
        String path = "rank." + ladder;
        String rankStr = config.getString(path + ".rank", "LT");
        int level = config.getInt(path + ".level", 1);
        int progression = config.getInt(path + ".progression", 0);

        Rank rank = Rank.valueOf(rankStr);
        return new RankProgress(rank, level, progression);
    }

    public void setRankProgress(String ladder, RankProgress progress) {
        String path = "rank." + ladder;
        config.set(path + ".rank", progress.getRank().getName());
        config.set(path + ".level", progress.getLevel());
        config.set(path + ".progression", progress.getProgression());
    }
}