package net.azisaba.lgw.lgwmanager;

import lombok.Getter;
import net.azisaba.lgw.lgwmanager.api.RedisManager;
import net.azisaba.lgw.lgwmanager.listener.LoginListener;
import net.azisaba.lgw.lgwmanager.api.scoreboard.ScoreBoardManager;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static dev.felnull.bettergui.BetterGUI.plugin;

public final class LGWManager extends JavaPlugin {
    @Getter
    public static LGWManager INSTANCE;
    @Getter
    public static ScoreboardLibrary scoreboardLibrary;
    @Getter
    public static Sidebar sidebar;
    @Getter
    public static RedisManager redisManager;
    @Getter
    public static boolean isLobby;
    @Getter
    public static String serverName;

    @Override
    public void onEnable() {
        INSTANCE = this;
        redisManager = new RedisManager();
        isLobby = INSTANCE.getConfig().getBoolean("isLobby", false);
        serverName = INSTANCE.getConfig().getString("serverName", "server_dev");
        saveDefaultConfig();
        initScoreboard();
    }

    @Override
    public void onDisable() {
        scoreboardLibrary.close();
        redisManager.shutdownRedis();
    }

    private void initListener() {
        Bukkit.getPluginManager().registerEvents(new LoginListener(), this);
    }
    private void initScoreboard() {
        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(plugin);
        } catch (NoPacketAdapterAvailableException e) {
            scoreboardLibrary = new NoopScoreboardLibrary();
            plugin.getLogger().warning("[LGWM]パケットアダプターの有効化に失敗しました");
        }
        initListener();
        sidebar = scoreboardLibrary.createSidebar();
        ScoreBoardManager scoreBoardManager = new ScoreBoardManager(this, sidebar);
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.hasMetadata("NPC")){
                continue;
            }
            sidebar.addPlayer(p);
        }
        //ScoreBoardのtick処理呼び出し
        new BukkitRunnable() {
            @Override
            public void run() {
                scoreBoardManager.tick();
            }
        }.runTaskTimer(this, 0, 1);
    }
}
