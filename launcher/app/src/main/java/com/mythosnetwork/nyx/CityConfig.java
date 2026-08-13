package com.mythosnetwork.nyx;

/** Fixed city definitions. Each city owns an independent character/progression namespace. */
public final class CityConfig {
    public final String id;
    public final String name;
    public final String host;
    public final int port;

    public CityConfig(String id, String name, String host, int port) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public static CityConfig[] all() {
        return new CityConfig[] {
            new CityConfig("city_01", "Cidade 01", "ip.oscrias.com.br", 7777),
            new CityConfig("city_02", "Cidade 02", "51.254.21.27", 7777)
        };
    }
}
