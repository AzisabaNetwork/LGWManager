package net.azisaba.lgw.lgwmanager.match.gamemode.gameend;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.azisaba.lgw.lgwmanager.match.event.MatchTimeEndEvent;
import net.azisaba.lgw.lgwmanager.task.CountDownTask;
import org.bukkit.Bukkit;

import java.time.Duration;

public class TimerEnd implements IGameEnd {
    public CountDownTask timer;
    public Duration endTime;

    public TimerEnd(Duration endTime) {
        this.endTime = endTime;
    }

    public void startTimer(MatchManager matchManager){
        matchManager.matchTime = endTime;
        this.timer = (CountDownTask) new CountDownTask(
                this.endTime,
                () -> {
                    endMatch(matchManager);
                    MatchTimeEndEvent event = new MatchTimeEndEvent(matchManager);
                    Bukkit.getPluginManager().callEvent(event);
                },
                matchManager
        ).runTask(LGWManager.INSTANCE);
    }
}
