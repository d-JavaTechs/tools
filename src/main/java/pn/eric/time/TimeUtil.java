package pn.eric.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Eric
 */
public class TimeUtil {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd");
    public static String getTime(int dayCount){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, dayCount);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        return simpleDateFormat.format(calendar.getTime());
    }
}
