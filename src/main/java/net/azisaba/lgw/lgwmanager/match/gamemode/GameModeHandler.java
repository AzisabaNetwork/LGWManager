package net.azisaba.lgw.lgwmanager.match.gamemode;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.IGameEnd;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.TimerEnd;
import net.azisaba.lgw.lgwmanager.task.ReadyMatchBroadCastTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.*;

public interface GameModeHandler {

    default void prepareMatch(MatchManager matchManager) {
        // 試合前の装備配布・スポーン位置設定などを行う
        Map<BattleTeam, Location> spawnPoint = matchManager.matchData.mapData.spawnPoint;
        EnumMap<BattleTeam, Set<Player>> playerList = matchManager.matchData.playerList;

        for (BattleTeam team : BattleTeam.values()) {
            ItemStack teamChestPlate = matchManager.getEquipment().getTeamChestPlate(team);
            Location spawn = spawnPoint.get(team);
            for (Player player : playerList.get(team)) {
                player.teleport(spawn);
                player.getInventory().setChestplate(teamChestPlate);
                player.sendMessage("試合の準備が整いました。まもなく開始します！");
            }
        }
    }

    default void startMatch(MatchManager matchManager) {
        // 試合開始時のロジック
        matchManager.matchData.playing = true;
        matchManager.matchTime = Duration.ZERO;

        // スコアボードの初期化や通知などもここで実施可能
        for (Player player : matchManager.matchData.getPlayerList()) {
            player.sendMessage("★ 試合開始！頑張ってください！ ★");
        }

        // ゲーム終了条件の開始（例：タイマー）
        for (IGameEnd gameEnd : matchManager.getGameEnd()) {
            if (gameEnd instanceof TimerEnd) {
                ((TimerEnd) gameEnd).startTimer(matchManager);
            }
        }
    }

    default void endMatch(MatchManager matchManager) {
        matchManager.matchData.playing = false;

        // 試合終了時に全プレイヤーをロビーに戻すなどの処理
        for (Player player : matchManager.matchData.getPlayerList()) {
            player.sendMessage("試合終了です。ロビーに戻ります。");
            // ロビーへの移動処理があればここに記述
        }

        // Redisのステータスをidleに戻す
        new BukkitRunnable() {
            @Override
            public void run() {
                LGWManager.getServerSettings().setServerStatus(matchManager.matchServer, true);
            }
        }.runTaskLater(LGWManager.INSTANCE, 20L);
    }

    default void readyMatch(MatchManager matchManager) {
        if(matchManager.matchData.getPlayerList().size() >= 2) {
            startMatch(matchManager);
        }
        matchManager.readyMatchBroadCast = new ReadyMatchBroadCastTask(matchManager).runTaskTimerAsynchronously(LGWManager.INSTANCE, 200, 200);
    }

    default void addPlayerInMatch(MatchManager matchManager, Player player) {
        matchManager.getMatchData().
    }



}