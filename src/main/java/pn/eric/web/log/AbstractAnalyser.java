package pn.eric.web.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Eric
 */
public abstract class AbstractAnalyser {

    static DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    static Date time1;
    static Date time2;
    static Date time3;
    static Date time4;
    static Date time5;
    static Date time6;
    static long timeDiffLimit = 300000;
    static long timeAverageLimit = 60000;

    static {
        try {
            time1 = sdf.parse("13:00:00");
            time2 = sdf.parse("19:00:00");
            time3 = sdf.parse("23:00:00");
            time4 = sdf.parse("00:00:00");
            time5 = sdf.parse("14:00:00");
            time6 = sdf.parse("20:00:00");
        } catch (Exception e) {

        }
    }

    public static Date timeToDate(String currentTime) {
        Date date = null;
        try {
            date = sdf.parse(currentTime.trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static boolean timeDiffAllow(String currentTime) {
        Date timeToDate = timeToDate(currentTime);
        try {
            if (currentTime.startsWith("13:")) {
                return (timeToDate.getTime() - time1.getTime() < timeDiffLimit);
            } else if (currentTime.startsWith("19:")) {
                return (timeToDate.getTime() - time2.getTime() < timeDiffLimit);

            } else if (currentTime.startsWith("23:")) {
                return (timeToDate.getTime() - time3.getTime() < timeDiffLimit);

            } else if (currentTime.startsWith("00:")) {
                return (timeToDate.getTime() - time4.getTime() < timeDiffLimit);

            } else if (currentTime.startsWith("14:")) {
                return (timeToDate.getTime() - time5.getTime() < timeDiffLimit);

            } else if (currentTime.startsWith("20:")) {
                return (timeToDate.getTime() - time6.getTime() < timeDiffLimit);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    protected static void compute(Map<String, List> map, long consumedTime, String key) {
        //value  最大耗时 最小耗时 响应次数 累计耗时
        if (map.containsKey(key)) {

            List value = map.get(key);
            long currentMaxTime = (Long) value.get(0);
            if (currentMaxTime < consumedTime) {
                value.set(0, consumedTime);
            }

            long currentMinTime = (Long) value.get(1);
            if (currentMinTime > consumedTime) {
                value.set(1, consumedTime);
            }

            //调用次数
            int currentInvokeTimes = (Integer) value.get(2);
            value.set(2, ++currentInvokeTimes);

            //累计耗时
            value.set(3, (Long) value.get(3) + consumedTime);

            map.put(key, value);
        } else {
            List datas = new ArrayList();
            datas.add(0, consumedTime);//最大耗时
            datas.add(1, consumedTime);//最小耗时
            datas.add(2, 1);//调用次数
            datas.add(3, consumedTime);//累计耗时

            map.put(key, datas);
        }
    }
}
