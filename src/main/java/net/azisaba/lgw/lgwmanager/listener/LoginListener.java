package net.azisaba.lgw.lgwmanager.listener;

import net.azisaba.lgw.lgwmanager.LGWManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        LGWManager.getSidebar().addPlayer(e.getPlayer());
        if(LGWManager.matchList.isEmpty()){
            Bukkit.dispatchCommand(e.getPlayer(), "spawn");
        }else {
            LGWManager.matchList.get(0).addPlayerInMatch(e.getPlayer());
        }

    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        LGWManager.getSidebar().removePlayer(e.getPlayer());
    }
}
