package net.azisaba.lgw.lgwmanager.match.gamemode;

import net.azisaba.lgw.lgwmanager.match.gamemode.equipment.IEquipment;
import net.azisaba.lgw.lgwmanager.match.gamemode.gameend.IGameEnd;
import net.azisaba.lgw.lgwmanager.match.gamemode.reward.IReward;

import java.util.List;

public class GameModeData {
    public final GameModeHandler handler;
    public final List<IGameEnd> endConditions;
    public final List<IReward> rewards;
    public final IEquipment equipment;

    public GameModeData(GameModeHandler handler, List<IGameEnd> endConditions, List<IReward> rewards, IEquipment equipment) {
        this.handler = handler;
        this.endConditions = endConditions;
        this.rewards = rewards;
        this.equipment = equipment;
    }
}