package net.azisaba.lgw.lgwmanager;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class LGWManager extends JavaPlugin {
    @Getter
    public static LGWManager INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
