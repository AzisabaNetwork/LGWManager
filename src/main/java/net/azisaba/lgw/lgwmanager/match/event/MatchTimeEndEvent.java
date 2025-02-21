package net.azisaba.lgw.lgwmanager.match.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.azisaba.lgw.lgwmanager.match.data.MatchData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
@EqualsAndHashCode(callSuper = false)
public class MatchTimeEndEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    public final MatchManager matchManager;

    public MatchTimeEndEvent(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
