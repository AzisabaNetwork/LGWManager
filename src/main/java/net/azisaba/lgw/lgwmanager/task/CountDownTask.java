package net.azisaba.lgw.lgwmanager.task;

import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Set;

public class CountDownTask extends BukkitRunnable {
    public Duration time;
    public Duration startTime;
    private final Runnable onEnd;
    private final MatchManager matchManager;
    private BossBar bossBar;
    private Set<Player> playerList;

    public CountDownTask(Duration time, Runnable onEnd, MatchManager matchManager) {
        this.time = time;
        this.startTime = time;
        this.onEnd = onEnd;
        this.matchManager = matchManager;
        this.playerList = matchManager.matchData.getPlayerList();
        bossBar = BossBar.bossBar(
                Component.text(formatTime(time)),
                1.0f,
                BossBar.Color.GREEN,
                BossBar.Overlay.PROGRESS
        );
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
        bossBar.name(Component.text(formatTime(time)));
        bossBar.progress((float) time.getSeconds() / startTime.getSeconds());

        // 残り時間を減らす
        this.time = this.time.minusSeconds(1);
    }

    private static String formatTime(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
