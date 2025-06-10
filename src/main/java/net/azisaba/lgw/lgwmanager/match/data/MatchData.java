package net.azisaba.lgw.lgwmanager.match.data;

import lombok.Getter;
import lombok.NonNull;
import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import net.azisaba.lgw.lgwmanager.match.gamemode.GameModeEnum;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class MatchData {
    public GameModeEnum gameMode;
    //マッチが進行中か
    public boolean playing = false;
    @Getter
    public EnumMap<BattleTeam, Integer> TeamPoint = new EnumMap<>(BattleTeam.class);
    public EnumMap<BattleTeam, Set<Player>> playerList = new EnumMap<>(BattleTeam.class);
    public MapData mapData;
    public List<Player> readyPlayer = new ArrayList<>();
    // スコアボードチーム
    private final Map<BattleTeam, Team> teams = new HashMap<>();

    public MatchData(GameModeEnum gameMode){
        this.gameMode = gameMode;
        this.playerList.put(BattleTeam.RED, new HashSet<>());
        this.playerList.put(BattleTeam.BLUE, new HashSet<>());
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
        if(!playing){
            readyPlayer.add(player);
        }

    }

    public void distributePlayers(List<Team> teams) {
        // plistをシャッフル
        Collections.shuffle(readyPlayer);

        // 均等に分ける
        readyPlayer.forEach(player -> distributePlayer(player, teams));
    }

    public void distributePlayer(Player player, List<Team> teams) {

        // エントリーが少ないチームにプレイヤーを追加 (同じ場合はポイントが少ない方、それでも同じなら最初の要素)
        teams.stream()
                .min(Comparator.comparing(Team::getSize).thenComparing(this::getCurrentTeamPoint))
                .ifPresent(lowTeam -> lowTeam.addEntry(player.getName()));
    }

    public int getCurrentTeamPoint(@NonNull Team team) {
        // battleTeamに変換
        BattleTeam battleTeam = getBattleTeam(team);

        // 変換失敗なら0を返す
        if ( battleTeam == null ) {
            return 0;
        }

        // ポイントを取得、無ければ0
        return getTeamPoint().getOrDefault(battleTeam, 0);
    }

    public BattleTeam getBattleTeam(Team team) {
        // 各チームのプレイヤーリストを取得し、リスポーンするプレイヤーが含まれていればbreak
        for ( BattleTeam battleTeam : BattleTeam.values() ) {

            // スコアボードのTeamを取得
            Team scoreboardTeam = getScoreboardTeam(battleTeam);

            // 同じならreturn
            if ( scoreboardTeam == team ) {
                return battleTeam;
            }
        }

        // 無ければnull
        return null;
    }

    public Team getScoreboardTeam(BattleTeam team) {
        return teams.getOrDefault(team, null);
    }


}
