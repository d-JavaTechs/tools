package pn.eric.coccurency.basic.java7;

import pn.eric.db.reverse.Main;

/**
 * Created by eric on 16/11/4.
 */
public class A_Calculator implements Runnable{

    private int number;

    public A_Calculator(int number){

        this.number = number;

    }
    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.printf("%s: %d*%d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            A_Calculator a_Calculator = new A_Calculator(i);
            Thread thread = new Thread(a_Calculator);
            thread.start();
        }

    }
}
