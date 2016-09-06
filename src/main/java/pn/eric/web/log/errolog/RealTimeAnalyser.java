package pn.eric.web.log.errolog;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Eric on 2016-08-16
 */
public class RealTimeAnalyser {
    public static void main(String args[]) throws Exception{

        Calendar cal  =  Calendar.getInstance();
        String today = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
        System.out.println(today);

        TailLogThread threadM = new TailLogThread(String.format("/home/weihu/production-logs/SZ-SERVICE-M/ksservices/detail-ksservices.%s.log",today));
        threadM.setName("SZ-SERVICE-M-detail-ksservices");
        threadM.start();

        Thread.sleep(1000);

        TailLogThread threadS = new TailLogThread(String.format("/home/weihu/production-logs/SZ-SERVICE-S/ksservices/detail-ksservices.%s.log",today));
        threadS.setName("SZ-SERVICE-S-detail-ksservices");
        threadS.start();

        Thread.sleep(1000);

        TailLogThread threadGray = new TailLogThread(String.format("/home/weihu/production-logs/gray/ksservices/detail-ksservices.%s.log",today));
        threadGray.setName("SZ-SERVICE-GRAY-ksservices");
        threadGray.start();


    }

}
