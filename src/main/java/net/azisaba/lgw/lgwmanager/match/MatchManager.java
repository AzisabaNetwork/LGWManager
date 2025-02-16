package net.azisaba.lgw.lgwmanager.match;

import net.azisaba.lgw.lgwmanager.match.data.KillStreakData;
import net.azisaba.lgw.lgwmanager.match.gamemode.equipment.IEquipment;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.IGameEnd;
import net.azisaba.lgw.lgwmanager.match.gamemode.GameModeEnum;
import net.azisaba.lgw.lgwmanager.match.gamemode.IGameMode;
import net.azisaba.lgw.lgwmanager.match.gamemode.reward.IReward;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatchManager {

    public IGameMode gameMode;
    public List<IGameEnd> gameEnd;
    public List<IReward> reward;
    public IEquipment equipment;
    //public List<MapType> mapType;
    public Map<Player, KillStreakData> killStreakDataMap = new HashMap<>();

    public MatchManager(GameModeEnum gameModeEnum){
        this.gameMode = gameModeEnum.gameMode;
        this.gameEnd = gameModeEnum.gameEnd;
        this.reward = gameModeEnum.reward;
        this.equipment = gameModeEnum.equipment;
        //this.mapType = gameModeEnum.mapType;
    }

    public void startMapVote(){

    }
    public void startMatch(){

        //Bukkit.getPluginManager()
        //        .callEvent(new MatchStartedEvent(, ));
    }
}
