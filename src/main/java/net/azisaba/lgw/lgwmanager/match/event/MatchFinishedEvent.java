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

// TODO MVPとかの情報も載せたい。

/**
 * マッチが終了したときに呼び出されるイベント
 *
 * @author siloneco
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MatchFinishedEvent extends Event {

    // マッチを行ったマップ
    private final MapData map;
    // 勝利したチーム
    private final List<BattleTeam> winners;
    // 各チームのプレイヤー
    private final Map<BattleTeam, List<Player>> teamPlayers;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public MatchFinishedEvent(MapData map,List<BattleTeam> winners,Map<BattleTeam, List<Player>> teamPlayers) {
        this.map = map;
        this.winners = winners;
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
