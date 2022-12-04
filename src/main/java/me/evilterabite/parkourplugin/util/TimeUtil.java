package me.evilterabite.parkourplugin.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

public class TimeUtil {
    public static String timeIntoHHMMSS(long millis) {
        Duration duration = Duration.ofMillis(millis);
        long seconds = duration.getSeconds();
        return seconds/3600 + ":" + (seconds % 3600) / 60 + ":" + seconds % 60;
    }

    public static long HHMMSSintoMillis(String string) {
        ArrayList<String> split = new ArrayList<>(Arrays.asList(string.split(":")));
        long HH = 0;
        long MM = 0;
        long SS = 0;
        for(String s : split) {
            long l = Long.parseLong(s);
            if(s.equals(split.get(0))) {
                HH = l;
            }
            if(s.equals(split.get(1))) {
                MM = l;
            }
            if(s.equals(split.get(2))) {
                SS = l;
            }
        }

        return Duration.ofHours(HH).toMillis() + Duration.ofMinutes(MM).toMillis() + Duration.ofSeconds(SS).toMillis();
    }
}
