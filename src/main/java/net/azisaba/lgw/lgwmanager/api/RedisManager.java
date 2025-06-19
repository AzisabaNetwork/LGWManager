package net.azisaba.lgw.lgwmanager.api;


import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import net.azisaba.lgw.lgwmanager.LGWManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;


public class RedisManager {
    public RedisClient redisClient;
    //Redisが使用可能か否か
    public boolean isTrue;
    public static String dbGroup;
    public StatefulRedisConnection<String, String> connection;

    public RedisManager() {
        FileConfiguration config = LGWManager.INSTANCE.getConfig();
        Logger logger = Bukkit.getLogger();
        dbGroup = config.getString("Redis.databaseGroup", "lgwm");
        String hostName = config.getString("Redis.IP", "localhost").trim();
        int hostPort = config.getInt("Redis.Port", 6379);
        String userName = config.getString("Redis.user", null);
        CharSequence userPass = config.getString("Redis.password", null);
        String uri;
        if (userName != null && userPass != null) {
            uri = "redis://" + userName + ":" + userPass + "@" + hostName + ":" + hostPort;
        } else if (userPass != null) {
            uri = "redis://:" + userPass + "@" + hostName + ":" + hostPort;
        } else {
            uri = "redis://" + hostName + ":" + hostPort;
        }

        RedisURI redisURI = RedisURI.create(uri);

        try {
            this.redisClient = RedisClient.create(redisURI);
            this.connection = redisClient.connect();
            isTrue = true;
            logger.info("[LGWM]Redisへの接続に成功しました");
        } catch (RedisConnectionException e) {
            logger.warning("[LGWM]Redis接続失敗: " + e.getMessage());
            logger.info("Connecting to Redis at: " + redisURI.toURI());
            isTrue = false;
            this.connection = null;
        }
    }

    public void shutdownRedis() {
        LGWManager.getServerSettings().shutdownServer();
        if(this.connection != null){
            connection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
            Bukkit.getLogger().info("[LGWM]Redisとの接続を切断しました");
        }
    }
}
