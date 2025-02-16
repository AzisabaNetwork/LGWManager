package net.azisaba.lgw.lgwmanager.api;


import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisURI;
import net.azisaba.lgw.lgwmanager.LGWManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;


public class RedisManager {
    public RedisClient redisClient;
    //Redisが使用可能か否か
    public boolean isTrue;

    public RedisManager() {
        FileConfiguration config = LGWManager.INSTANCE.getConfig();
        Logger logger = Bukkit.getLogger();
        String hostName = config.getString("Redis.IP", "localhost");
        int hostport = config.getInt("Redis.Port", 6379);
        String userName = config.getString("Redis.user", null);
        String userPass = config.getString("Redis.password", null);
        RedisURI redisURI;

        if(userName != null && userPass != null ){
            redisURI = RedisURI.Builder.redis(hostName, hostport)
                    .withAuthentication(userName, userPass)
                    .build();
        } else if(userPass != null){
            redisURI = RedisURI.Builder.redis(hostName, hostport)
                    .withPassword(userPass)
                    .build();
        }else {
            redisURI = RedisURI.Builder.redis(hostName, hostport)
                    .build();
        }

        try {
            this.redisClient = RedisClient.create(redisURI);
            isTrue = true;
            logger.info("[LGWM]Redisへの接続に成功しました");
        } catch (RedisConnectionException e) {
            logger.warning("[LGWM]Redis接続失敗: " + e.getMessage());
            isTrue = false;
        }
    }

    public void shutdownRedis() {
        if (redisClient != null) {
            redisClient.shutdown();
            Bukkit.getLogger().info("[LGWM]Redisとの接続を切断しました");
        }
    }




}
