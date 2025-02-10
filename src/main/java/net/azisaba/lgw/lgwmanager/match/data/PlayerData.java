package net.azisaba.lgw.lgwmanager.match.data;

import lombok.Getter;

public class PlayerData {
    public int kill;
    public int assist;
    public boolean inMatch;
    @Getter
    private int nowKS;
    @Getter
    private int nowKSPoint;
    public PlayerData() {
        this.assist = 0;
        this.kill = 0;
        this.inMatch = false;
        this.nowKS = 0;
        this.nowKSPoint = 0;
    }

    public int addNowKS() {
        nowKS++;
        return nowKS;
    }

    public int resetNowKS() {
        nowKS = 0;
        return nowKS;
    }

    public int addNowKSPoint(int setNumber) {
        nowKSPoint += setNumber;
        return nowKSPoint;
    }

    public int resetNowKSPoint() {
        nowKSPoint = 0;
        return nowKSPoint;
    }
}
