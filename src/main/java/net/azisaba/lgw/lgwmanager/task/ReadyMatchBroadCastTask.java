package net.azisaba.lgw.lgwmanager.task;

import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ReadyMatchBroadCastTask extends BukkitRunnable {
    MatchManager matchManager;
    public ReadyMatchBroadCastTask(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    @Override
    public void run() {
        Bukkit.broadcast(Component.text(matchManager.matchServer + "現在ほかのプレイヤーを待っています...").color(NamedTextColor.GREEN));
    }
}
