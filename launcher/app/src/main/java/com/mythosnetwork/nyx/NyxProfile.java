package com.mythosnetwork.nyx;

public final class NyxProfile {
    private final NyxCity city;
    private String characterName = "";

    public NyxProfile(NyxCity city) {
        this.city = city;
    }

    public NyxCity getCity() { return city; }
    public String getCharacterName() { return characterName; }
    public void setCharacterName(String name) { characterName = name == null ? "" : name.trim(); }
}
