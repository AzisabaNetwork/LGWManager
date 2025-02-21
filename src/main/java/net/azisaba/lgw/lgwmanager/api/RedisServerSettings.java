package net.azisaba.lgw.lgwmanager.api;

import io.lettuce.core.KeyValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.Setter;
import net.azisaba.lgw.lgwmanager.LGWManager;
import org.bukkit.Bukkit;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RedisServerSettings {
    StatefulRedisConnection<String, String> connection = LGWManager.getRedisManager().connection;
    RedisReactiveCommands<String, String> reactiveCommands = connection.reactive();
    RedisCommands<String,String> syncCommands = connection.sync();
    Set<String> serverList = new HashSet<>();
    @Setter
    public static String idleServer;

    public void setupServer() {
        if(!LGWManager.isLobby) {
            Mono<Boolean> result = reactiveCommands.hset("servers_status", LGWManager.getServerName().getFirst(), "idle");
            result.subscribe(success -> {
                if (success) {
                    Bukkit.getLogger().info("サーバーをRedisに登録しました");
                }else {
                    Bukkit.getLogger().warning("サーバーをRedisに登録することに失敗しました");
                }
            });
        }else {
            Bukkit.getLogger().info("ロビーサーバーのためRedisに登録しませんでした");
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

    private String getIdleServer() {
        for(Map.Entry<String, String> entry : getServerList().entrySet()){
            if(entry.getValue().equals("idle")){
                return entry.getKey();
            }
        }
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

    public void setServerStatus(boolean isIdle){
        if(isIdle) {
            reactiveCommands.hset("servers_status", LGWManager.getServerName().getLast(), "idle").subscribe();
        }else {
            reactiveCommands.hset("servers_status", LGWManager.getServerName().getLast(), "busy").subscribe();
        }
    }
}
