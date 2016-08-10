package pn.eric.scheduler;

import pn.eric.email.Sendmail;
import pn.eric.shell.JavaShellUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * @author Eric on
 * @date
 */
public class LoggerTimer {
    public static void main(String[] args) {
        Date time  = getTime();
        String tmr = new SimpleDateFormat( "yyyy-MM-dd").format(time);
        System.out.println("schedule next task time at "+tmr);


        Timer timer = new Timer();
        timer.schedule(new TimerTaskTest(), time);
    }

    public static Date getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        return   calendar.getTime();
    }
    static class TimerTaskTest extends java.util.TimerTask{
        public void run(){
            try {
                new JavaShellUtil().executeShell(JavaShellUtil.BATCHLOG);
                new JavaShellUtil().executeShell(JavaShellUtil.BATCHAGGREGATION);
                Sendmail.send();
                Date time = getTime();
                String tmr = new SimpleDateFormat( "yyyy-MM-dd").format(time);
                System.out.println("schedule next task time at "+tmr);


                Timer timer = new Timer();
                timer.schedule(new TimerTaskTest(), time);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
