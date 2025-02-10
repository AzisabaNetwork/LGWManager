package net.azisaba.lgw.lgwmanager.listener;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.task.PlayerHealingTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

//LeonGunWarあるうちはいらない
public class AutoRespawnListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent e) {
        Player deader = e.getEntity();

        Bukkit.getScheduler().runTask(LGWManager.INSTANCE, () -> {
            // リスポーン
            deader.spigot().respawn();
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        //消火
        Bukkit.getScheduler().scheduleSyncDelayedTask(LGWManager.INSTANCE, () -> p.setFireTicks(0), 0);
        //HP食料回復
        new PlayerHealingTask(p).runTaskLater(LGWManager.INSTANCE, 0);
    }
}
