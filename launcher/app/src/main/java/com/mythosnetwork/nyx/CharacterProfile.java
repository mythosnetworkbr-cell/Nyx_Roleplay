package com.mythosnetwork.nyx;

/** Client-side model for a character scoped to exactly one Nyx city. */
public final class CharacterProfile {
    public final String cityId;
    public String name;
    public String job;
    public long money;
    public String inventory;

    public CharacterProfile(String cityId) {
        this.cityId = cityId;
        this.name = "Novo personagem";
        this.job = "Desempregado";
        this.money = 0L;
        this.inventory = "";
    }
}
