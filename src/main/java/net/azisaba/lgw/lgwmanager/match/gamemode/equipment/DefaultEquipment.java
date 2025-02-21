package net.azisaba.lgw.lgwmanager.match.gamemode.equipment;

import lombok.Getter;
import net.azisaba.lgw.lgwmanager.match.BattleTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.EnumMap;
import java.util.UUID;

public class DefaultEquipment implements IEquipment{
    @Getter
    public final EnumMap<BattleTeam, EnumMap<ArmorPosition, ItemStack>> teamArmor;

    public DefaultEquipment() {
        EnumMap<ArmorPosition, ItemStack> armorItemStack = new EnumMap<>(ArmorPosition.class);
        EnumMap<BattleTeam, EnumMap<ArmorPosition, ItemStack>> teamArmor = new EnumMap<>(BattleTeam.class);

        for(BattleTeam team : BattleTeam.values()){
            armorItemStack.put(ArmorPosition.CHEST_PLATE, getTeamChestPlate(team));
            teamArmor.put(team, armorItemStack);
        }

        this.teamArmor = teamArmor;
    }

    // チームの色付きチェストプレート！！
    @Override
    public ItemStack getTeamChestPlate(BattleTeam team) {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', team.getTeamName())));
        meta.setColor(team.getColor());
        meta.setUnbreakable(true);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(),"generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1);
        return item;
    }
}
