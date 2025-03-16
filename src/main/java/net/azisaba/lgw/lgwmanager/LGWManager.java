package net.azisaba.lgw.lgwmanager;

import lombok.AccessLevel;
import lombok.Getter;
import net.azisaba.lgw.lgwmanager.api.RedisManager;
import net.azisaba.lgw.lgwmanager.api.RedisServerSettings;
import net.azisaba.lgw.lgwmanager.listener.LoginListener;
import net.azisaba.lgw.lgwmanager.api.scoreboard.ScoreBoardManager;
import net.azisaba.lgw.lgwmanager.match.data.MapData;
import net.azisaba.lgw.lgwmanager.match.gamemode.MapType;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

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
    public static LinkedList<String> serverName = new LinkedList<>();
    @Getter
    public static RedisServerSettings serverSettings;
    @Getter
    public static EnumMap<MapType, List<MapData>> mapList;

    @Override
    public void onEnable() {
        INSTANCE = this;
        redisManager = new RedisManager();
        isLobby = INSTANCE.getConfig().getBoolean("isLobby", false);
        serverName.addFirst(INSTANCE.getConfig().getString("ServerName", "server_dev"));
        saveDefaultConfig();
        initScoreboard();
        serverSettings = new RedisServerSettings();
        serverSettings.setupServer();
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
        }.runTaskTimer(this, 0, 2);
    }

    public void leaveLobbyScoreBoard(Player p){
        getSidebar().removePlayer(p);
    }

}
