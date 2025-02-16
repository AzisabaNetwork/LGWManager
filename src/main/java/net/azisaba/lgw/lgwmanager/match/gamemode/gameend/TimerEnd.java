package net.azisaba.lgw.lgwmanager.match.gamemode.gameend;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.match.data.MatchData;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class TimerEnd implements IGameEnd{
    public Duration endTime;

    public TimerEnd(Duration endTime){
        this.endTime = endTime;
    }

    public void startTimer(MatchData matchData){
        long ticks = endTime.getSeconds() * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                endMatch(matchData);
            }
        }.runTaskLater(LGWManager.INSTANCE, ticks);
    }

}
