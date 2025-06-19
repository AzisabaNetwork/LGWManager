package net.azisaba.lgw.lgwmanager.match.data;

import lombok.Getter;
import lombok.NonNull;
import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.api.scoreboard.DefaultMatchScoreBoardManager;
import net.azisaba.lgw.lgwmanager.api.scoreboard.IMatchScoreBoard;
import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.azisaba.lgw.lgwmanager.match.gamemode.GameModeEnum;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

import static net.azisaba.lgw.lgwmanager.LGWManager.playerScoreboardMap;
import static net.azisaba.lgw.lgwmanager.LGWManager.scoreboardLibrary;

public class MatchData {
    public GameModeEnum gameMode;
    //マッチが進行中か
    public boolean playing = false;
    @Getter
    public EnumMap<BattleTeam, Integer> TeamPoint = new EnumMap<>(BattleTeam.class);
    public EnumMap<BattleTeam, Set<Player>> playerList = new EnumMap<>(BattleTeam.class);
    public MapData mapData;
    public List<Player> matchPlayer = new ArrayList<>();
    // スコアボードチーム
    private final Map<BattleTeam, Team> teams = new HashMap<>();
    private MatchManager manager;

    public MatchData(GameModeEnum gameMode, MatchManager manager){
        this.gameMode = gameMode;
        this.manager = manager;
        for (BattleTeam battleTeam : gameMode.teamList){
            this.playerList.put(battleTeam, new HashSet<>());
        }
    }


    private IMatchScoreBoard getLibraryScoreboard(Player player) {
        return playerScoreboardMap.get(player);
    }

    public Set<Player> getPlayerList() {
        Set<Player> playerSet = new HashSet<>();
        for(BattleTeam team : BattleTeam.values()){
            playerSet.addAll(this.playerList.get(team));
        }
        return playerSet;
    }

    public Set<Player> getTeamPlayerList(BattleTeam battleTeam) {
        return this.playerList.get(battleTeam);
    }
    public void addPlayer(Player player) {
        matchPlayer.add(player);
        if(playing){
            distributePlayer(player);
        }

    }

    public void initTeamDistribute(Set<BattleTeam> teams) {
        // plistをシャッフル
        Collections.shuffle(matchPlayer);

        // 均等に分ける
        matchPlayer.forEach(this::distributePlayer);
    }

    /*
    チーム振り分け
     */
    public void distributePlayer(Player player) {

        BattleTeam selectedTeam = playerList.keySet().stream()
                .min(Comparator
                        .comparing((BattleTeam team) -> playerList.get(team).size()) // ①人数の少なさ
                        .thenComparing(team -> TeamPoint.getOrDefault(team, 0))      // ②ポイントの少なさ
                )
                .orElse(null);
        if (selectedTeam != null) {
            playerList.get(selectedTeam).add(player);
            Sidebar sidebar = scoreboardLibrary.createSidebar();
            DefaultMatchScoreBoardManager matchBoard = new DefaultMatchScoreBoardManager(LGWManager.getINSTANCE(), sidebar, manager);
            sidebar.addPlayer(player);
            playerScoreboardMap.put(player, matchBoard);
        } else {
            player.sendMessage("チームの振り分けに失敗したにゃ…");
            //ここにロビーへ送り返す処理？
        }
    }

    public int getCurrentTeamPoint(@NonNull BattleTeam battleTeam) {
        // ポイントを取得、無ければ0
        return getTeamPoint().getOrDefault(battleTeam, 0);
    }


    public Team getScoreboardTeam(BattleTeam team) {
        return teams.getOrDefault(team, null);
    }


}
