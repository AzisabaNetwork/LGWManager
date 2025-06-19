package net.azisaba.lgw.lgwmanager.api.scoreboard;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

public class ScoreBoardManager implements IMatchScoreBoard{
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
                    return Component.text(time, NamedTextColor.GRAY);
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
    @Override
    public void tick() {
        titleAnimation.nextFrame();

        EfficientSidebar efficient = new EfficientSidebar(sidebar);

        // タイトル行描画
        componentSidebar.titleComponent().draw(new EfficientSidebarDrawable(efficient) {
            @Override
            public void drawLine(@NotNull ComponentLike line) {
                efficient.setTitle(line.asComponent());
            }
        });

        // 本文行描画
        EfficientSidebarDrawable body = new EfficientSidebarDrawable(efficient);
        componentSidebar.linesComponent().draw(body);
        body.clearRemaining();
    }

    private @NotNull SidebarAnimation<Component> createGradientAnimationGray(@NotNull Component text) {
        String content = ((TextComponent) text).content();
        List<Component> frames = new ArrayList<>();

        int totalFrames = 32;
        for (int frame = 0; frame < totalFrames; frame++) {
            float shift = (float) frame / totalFrames;
            Component combined = Component.empty();

            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                float ratio = (float) i / content.length();
                float brightness = (ratio + shift) % 1f;

                int value = (int) (255 - brightness * 128); // 255→127
                if (value < 127) value = 127;

                TextColor color = TextColor.color(value, value, value);
                Component letter = Component.text(c, color, TextDecoration.BOLD);
                combined = combined.append(letter);
            }

            frames.add(combined);
        }

        return new CollectionSidebarAnimation<>(frames);
    }
}
