package net.azisaba.lgw.lgwmanager.match.gamemode;

import net.azisaba.lgw.lgwmanager.match.equipment.DefaultEquipment;
import net.azisaba.lgw.lgwmanager.match.equipment.IEquipment;
import net.azisaba.lgw.lgwmanager.match.gameend.IGameEnd;
import net.azisaba.lgw.lgwmanager.match.gameend.TimerEnd;
import net.azisaba.lgw.lgwmanager.match.MapType;
import net.azisaba.lgw.lgwmanager.match.reward.IReward;
import net.azisaba.lgw.lgwmanager.match.reward.KillReward;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public enum GameModeEnum {
    TEAM_DEATH_MATCH("tdm",
            new TeamDeathMatch(),
            Arrays.asList(new TimerEnd(Duration.ofMinutes(10))),
            Arrays.asList(new KillReward()),
            new DefaultEquipment(),
            Arrays.asList(MapType.TDM));

    public final String alias;
    public final IGameMode gameMode;
    public final List<IGameEnd> gameEnd;
    public final List<IReward> reward;
    public final IEquipment equipment;
    //public final List<MapType> mapType;

    GameModeEnum(String alias, IGameMode gameMode, List<IGameEnd> gameEnd, List<IReward> reward, IEquipment equipment, List<MapType> mapType){
        this.alias = alias;
        this.gameMode = gameMode;
        this.gameEnd = gameEnd;
        this.reward = reward;
        this.equipment = equipment;
        //this.mapType = mapType;
    }

    public static GameModeEnum getFromString(String text){
        String suggest = text.replace(" ", "").toLowerCase();
        for(GameModeEnum mode : GameModeEnum.values()){
            if(mode.alias.equals(suggest)){
                return mode;
            }
        }
        return null;
    }
}
