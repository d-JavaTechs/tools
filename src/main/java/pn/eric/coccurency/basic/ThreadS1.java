package pn.eric.coccurency.basic;

/**
 * @author Shadow
 * @date
 */
public class ThreadS1  extends  Thread {
    @Override
    public void run() {
        for (int i=0;i<50;i++){
            System.out.println("thread name: " + Thread.currentThread().getName() + "-->" + i);
        }
    }


    public static void main(String[] args) {
        Thread thread = new ThreadS1();
        thread.start();
        thread = new ThreadS1();
        thread.start();
        thread = new ThreadS1();
        thread.start();
    }
}
