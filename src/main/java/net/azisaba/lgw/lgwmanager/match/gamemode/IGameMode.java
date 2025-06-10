package net.azisaba.lgw.lgwmanager.match.gamemode;

import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.azisaba.lgw.lgwmanager.match.gamemode.equipment.DefaultEquipment;
import net.azisaba.lgw.lgwmanager.match.gamemode.equipment.IEquipment;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.IGameEnd;
import net.azisaba.lgw.lgwmanager.match.gamemode.reward.IReward;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface IGameMode {
    /**
     * 試合を開始する処理。マップ移動や装備配布などを行う。
     * デフォルトでは何もしません。必要に応じてオーバーライドしてください。
     */
    default void startMatch(MatchManager matchManager) {
        // デフォルトの試合開始処理（空）
    }

    /**
     * 試合終了後の後処理などがあれば記述。必要がなければ空でもよい。
     */
    default void endMatch(MatchManager matchManager) {
        // デフォルトの試合終了処理（空）
    }

    /**
     * 試合中に1tickごとに呼ばれる処理。スコアボード更新などに利用。
     */
    default void onTick(MatchManager matchManager) {
        // デフォルトのtick処理（空）
    }

    /**
     * 試合の終了条件（時間制、キル数制など）を登録するためのメソッド。
     */
    default List<IGameEnd> getGameEndConditions() {
        return Collections.emptyList();
    }

    /**
     * 試合勝利時などに報酬処理を行うためのメソッド。
     */
    default List<IReward> getRewards() {
        return Collections.emptyList();
    }

    /**
     * チーム装備やアイテムなどを提供するメソッド。
     */
    default IEquipment getEquipment() {
        return new DefaultEquipment();
    }

    /**
     * このモードで使用できるマップの種類
     */
    default List<MapType> getAllowedMapTypes() {
        return List.of(MapType.TDM);
    }
}
