package net.azisaba.lgw.lgwmanager.listener;

import net.azisaba.lgw.lgwmanager.LGWManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        LGWManager.getSidebar().addPlayer(e.getPlayer());
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        LGWManager.getSidebar().removePlayer(e.getPlayer());
    }
}
