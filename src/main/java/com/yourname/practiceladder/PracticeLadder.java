package com.yourname.practiceladder;

import com.yourname.practiceladder.Commands.*;
import com.yourname.practiceladder.Listeners.*;
import com.yourname.practiceladder.Managers.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class PracticeLadder extends JavaPlugin {

    private static PracticeLadder instance;

    private DataManager dataManager;
    private RankingManager rankingManager;
    private QueueManager queueManager;
    private MatchManager matchManager;
    private ArenaManager arenaManager;
    private KitManager kitManager;
    private MenuManager menuManager;
    private DisplayManager displayManager;
    private DuelManager duelManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.dataManager = new DataManager(this);
        this.rankingManager = new RankingManager(this, dataManager);
        this.arenaManager = new ArenaManager(this);
        this.kitManager = new KitManager(this);
        this.matchManager = new MatchManager(this, arenaManager, rankingManager, dataManager);
        this.queueManager = new QueueManager(this, arenaManager, matchManager);
        this.menuManager = new MenuManager(this, rankingManager);
        this.displayManager = new DisplayManager(this, rankingManager);
        this.duelManager = new DuelManager(this);

        registerCommands();
        registerListeners();

        getLogger().info("PracticeLadder enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("PracticeLadder disabled.");
    }

    private void registerCommands() {
        getCommand("practice").setExecutor(new PracticeCommand(this, menuManager));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("queue").setExecutor(new QueueCommand(this, queueManager));
        getCommand("duel").setExecutor(new DuelCommand(this, duelManager));
        getCommand("accept").setExecutor(new AcceptCommand(this, duelManager));
        getCommand("decline").setExecutor(new DeclineCommand(this, duelManager));
        getCommand("stats").setExecutor(new StatsCommand(this, dataManager, rankingManager, displayManager));
        getCommand("rank").setExecutor(new RankCommand(this, rankingManager));
        getCommand("bot").setExecutor(new BotCommand(this, matchManager));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new MenuClickListener(this, queueManager, duelManager, menuManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this, queueManager), this);
    }

    public static PracticeLadder getInstance() {
        return instance;
    }

    public Location getSpawnLocation() {
        World world = Bukkit.getWorld(getConfig().getString("spawn.world", "world"));
        if (world == null) return Bukkit.getWorlds().get(0).getSpawnLocation();

        return new Location(
                world,
                getConfig().getDouble("spawn.x", 0.5),
                getConfig().getDouble("spawn.y", 64.0),
                getConfig().getDouble("spawn.z", 0.5),
                (float) getConfig().getDouble("spawn.yaw", 0.0),
                (float) getConfig().getDouble("spawn.pitch", 0.0)
        );
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public RankingManager getRankingManager() {
        return rankingManager;
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

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public DuelManager getDuelManager() {
        return duelManager;
    }
}
