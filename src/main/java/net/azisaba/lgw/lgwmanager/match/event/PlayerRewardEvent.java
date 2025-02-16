package net.azisaba.lgw.lgwmanager.match.event;

import lombok.Getter;
import net.azisaba.lgw.lgwmanager.match.data.MatchData;
import net.azisaba.lgw.lgwmanager.match.gamemode.reward.RewardType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerRewardEvent extends @NotNull Event {
    @Getter
    private final Player player;
    @Getter
    private final MatchData matchData;
    @Getter
    private final RewardType rewardType;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PlayerRewardEvent(Player player, MatchData matchData, RewardType rewardType){
        this.player = player;
        this.matchData = matchData;
        this.rewardType = rewardType;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
