package com.yourname.practiceladder.Managers;

import com.yourname.practiceladder.Model.Rank;
import com.yourname.practiceladder.Model.RankProgress;
import com.yourname.practiceladder.PracticeLadder;

import java.util.UUID;

public class RankingManager {

    private final PracticeLadder plugin;
    private final DataManager dataManager;

    private static final int MAX_PROGRESSION = 100;
    private static final int PROGRESSION_PER_WIN = 20;
    private static final int PROGRESSION_PER_LOSS = 10;

    public RankingManager(PracticeLadder plugin, DataManager dataManager) {
        this.plugin = plugin;
        this.dataManager = dataManager;
    }

    public RankProgress getRankProgress(UUID uuid, String ladder) {
        return dataManager.getPlayerStats(uuid).getRankProgress(ladder);
    }

    public void addWinProgression(UUID uuid, String ladder) {
        RankProgress progress = getRankProgress(uuid, ladder);
        progress.setProgression(progress.getProgression() + PROGRESSION_PER_WIN);

        // Check if rank up
        if (progress.getProgression() >= MAX_PROGRESSION) {
            rankUp(progress);
            progress.setProgression(progress.getProgression() - MAX_PROGRESSION);
        }

        saveProgress(uuid, ladder, progress);
    }

    public void addLossProgression(UUID uuid, String ladder) {
        RankProgress progress = getRankProgress(uuid, ladder);
        progress.setProgression(progress.getProgression() - PROGRESSION_PER_LOSS);

        // Check if rank down
        if (progress.getProgression() < 0) {
            rankDown(progress);
            progress.setProgression(MAX_PROGRESSION + progress.getProgression()); // Add negative value
        }

        saveProgress(uuid, ladder, progress);
    }

    private void rankUp(RankProgress progress) {
        int currentLevel = progress.getLevel();

        if (currentLevel < 5) {
            progress.setLevel(currentLevel + 1);
        } else {
            // Advance to next rank
            Rank currentRank = progress.getRank();
            if (currentRank == Rank.LT) {
                progress.setRank(Rank.MT);
                progress.setLevel(1);
            } else if (currentRank == Rank.MT) {
                progress.setRank(Rank.HT);
                progress.setLevel(1);
            }
            // HT is max, stays at HT 5
        }
    }

    private void rankDown(RankProgress progress) {
        int currentLevel = progress.getLevel();

        if (currentLevel > 1) {
            progress.setLevel(currentLevel - 1);
        } else {
            // Demote to previous rank
            Rank currentRank = progress.getRank();
            if (currentRank == Rank.MT) {
                progress.setRank(Rank.LT);
                progress.setLevel(5);
            } else if (currentRank == Rank.HT) {
                progress.setRank(Rank.MT);
                progress.setLevel(5);
            }
            // LT is min, stays at LT 1
        }
    }

    private void saveProgress(UUID uuid, String ladder, RankProgress progress) {
        var stats = dataManager.getPlayerStats(uuid);
        stats.setRankProgress(ladder, progress);
        dataManager.savePlayerStats(uuid, stats);
    }
}