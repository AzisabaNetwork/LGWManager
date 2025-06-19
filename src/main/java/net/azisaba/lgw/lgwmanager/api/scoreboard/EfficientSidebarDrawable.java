package net.azisaba.lgw.lgwmanager.api.scoreboard;

import net.kyori.adventure.text.ComponentLike;
import net.megavex.scoreboardlibrary.api.objective.ScoreFormat;
import net.megavex.scoreboardlibrary.api.sidebar.component.LineDrawable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EfficientSidebarDrawable implements LineDrawable {
    private final EfficientSidebar efficientSidebar;
    private int index = 0;

    public EfficientSidebarDrawable(EfficientSidebar efficientSidebar) {
        this.efficientSidebar = efficientSidebar;
    }

    @Override
    public void drawLine(@NotNull ComponentLike line, @Nullable ScoreFormat scoreFormat) {
        if (index < 15) {
            efficientSidebar.setLine(index++, line.asComponent());
        }
    }

    @Override
    public void drawLine(@NotNull ComponentLike line) {
        drawLine(line, null);
    }

    public void clearRemaining() {
        efficientSidebar.clearRemainingLines(index);
    }
}