package pn.eric.coccurency.basic.java7;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by eric on 16/11/4.
 */
public class B_SafeTask implements Runnable {

    private static ThreadLocal<Date> startDate = new ThreadLocal<Date>(){
        protected Date initialValue(){
            return new Date();
        }
    };
    @Override
    public void run() {

        System.out.printf("Starting Thread: %s,: %s\n", Thread.currentThread().getId(), startDate.get() );
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.printf(" Thread finished:  %s,: %s\n", Thread.currentThread().getId(), startDate.get() );

    }

    public static void main(String[] args) {
        B_SafeTask b_UnsafeTask = new B_SafeTask();
        for (int i=0;i<10;i++){
            Thread thread = new Thread(b_UnsafeTask);
            thread.start();
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
