package net.azisaba.lgw.lgwmanager.task;

import com.google.common.base.Strings;
import lombok.Getter;
import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.match.MapType;
import net.azisaba.lgw.lgwmanager.match.gamemode.GameModeEnum;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MapSelectCountDown extends BukkitRunnable {
    /*
    @Getter
    private final List<MapType> mapTypes;
    @Getter
    private final GameModeEnum gameModeEnum;

    private final HashMap<Integer, AtomicInteger> votes = new HashMap<>();

    @Getter
    private final AtomicInteger timeLeft = new AtomicInteger(10);
    public MapSelectCountDown(List<MapType> mapTypes, GameModeEnum gameModeEnum) {
        this.mapTypes = mapTypes;
        this.gameModeEnum = gameModeEnum;
    }

    */
    @Override
    public void run() {

    }
        /*
        // 0の場合カウントダウン終了処理
        if ( timeLeft.get() <= 0 ) {
            // カウントダウンを停止
            LGWManager.INSTANCE.getMapSelectCountdown().stopCountdown();

            //票数に応じてランダムにマップを決定
            List<GameMap> random = new ArrayList<>();

            for (Integer i : votes.keySet()) {
                for (int count = 0; count < getVoteFor(i); count++) {
                    random.add(maps.get(i));
                }
            }

            if(random.isEmpty()){
                random.addAll(maps);
            }

            Collections.shuffle(random);

            GameMap m = random.get(0);

            LeonGunWar.getPlugin().getManager().setCurrentGameMap(m);
            BroadcastUtils.broadcast(
                    Chat.f("{0}&7Mapが &e{1} &7に決定！", LeonGunWar.GAME_PREFIX, m.getMapName()));


            // 試合開始のカウントダウンのトリガー
            LeonGunWar.getPlugin().getManager().setMatchMode(mode);

            // 5秒後にスコアボードを消す
            Bukkit.getScheduler().runTaskLater(LeonGunWar.getPlugin(), () -> {
                LeonGunWar.getPlugin().getScoreboardDisplayer().clearSideBar();
            }, 20L * 5);

            // 表示内容を取得
            String distributorName = LeonGunWar.getPlugin().getManager().getTeamDistributor()
                    .getDistributorName();
            String mapName = Optional.ofNullable(
                            LeonGunWar.getPlugin().getManager().getCurrentGameMap())
                    .map(GameMap::getMapName).orElse("ランダム");

            // メッセージを表示
            BroadcastUtils.broadcast(
                    Chat.f("{0}&7{1}", LeonGunWar.GAME_PREFIX, Strings.repeat("=", 40)));
            BroadcastUtils.broadcast(
                    Chat.f("{0}&7モード   {1}", LeonGunWar.GAME_PREFIX, mode.getModeName()));
            BroadcastUtils.broadcast(
                    Chat.f("{0}&7振り分け  {1}", LeonGunWar.GAME_PREFIX, distributorName));
            BroadcastUtils.broadcast(Chat.f("{0}&7マップ  {1}", LeonGunWar.GAME_PREFIX, mapName));
            BroadcastUtils.broadcast(Chat.f("{0}&7まもなく試合を開始します", LeonGunWar.GAME_PREFIX));
            BroadcastUtils.broadcast(
                    Chat.f("{0}&7{1}", LeonGunWar.GAME_PREFIX, Strings.repeat("=", 40)));

            // 音を鳴らす
            BroadcastUtils.getOnlinePlayers().forEach(
                    player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1));

            // 全プレイヤーにQuickメッセージを送信
            //LeonGunWar.getQuickBar().send(BroadcastUtils.getOnlinePlayers().toArray(new Player[0]));
            return;
        }

        // chatがtrueの場合表示
        if ( timeLeft.get() <= 5 ) {
            String msg = Chat.f("{0}&7Map決定まで残り &c{1}&7", LeonGunWar.GAME_PREFIX, SecondOfDay.f(timeLeft.get()));
            BroadcastUtils.broadcast(msg);
        }

        // 1秒減らす
        timeLeft.decrementAndGet();
        // スコアボードを更新
        LeonGunWar.getPlugin().getScoreboardDisplayer().updateScoreboard();
    }

    public int getVoteFor(int index) {
        return votes.getOrDefault(index, new AtomicInteger(0)).get();
    }

    public void incrementVoteFor(int index) {
        if ( votes.containsKey(index) ) {
            votes.get(index).incrementAndGet();
        } else {
            votes.put(index, new AtomicInteger(1));
        }
    }

    public void decrementVoteFor(int index) {
        if ( votes.containsKey(index) ) {
            votes.get(index).decrementAndGet();
        }
    }
     */
}
