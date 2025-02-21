package net.azisaba.lgw.lgwmanager.task;

import net.azisaba.lgw.lgwmanager.match.MatchManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class CountDownTask extends BukkitRunnable {
    public Duration time;
    private final Runnable onEnd;
    private final MatchManager matchManager;

    public CountDownTask(Duration time, Runnable onEnd, MatchManager matchManager) {
        this.time = time;
        this.onEnd = onEnd;
        this.matchManager = matchManager;
    }

    @Override
    public void run() {
        if (this.time.isZero() || this.time.isNegative()) {
            onEnd.run();
            this.cancel();
            return;
        }
        matchManager.matchTime = this.time;
        matchManager.matchScoreBoard.tick();
        // 残り時間を減らす
        this.time = this.time.minusSeconds(1);
    }
}
