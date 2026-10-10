# PracticeLadder Plugin Structure

## Directory Overview

```
PracticeLadder/
├── pom.xml                                    # Maven build configuration
├── README.md                                  # Documentation
├── .gitignore                                 # Git ignore rules
└── src/
    └── main/
        ├── java/com/yourname/practiceladder/
        │   ├── PracticeLadder.java           # Main plugin class
        │   ├── Commands/                      # Command handlers
        │   │   ├── PracticeCommand.java
        │   │   ├── SpawnCommand.java
        │   ���   ├── QueueCommand.java
        │   │   ├── DuelCommand.java
        │   │   ├── AcceptCommand.java
        │   │   ├── DeclineCommand.java
        │   │   ├── StatsCommand.java
        │   │   ├── RankCommand.java
        │   │   └── BotCommand.java
        │   ├── Managers/                      # Core logic
        │   │   ├── DataManager.java          # Player data persistence
        │   │   ├── RankingManager.java       # Rank/ELO progression
        │   │   ├── QueueManager.java         # Queue and matchmaking
        │   │   ├── MatchManager.java         # Match lifecycle
        │   │   ├── ArenaManager.java         # Arena management
        │   │   ├── KitManager.java           # Kit selection/giving
        │   │   ├── MenuManager.java          # GUI menu creation
        │   │   ├── DuelManager.java          # Duel requests
        │   │   └── Arena.java                # Arena data model
        │   ├── Model/                         # Data models
        │   │   ├── Rank.java                 # Enum: LT, MT, HT
        │   │   ├── RankProgress.java         # Rank + level + progression
        │   │   ���── PlayerStats.java          # Player win/loss/rank data
        │   ├── Listeners/                     # Event handlers
        │   │   ├── MenuClickListener.java    # GUI interaction
        │   │   ├── PlayerJoinListener.java   # Join message
        │   │   ├── PlayerQuitListener.java   # Queue cleanup on quit
        │   └── config/
        └── resources/
            ├── plugin.yml                     # Plugin manifest
            └── config.yml                     # Server configuration
```

## Core Components

### Managers

**DataManager**: Loads/saves player YAML files with rank and stats

**RankingManager**: Calculates rank progression
- Win: +25 progress
- Loss: -20 progress
- Rank up at 100, rank down at 0

**QueueManager**: Manages queues per ladder, auto-matches players

**MatchManager**: Starts/ends matches, handles arena assignment

**MenuManager**: Creates GUIs for /practice menu

**DuelManager**: Handles duel requests and acceptance

### Commands

All commands have descriptions in `plugin.yml` and are registered in `PracticeLadder.java`

### Listeners

Listen for:
- Menu clicks (`InventoryClickEvent`)
- Player joins/quits
- (Optional: deaths for auto-ending matches)

## How It Works

1. Player uses `/practice` → opens main menu
2. Clicks "Queue" → ladder selection menu
3. Selects ladder → joins queue
4. Two players in queue → auto-match and start match
5. Match ends (death listener) → winner gets +25 progress, loser gets -20
6. Rank updates if thresholds crossed
7. Player can check `/stats` to see current rank

## File Structure

- **Player data**: `plugins/PracticeLadder/data/{uuid}.yml`
- **Config**: `plugins/PracticeLadder/config.yml`

## Building

```bash
mvn clean package
```

Output: `target/PracticeLadder-1.0.0.jar`

## Future Enhancements

- Real bot NPCs (Citizens plugin integration)
- Spectator mode
- Best-of-3 / Best-of-5 series
- Leaderboard command
- Ranked/unranked toggle storage
- Custom arena config loading
- Anti-cheat integration
- Seasonal rank resets
- Custom rewards/badges
