package net.azisaba.lgw.lgwmanager.match.gameend;

import net.azisaba.lgw.lgwmanager.LGWManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class TimerEnd implements IGameEnd{
    public Duration endTime;

    public TimerEnd(Duration endTime){
        this.endTime = endTime;
    }

    public void startTimer(){
        long ticks = endTime.getSeconds() * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                endMatch();
            }
        }.runTaskLater(LGWManager.INSTANCE, ticks);
    }

}
