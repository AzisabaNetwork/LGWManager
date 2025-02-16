package net.azisaba.lgw.lgwmanager.match.gamemode.reward;

import net.azisaba.lgw.lgwmanager.match.data.MatchData;
import org.bukkit.entity.Player;

public interface IReward {
    public void giveReward(Player player, MatchData matchData);
}
