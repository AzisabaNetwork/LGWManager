package net.azisaba.lgw.lgwmanager.match.gamemode;

import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.azisaba.lgw.lgwmanager.match.data.MapData;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.IGameEnd;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class TeamDeathMatchHandler implements GameModeHandler {
    @Override
    public void onStart(MatchManager manager) {
        // 開始ロジック（装備配布・テレポートなど）
    }

    @Override
    public void onEnd(MatchManager manager) {
        // 終了ロジック（得点発表・リセットなど）
    }
}
