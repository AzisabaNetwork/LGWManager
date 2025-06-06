package net.azisaba.lgw.lgwmanager.match.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * プレイヤーがエントリー解除したときに呼び出されるイベント
 *
 * @author siloneco
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlayerLeaveEntryMatchEvent extends Event {

    // エントリー解除したプレイヤー
    private final Player leavePlayer;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PlayerLeaveEntryMatchEvent(Player player) {
        this.leavePlayer = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
