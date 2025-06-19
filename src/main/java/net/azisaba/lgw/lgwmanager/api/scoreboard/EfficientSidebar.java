package net.azisaba.lgw.lgwmanager.api.scoreboard;

import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EfficientSidebar {
    private final Sidebar sidebar;
    private final Map<Integer, Component> previousLines = new HashMap<>();
    private Component previousTitle = null;

    public EfficientSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    public void setTitle(Component newTitle) {
        if (!Objects.equals(previousTitle, newTitle)) {
            sidebar.title(newTitle);
            previousTitle = newTitle;
        }
    }

    public void setLine(int index, Component line) {
        if (!Objects.equals(previousLines.get(index), line)) {
            sidebar.line(index, line);
            previousLines.put(index, line);
        }
    }

    public void clearRemainingLines(int fromIndex) {
        for (int i = fromIndex; i < 15; i++) {
            if (previousLines.containsKey(i)) {
                sidebar.line(i, null);
                previousLines.remove(i);
            }
        }
    }
}