package net.azisaba.lgw.lgwmanager.match.gamemode.gameend;

public class KillPointEnd implements IGameEnd{

     //試合を終了させるためのキル数
    public int endOfKillPoint;

    public KillPointEnd(int endOfKillPoint){
        this.endOfKillPoint = endOfKillPoint;
    }
}
