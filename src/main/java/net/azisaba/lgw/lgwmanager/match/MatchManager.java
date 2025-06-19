package net.azisaba.lgw.lgwmanager.match;

import lombok.Getter;
import net.azisaba.lgw.lgwmanager.api.scoreboard.IMatchScoreBoard;
import net.azisaba.lgw.lgwmanager.match.data.KillStreakData;
import net.azisaba.lgw.lgwmanager.match.data.MatchData;
import net.azisaba.lgw.lgwmanager.match.gamemode.*;
import net.azisaba.lgw.lgwmanager.match.gamemode.equipment.IEquipment;
import net.azisaba.lgw.lgwmanager.match.gamemode.gamemodehandler.GameModeHandler;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.IGameEnd;
import net.azisaba.lgw.lgwmanager.match.gamemode.reward.IReward;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatchManager {
    public final GameModeEnum gameModeEnum;
    public final GameModeHandler gameModeHandler;
    public final IMatchScoreBoard matchScoreBoard;
    @Getter
    public final MatchData matchData;
    public Duration matchTime = null;
    public final Map<Player, KillStreakData> killStreakDataMap = new HashMap<>();
    public String matchServer;
    public BukkitTask readyMatchBroadCast;

    public MatchManager(GameModeEnum gameModeEnum, IMatchScoreBoard scoreBoard) {
        this.gameModeEnum = gameModeEnum;
        this.gameModeHandler = gameModeEnum.handler;
        this.matchScoreBoard = scoreBoard;
        this.matchData = new MatchData(gameModeEnum, this);
    }

    public void startMapVote() {
        // 未実装：マップ投票処理
    }

    public void prepareMatch() {
        gameModeHandler.prepareMatch(this);
    }

    public void startMatch() {
        gameModeHandler.startMatch(this);
    }

    public void endMatch() {
        gameModeHandler.endMatch(this);
    }

    public List<IGameEnd> getGameEnd() {
        return gameModeEnum.gameEnd;
    }

    public List<IReward> getRewards() {
        return gameModeEnum.reward;
    }

    public IEquipment getEquipment() {
        return gameModeEnum.equipment;
    }

    public List<MapType> getMapTypes() {
        return gameModeEnum.mapType;
    }
    public void addPlayerInMatch(Player player) {
        gameModeHandler.addPlayerInMatch(this, player);
    }
}