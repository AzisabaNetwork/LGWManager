package net.azisaba.lgw.lgwmanager.match;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.api.RedisServerSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LobbyMatchManager {
    public String matchServer;

    public void startMatch(Player p){
        if(!LGWManager.isLobby()){
            p.sendMessage("この機能はロビーサーバー限定です");
            return;
        }
        String idleServer = LGWManager.getServerSettings().getIdleServer();
        if(idleServer == null){
            p.sendMessage(Component.text("現在利用可能な試合サーバーがありません、既に開始されているマッチに参加してください"));
            return;
        }
        initStartMatch(idleServer);
    }

    public void startMatch(){
        if(!LGWManager.isLobby()){
            Bukkit.getLogger().info("この機能はロビーサーバー限定です");
            return;
        }
        String idleServer = LGWManager.getServerSettings().getIdleServer();
        if(idleServer == null){
            return;
        }
        initStartMatch(idleServer);
    }

    /*
    startMatchの共通処理部分
     */
    private void initStartMatch(String idleServer){
        RedisServerSettings redisServerSettings = LGWManager.getServerSettings();
        this.matchServer = idleServer;

        // Redisにマッチ情報を登録（例：match:pending:{serverName}）
        String key = "match:pending:" + idleServer;
        String matchData = "type=DEFAULT;timestamp=" + System.currentTimeMillis(); // 必要な情報を整形して格納
        LGWManager.getRedisManager().connection.sync().set(key, matchData);

        // RedisのPub/Subで試合開始を通知（チャンネル：match:start）
        LGWManager.getRedisManager().connection.sync().publish("match:start", idleServer);

        // あとでプレイヤー移動処理などもここに追加する
        Bukkit.getLogger().info("試合を " + idleServer + " で開始しました！");
    }
}
