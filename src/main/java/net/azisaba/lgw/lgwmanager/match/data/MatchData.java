package net.azisaba.lgw.lgwmanager.match.data;

import net.azisaba.lgw.lgwmanager.match.gamemode.GameModeEnum;

import java.time.Duration;

public class MatchData {
    public GameModeEnum gameMode;
    //マッチが進行中か
    public boolean playing = false;

    public MatchData(GameModeEnum gameMode){
        this.gameMode = gameMode;
    }

}
