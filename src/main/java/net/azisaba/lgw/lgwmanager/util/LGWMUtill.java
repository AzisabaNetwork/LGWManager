package net.azisaba.lgw.lgwmanager.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LGWMUtill {

    /*
    count: 取り出す個数
    max: 値の最大値
     */
    public static List<Integer> getUniqueRandomNumbers(int count, int max){
        if (count > max + 1) {
            throw new IllegalArgumentException("countはmax以下じゃないとダメ！");
        }

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= max; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        return numbers.subList(0, count);
    }
}
