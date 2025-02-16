package net.azisaba.lgw.lgwmanager.match.gamemode.reward;

import net.azisaba.lgw.lgwmanager.match.data.MatchData;
import net.azisaba.lgw.lgwmanager.match.event.PlayerRewardEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KillReward implements IReward{
    @Override
    public void giveReward(Player player, MatchData matchData) {
        Bukkit.getPluginManager().callEvent(new PlayerRewardEvent(player, matchData, RewardType.KILL));
    }
}
