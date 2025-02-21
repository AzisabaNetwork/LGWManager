package net.azisaba.lgw.lgwmanager.match.gamemode.equipment;

import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import org.bukkit.inventory.ItemStack;

public interface IEquipment {
    public default ItemStack getTeamChestPlate(BattleTeam team) {
        return null;
    }
}
