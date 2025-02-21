package net.azisaba.lgw.lgwmanager.match.gamemode.gameend;

import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.azisaba.lgw.lgwmanager.match.data.MatchData;

public interface IGameEnd {
    //Todo:マッチの終了処理を書く
    default void endMatch(MatchManager matchManager){
    }
    default void startTimer(MatchManager matchManager){
    }

}
