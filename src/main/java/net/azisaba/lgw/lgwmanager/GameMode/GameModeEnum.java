package net.azisaba.lgw.lgwmanager.GameMode;

import net.azisaba.lgw.lgwmanager.Equipment.DefaultEquipment;
import net.azisaba.lgw.lgwmanager.Equipment.IEquipment;
import net.azisaba.lgw.lgwmanager.GameEnd.IGameEnd;
import net.azisaba.lgw.lgwmanager.GameEnd.KillPointEnd;
import net.azisaba.lgw.lgwmanager.GameEnd.TimerEnd;
import net.azisaba.lgw.lgwmanager.Reward.IReward;
import net.azisaba.lgw.lgwmanager.Reward.KillReward;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public enum GameModeEnum {
    TEAM_DEATH_MATCH("tdm", new TeamDeathMatch(), Arrays.asList(new TimerEnd(Duration.ofMinutes(10))), Arrays.asList(new KillReward()), new DefaultEquipment());

    public final String alias;
    public final IGameMode gameMode;
    public final List<IGameEnd> gameEnd;
    public final List<IReward> reward;
    public final IEquipment equipment;

    GameModeEnum(String alias, IGameMode gameMode, List<IGameEnd> gameEnd, List<IReward> reward, IEquipment equipment){
        this.alias = alias;
        this.gameMode = gameMode;
        this.gameEnd = gameEnd;
        this.reward = reward;
        this.equipment = equipment;
    }
}
