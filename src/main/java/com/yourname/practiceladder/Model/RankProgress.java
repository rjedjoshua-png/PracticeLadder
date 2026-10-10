package com.yourname.practiceladder.Model;

public class RankProgress {

    private Rank rank; // LT, MT, or HT
    private int level; // 1-5 within the rank
    private int progression; // 0-100

    public RankProgress(Rank rank, int level, int progression) {
        this.rank = rank;
        this.level = level;
        this.progression = progression;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level > 5) level = 5;
        if (level < 1) level = 1;
        this.level = level;
    }

    public int getProgression() {
        return progression;
    }

    public void setProgression(int progression) {
        if (progression > 100) progression = 100;
        if (progression < 0) progression = 0;
        this.progression = progression;
    }

    public String getDisplayName() {
        return rank.getName() + " " + level + " [" + getProgressionBar() + "] " + progression + "%";
    }

    public String getProgressionBar() {
        int filled = progression / 10;
        int empty = 10 - filled;
        return "§a" + "█".repeat(filled) + "§7" + "█".repeat(empty);
    }
}