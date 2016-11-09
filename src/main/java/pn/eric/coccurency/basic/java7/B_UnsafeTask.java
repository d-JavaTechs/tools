package pn.eric.coccurency.basic.java7;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by eric on 16/11/4.
 */
public class B_UnsafeTask implements Runnable {

    private Date startDate;
    @Override
    public void run() {
        startDate = new Date();

        System.out.printf("Starting Thread: %s,: %s\n", Thread.currentThread().getId(), startDate );
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.printf(" Thread finished:  %s,: %s\n", Thread.currentThread().getId(), startDate );

    }

    public static void main(String[] args) {
        B_UnsafeTask b_UnsafeTask = new B_UnsafeTask();
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
