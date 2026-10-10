package com.yourname.practiceladder.Model;

import org.bukkit.entity.Player;

public class Match {

    private final Player player1;
    private final Player player2;
    private final String ladder;
    private final Arena arena;

    public Match(Player player1, Player player2, String ladder, Arena arena) {
        this.player1 = player1;
        this.player2 = player2;
        this.ladder = ladder;
        this.arena = arena;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getLadder() {
        return ladder;
    }

    public Arena getArena() {
        return arena;
    }
}