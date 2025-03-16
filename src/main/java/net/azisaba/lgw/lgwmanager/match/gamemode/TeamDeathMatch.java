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

public class TeamDeathMatch implements IGameMode{
    @Override
    public void startMatch(MatchManager matchManager){
        for(IGameEnd gameEnd : matchManager.gameEnd){
            gameEnd.startTimer(matchManager);
        }
        MapData mapData = matchManager.matchData.mapData;
        Map<BattleTeam, Location> spawnPoint = mapData.spawnPoint;
        EnumMap<BattleTeam, Set<Player>> playerList = matchManager.matchData.playerList;

        for(BattleTeam battleTeam : BattleTeam.values()){
            ItemStack teamChestPlate = matchManager.equipment.getTeamChestPlate(battleTeam);
            Location spawn = spawnPoint.get(battleTeam);
            for(Player player : playerList.get(battleTeam)){
                matchJoinPlayer(player, spawn, teamChestPlate);
            }
        }
    }

    public void matchJoinPlayer(Player player, Location spawn, ItemStack teamChestPlate) {
        player.teleport(spawn);
        player.getInventory().setChestplate(teamChestPlate);

        //これより下は装飾

        //ここまで
    }
}
