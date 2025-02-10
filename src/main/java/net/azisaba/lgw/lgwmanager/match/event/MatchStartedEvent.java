package net.azisaba.lgw.lgwmanager.match.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.azisaba.lgw.lgwmanager.match.data.MapData;
import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * マッチが開始されたときに呼び出されるイベント
 *
 * @author siloneco
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MatchStartedEvent extends Event {

    // 試合を行うマップ
    private MapData map;
    // 各チームのプレイヤーリスト
    private Map<BattleTeam, List<Player>> teamPlayers;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public MatchStartedEvent(MapData currentGameMap, Map<BattleTeam, List<Player>> teamPlayers) {
        this.map = currentGameMap;
        this.teamPlayers = teamPlayers;
    }

    public List<Player> getAllTeamPlayers() {
        return teamPlayers.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Player> getTeamPlayers(BattleTeam team) {
        return teamPlayers.get(team);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
