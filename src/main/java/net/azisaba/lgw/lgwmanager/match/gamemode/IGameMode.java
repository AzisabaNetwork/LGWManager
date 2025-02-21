package net.azisaba.lgw.lgwmanager.match.gamemode;

import net.azisaba.lgw.lgwmanager.match.MatchManager;

public interface IGameMode {
    //Todo:マッチの開始処理を記載する
    default void startMatch(MatchManager matchManager){
    }
    default void playerKill(MatchManager matchManager){
    }
}
