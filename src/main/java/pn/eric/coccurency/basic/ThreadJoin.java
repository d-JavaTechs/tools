package pn.eric.coccurency.basic;

/**
 * @author Shadow
 * @date
 */
public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ThreadTesterA());
        Thread t2 = new Thread(new ThreadTesterB());
        t1.start();
        t1.join(); // wait t1 to be finished
        System.out.print("invode t2");
        t2.start();
        t2.join(); // in this program, this may be removed
    }
}
class ThreadTesterA implements Runnable {

    private int counter;

    @Override
    public void run() {
        while (counter <= 10) {
            System.out.println("A Counter = " + counter + " ");
            counter++;
        }
        System.out.println();
    }
}


class ThreadTesterB implements Runnable {

    private int i;

    @Override
    public void run() {
        while (i <= 10) {
            System.out.println("B i = " + i + " ");
            i++;
        }
        System.out.println();
    }
}
