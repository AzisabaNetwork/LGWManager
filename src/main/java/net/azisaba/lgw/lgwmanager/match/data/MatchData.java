package net.azisaba.lgw.lgwmanager.match.data;

import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import net.azisaba.lgw.lgwmanager.match.gamemode.GameModeEnum;
import org.bukkit.entity.Player;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class MatchData {
    public GameModeEnum gameMode;
    //マッチが進行中か
    public boolean playing = false;
    public EnumMap<BattleTeam, Integer> TeamPoint = new EnumMap<>(BattleTeam.class);
    public EnumMap<BattleTeam, Set<Player>> playerList = new EnumMap<>(BattleTeam.class);
    public MapData mapData;

    public MatchData(GameModeEnum gameMode){
        this.gameMode = gameMode;
        this.playerList.put(BattleTeam.RED, new HashSet<>());
        this.playerList.put(BattleTeam.BLUE, new HashSet<>());
    }

}
