package net.azisaba.lgw.lgwmanager.match.dataIO;

import net.azisaba.lgw.lgwmanager.match.data.MapData;
import net.azisaba.lgw.lgwmanager.match.gamemode.MapType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MapLoader {
    public static EnumMap<MapType, List<MapData>> getMaplist() {
        return new EnumMap<>(MapType.class);
    }
}
