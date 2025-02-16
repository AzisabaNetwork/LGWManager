package net.azisaba.lgw.lgwmanager.match.data;

import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import net.azisaba.lgw.lgwmanager.match.gamemode.MapType;
import net.azisaba.lgw.lgwmanager.util.Pair;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

import java.util.*;

public class MapData {

    String mapName;
    Component mapDisplayName;
    //各mapが対応しているゲームモード
    List<MapType> availableMapType;
    Map<BattleTeam, Location> spawnPoint = new HashMap<>();
    //コンクエストなどの占領6地点の固有の地点の対角線上にある点
    Map<String,Pair<Location, Location>> uniquePointSquare;

    public MapData(String mapName, Component mapDisplayName, List<MapType> availableMapType, Map<BattleTeam, Location> spawnPoint){
        setupMapData(mapName, mapDisplayName, availableMapType, spawnPoint);
    }
    public MapData(String mapName, Component mapDisplayName, List<MapType> availableMapType, Map<BattleTeam, Location> spawnPoint, Map<String, Pair<Location, Location>> uniquePointSquare){
        setupMapData(mapName, mapDisplayName, availableMapType, spawnPoint);
        this.uniquePointSquare = uniquePointSquare;
    }

    private void setupMapData(String mapName, Component mapDisplayName, List<MapType> availableMapType, Map<BattleTeam, Location> spawnPoint){
        this.mapName = mapName;
        this.mapDisplayName = mapDisplayName;
        this.availableMapType = availableMapType;
        this.spawnPoint = spawnPoint;
    }


}
