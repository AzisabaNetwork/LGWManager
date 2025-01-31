package net.azisaba.lgw.lgwmanager.Data;

import net.azisaba.lgw.lgwmanager.GameMode.GameModeEnum;

public class MatchData {
    public GameModeEnum gameMode;
    public MatchData(GameModeEnum gameMode){
        this.gameMode = gameMode;
    }
}
