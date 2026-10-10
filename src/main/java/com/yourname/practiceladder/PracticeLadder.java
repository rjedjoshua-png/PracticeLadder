package com.yourname.practiceladder;

import com.yourname.practiceladder.Commands.*;
import com.yourname.practiceladder.Listeners.*;
import com.yourname.practiceladder.Managers.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class PracticeLadder extends JavaPlugin {

    private static PracticeLadder instance;

    private QueueManager queueManager;
    private MatchManager matchManager;
    private ArenaManager arenaManager;
    private KitManager kitManager;
    private RankingManager rankingManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.dataManager = new DataManager(this);
        this.rankingManager = new RankingManager(this, dataManager);
        this.kitManager = new KitManager(this);
        this.arenaManager = new ArenaManager(this);
        this.matchManager = new MatchManager(this, arenaManager, rankingManager, dataManager);
        this.queueManager = new QueueManager(this, arenaManager, matchManager);

        registerCommands();
        registerListeners();

        getLogger().info("PracticeLadder enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PracticeLadder disabled!");
    }

    private void registerCommands() {
        getCommand("practice").setExecutor(new PracticeCommand(this));
        getCommand("queue").setExecutor(new QueueCommand(this, queueManager));
        getCommand("duel").setExecutor(new DuelCommand(this, matchManager));
        getCommand("accept").setExecutor(new AcceptCommand(this, matchManager));
        getCommand("decline").setExecutor(new DeclineCommand(this, matchManager));
        getCommand("stats").setExecutor(new StatsCommand(this, dataManager, rankingManager));
        getCommand("rank").setExecutor(new RankCommand(this, rankingManager));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this, queueManager, matchManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this, matchManager), this);
        getServer().getPluginManager().registerEvents(new DamageListener(this, matchManager), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
    }

    public static PracticeLadder getInstance() {
        return instance;
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }

    public MatchManager getMatchManager() {
        return matchManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public RankingManager getRankingManager() {
        return rankingManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}