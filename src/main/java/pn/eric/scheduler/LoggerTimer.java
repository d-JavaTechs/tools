package pn.eric.scheduler;

import pn.eric.email.EmailUtil;
import pn.eric.shell.JavaShellUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * @author Eric on 2016-08-10
 */
public class LoggerTimer {
    public static void main(String[] args) {
        if(args!=null && "e".equals(args[0])){
            executeTask();
        }
        scheduleTask();
    }

    private static void executeTask(){
        try {
            JavaShellUtil.executeShell(JavaShellUtil.BATCHLOG);
            JavaShellUtil.executeShell(JavaShellUtil.BATCHAGGREGATION);
            EmailUtil.send();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void scheduleTask(){
        Date time  = getTime();
        String tmr = new SimpleDateFormat( "yyyy-MM-dd HH:mm:SS").format(time);
        System.out.println("schedule next task time at "+tmr);
        Timer timer = new Timer();
        timer.schedule(new TimerTaskTest(), time);
    }

    public static Date getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 00);
        return   calendar.getTime();
    }

    private static class TimerTaskTest extends java.util.TimerTask{
        public void run(){
            executeTask();
            scheduleTask();
        }
    }
}
