package net.azisaba.lgw.lgwmanager.task;

import net.azisaba.lgw.lgwmanager.LGWManager;
import net.azisaba.lgw.lgwmanager.match.MatchManager;
import net.azisaba.lgw.lgwmanager.match.data.MapData;
import net.azisaba.lgw.lgwmanager.match.event.MatchTimeEndEvent;
import net.azisaba.lgw.lgwmanager.match.gamemode.MapType;
import net.azisaba.lgw.lgwmanager.util.LGWMUtill;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class MapSelectTask extends BukkitRunnable {

    private CountDownTask timer;
    public MatchManager matchManager;
    private Duration countDownTime = Duration.ofSeconds(10);//10秒

    //======================
    //設定値
    private final int mapCount = 3;//マップ投票で表示されるマップ数
    //======================

    public MapSelectTask(MatchManager matchManager){
        this.matchManager = matchManager;
    }

    @Override
    public void run() {

        this.timer = (CountDownTask) new CountDownTask(
                this.countDownTime,
                () -> {

                },
                matchManager
        ).runTask(LGWManager.INSTANCE);
    }

    public List<MapData> getRandomMapData(MapType mapType){
        List<MapData> allMapList = LGWManager.getMapList().get(mapType);
        List<MapData> mapList = new ArrayList<>();
        for(int i : LGWMUtill.getUniqueRandomNumbers(mapCount, allMapList.size() - 1)){
            mapList.add(allMapList.get(i));
        }
        return mapList;
    }

}
