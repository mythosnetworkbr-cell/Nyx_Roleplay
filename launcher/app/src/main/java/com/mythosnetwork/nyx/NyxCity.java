package com.mythosnetwork.nyx;

public final class NyxCity {
    public final String id;
    public final String name;
    public final String host;
    public final int port;
    public final int characterSlot;

    public NyxCity(String id, String name, String host, int port, int characterSlot) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
        this.characterSlot = characterSlot;
    }

    public static final NyxCity CITY_01 = new NyxCity("city01", "Cidade 01", "ip.oscrias.com.br", 7777, 1);
    public static final NyxCity CITY_02 = new NyxCity("city02", "Cidade 02", "51.254.21.27", 7777, 2);
}
