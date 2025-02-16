package net.azisaba.lgw.lgwmanager.api.scoreboard;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import net.megavex.scoreboardlibrary.api.sidebar.component.ComponentSidebarLayout;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;
import net.megavex.scoreboardlibrary.api.sidebar.component.animation.CollectionSidebarAnimation;
import net.megavex.scoreboardlibrary.api.sidebar.component.animation.SidebarAnimation;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreBoardManager {
    private final Sidebar sidebar;
    private final ComponentSidebarLayout componentSidebar;
    private final SidebarAnimation<Component> titleAnimation;

    public ScoreBoardManager(@NotNull Plugin plugin, @NotNull Sidebar sidebar) {
        this.sidebar = sidebar;

        this.titleAnimation = createGradientAnimationGray(Component.text("LeGSystem", Style.style(TextDecoration.BOLD)));
        var title = SidebarComponent.animatedLine(titleAnimation);

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy年MM月dd日E");
        SimpleDateFormat dtf2 = new SimpleDateFormat("HH時mm分ss");

        SidebarComponent onlinePlayers = new KeyValueSidebarComponent(
                Component.text("オンラインのプレイヤー:").decorate(TextDecoration.BOLD),
                () -> Component.text(plugin.getServer().getOnlinePlayers().size())
        );

        SidebarComponent lines = SidebarComponent.builder()
                .addStaticLine(Component.text("Version:" + LGWManager.getINSTANCE().getDescription().getVersion(), NamedTextColor.GRAY))
                .addStaticLine(Component.text("---------------------"))
                .addDynamicLine(() -> {
                    var time = dtf.format(new Date());
                    return Component.text(time + "day", NamedTextColor.GRAY);
                })
                .addDynamicLine(() -> {
                    var time = dtf2.format(new Date());
                    return Component.text(time, NamedTextColor.GRAY);
                })
                .addComponent(onlinePlayers)
                .addStaticLine(Component.text("---------------------"))
                .addStaticLine(Component.text(ChatColor.translateAlternateColorCodes('&', "&7今すぐ &6azisaba.net &7で遊べ！")))
                .addStaticLine(Component.text("---------------------"))
                .build();


        this.componentSidebar = new ComponentSidebarLayout(title, lines);
    }

    // Called every tick
    public void tick() {
        // Advance title animation to the next frame
        titleAnimation.nextFrame();

        // Update sidebar title & lines
        componentSidebar.apply(sidebar);
    }

    private @NotNull SidebarAnimation<Component> createGradientAnimationGray(@NotNull Component text) {
        float step = 1f / 32f;

        TagResolver.Single textPlaceholder = Placeholder.component("text", text);
        List<Component> frames = new ArrayList<>((int) (2f / step));

        float phase = -1f;
        while (phase < 1) {
            frames.add(MiniMessage.miniMessage().deserialize("<gradient:white:gray:" + phase + "><text>", textPlaceholder));
            phase += step;
        }

        return new CollectionSidebarAnimation<>(frames);
    }
}
