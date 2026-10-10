package com.yourname.practiceladder.Model;

public enum Rank {
    LT("LT", 1),
    MT("MT", 2),
    HT("HT", 3);

    private final String name;
    private final int tier;

    Rank(String name, int tier) {
        this.name = name;
        this.tier = tier;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }
}