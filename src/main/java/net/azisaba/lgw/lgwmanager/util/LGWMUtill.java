package net.azisaba.lgw.lgwmanager.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class LGWMUtill {

    /*
    count: 取り出す個数
    max: 値の最大値
     */
    public static List<Integer> getUniqueRandomNumbers(int count, int max){
        if (count > max + 1) {
            throw new IllegalArgumentException("countはmax以下じゃないとダメ！");
        }

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= max; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        return numbers.subList(0, count);
    }

    /**
     * UUIDからオンラインのプレイヤーを安全に取得します。
     * オンラインでなければ空のOptionalを返します。
     */
    public static Optional<Player> getOnlinePlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return (player != null && player.isOnline()) ? Optional.of(player) : Optional.empty();
    }

    public static Player getOnlinePlayerOrNull(UUID uuid) {
        return getOnlinePlayer(uuid).orElse(null);
    }

    /**
     * UUIDからプレイヤー名を取得します。
     * オンラインでなければOfflinePlayerから取得します。
     */
    public static String getPlayerName(UUID uuid) {
        Player online = Bukkit.getPlayer(uuid);
        if (online != null && online.isOnline()) {
            return online.getName();
        }
        OfflinePlayer offline = Bukkit.getOfflinePlayer(uuid);
        return offline.getName() != null ? offline.getName() : "Unknown";
    }

    /**
     * UUIDがオンラインか確認するだけのヘルパー
     */
    public static boolean isOnline(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null && player.isOnline();
    }
}
