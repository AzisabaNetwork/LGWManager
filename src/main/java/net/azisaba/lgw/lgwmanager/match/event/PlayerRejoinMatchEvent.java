package net.azisaba.lgw.lgwmanager.match.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayerRejoinMatchEvent extends Event {

    // 途中参加をしたプレイヤー
    private final Player player;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PlayerRejoinMatchEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
