package net.azisaba.lgw.lgwmanager.match;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.api.RedisManager;
import net.azisaba.lgw.lgwmanager.api.RedisServerSettings;
import net.azisaba.lgw.lgwmanager.api.scoreboard.IMatchScoreBoard;
import net.azisaba.lgw.lgwmanager.match.data.KillStreakData;
import net.azisaba.lgw.lgwmanager.match.data.MapData;
import net.azisaba.lgw.lgwmanager.match.data.MatchData;
import net.azisaba.lgw.lgwmanager.match.gamemode.MapType;
import net.azisaba.lgw.lgwmanager.match.gamemode.equipment.IEquipment;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.IGameEnd;
import net.azisaba.lgw.lgwmanager.match.gamemode.GameModeEnum;
import net.azisaba.lgw.lgwmanager.match.gamemode.IGameMode;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.TimerEnd;
import net.azisaba.lgw.lgwmanager.match.gamemode.reward.IReward;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatchManager {

    public IGameMode gameMode;
    public List<IGameEnd> gameEnd;
    public List<IReward> reward;
    public IEquipment equipment;
    public IMatchScoreBoard matchScoreBoard;
    public List<MapType> mapType;
    public Map<Player, KillStreakData> killStreakDataMap = new HashMap<>();
    public Duration matchTime = null;
    public MatchData matchData;


    public String matchServer;


    public MatchManager(GameModeEnum gameModeEnum, IMatchScoreBoard scoreBoard){
        this.gameMode = gameModeEnum.gameMode;
        this.gameEnd = gameModeEnum.gameEnd;
        this.reward = gameModeEnum.reward;
        this.equipment = gameModeEnum.equipment;
        this.mapType = gameModeEnum.mapType;
        this.matchData = new MatchData(gameModeEnum);
        this.matchScoreBoard = scoreBoard;
    }

    public void startMapVote(){

    }

    public void startMatch(){
        gameMode.startMatch(this);
    }

    public void endMatch(){


        //一番最後の処理
        BukkitTask idleServer = new BukkitRunnable(){
            @Override
            public void run() {
                RedisServerSettings redisServerSettings = LGWManager.getServerSettings();
                redisServerSettings.setServerStatus(matchServer, true);
            }
        }.runTaskLater(LGWManager.INSTANCE, 10);
    }
}
