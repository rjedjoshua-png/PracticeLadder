package com.yourname.practiceladder.Managers;

import com.yourname.practiceladder.Model.Arena;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private final List<Arena> arenas = new ArrayList<>();

    public ArenaManager(PracticeLadder plugin) {
        arenas.add(new Arena("arena1",
                new Location(Bukkit.getWorld("world"), 0, 70, 0),
                new Location(Bukkit.getWorld("world"), 2, 70, 0)));

        arenas.add(new Arena("arena2",
                new Location(Bukkit.getWorld("world"), 10, 70, 0),
                new Location(Bukkit.getWorld("world"), 12, 70, 0)));
    }

    public Arena getAvailableArena() {
        for (Arena arena : arenas) {
            if (!arena.isInUse()) {
                return arena;
            }
        }
        return null;
    }
}