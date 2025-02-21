package net.azisaba.lgw.lgwmanager.api;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import net.azisaba.lgw.lgwmanager.LGWManager;
import org.bukkit.Bukkit;
import reactor.core.publisher.Mono;

import java.util.Map;

public class RedisServerSettings {
    StatefulRedisConnection<String, String> connection = LGWManager.getRedisManager().connection;
    RedisReactiveCommands<String, String> reactiveCommands = connection.reactive();
    RedisCommands<String,String> syncCommands = connection.sync();

    public void setupServer() {
        if(!LGWManager.isLobby) {
            String serverName = LGWManager.getServerName().getFirst();
            Mono<String> existingStatus = reactiveCommands.hget("servers_status", serverName);
            existingStatus.subscribe(status -> {
                if (status != null) {
                    Bukkit.getLogger().warning("サーバー名 '" + serverName + "' は既にRedisに登録されています！");
                } else {

                    Mono<Boolean> result = reactiveCommands.hset("servers_status", serverName, "idle");
                    result.subscribe(success -> {
                        if (success) {
                            Bukkit.getLogger().info("サーバー名をRedisに登録しました");
                        } else {
                            Bukkit.getLogger().warning("サーバー名をRedisに登録することに失敗しました");
                        }
                    });
                }
            }, error -> {
                Bukkit.getLogger().warning("[RedisServerSettings::setupServer]Redisへの接続中にエラーが発生しました: " + error.getMessage());
            });
        }else {
            Bukkit.getLogger().info("ロビーサーバーのためサーバー名をRedisに登録しませんでした");
        }

    }

    public void shutdownServer() {
        //非同期処理中にプログラムが停止する可能性があるので同期的に処理
        syncCommands.hdel("server_status", LGWManager.getServerName().getLast());
        Bukkit.getLogger().info("サーバーをRedisから削除しました");
    }

    public Map<String, String> getServerList(){
        return syncCommands.hgetall("servers_status");
    }

    public String getIdleServer() {
        for(Map.Entry<String, String> entry : getServerList().entrySet()){
            if(entry.getValue().equals("idle")){
                Bukkit.getLogger().info("試合サーバーが見つかりました: " + entry.getKey());
                setServerStatus(entry.getKey(), false);
                return entry.getKey();
            }
        }
        Bukkit.getLogger().info("現在使用可能な試合サーバーがありません、試合サーバーの増設を検討してください");
        return null;
        /* リアクティブとして書こうとした残骸　今後リアクティブを書く時の参考にするために
        リアクティブ:始めから終わりまでいつ終わってもいいときにしか使ってはならない(戒め 例:コマンドの結果表示とかキャッシュとか

        private Mono<String> getIdleServer(Consumer<String> consumer) {
        return reactiveCommands.hgetall("servers_status") // すべてのサーバー状態を取得
                .collectMap(KeyValue::getKey, KeyValue::getValue) // Map<String, String> に変換
                .flatMap(map -> map.entrySet().stream() //FlatmapにするとMono<>が返ってくる MapだとMono<Mono<>>が返る?
                        .filter(entry -> "idle".equals(entry.getValue())) // idleなサーバーを探す
                        .findFirst() // 最初に見つかったものを取得
                        .map(entry -> Mono.just(entry.getKey())) // Mono<String> に変換
                        .orElse(Mono.empty()) // 見つからなかったら空のMono
                );
        result.subscribe(server -> {
            Bukkit.getLogger().info("試合サーバーが見つかりました: " + server);
            setIdleServer(server);
        }, error -> {
            Bukkit.getLogger().warning("試合サーバーの検索中にエラーが発生しました" + error);
        }, () -> {
            Bukkit.getLogger().info("現在使用可能な試合サーバーがありません、試合サーバーの増設を検討してください");
            setIdleServer(null);
        });
         */
    }

    public void setServerStatus(String serverName, boolean isIdle){
        if(isIdle) {
            reactiveCommands.hset("servers_status", serverName, "idle").subscribe();
        }else {
            reactiveCommands.hset("servers_status", serverName, "busy").subscribe();
        }
    }

    public void setServerStatus(boolean isIdle){
        if(isIdle) {
            reactiveCommands.hset("servers_status", LGWManager.getServerName().getLast(), "idle").subscribe();
        }else {
            reactiveCommands.hset("servers_status", LGWManager.getServerName().getLast(), "busy").subscribe();
        }
    }
}
