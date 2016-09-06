package pn.eric.design.pattern.callback;

public class A {

    private final Callback callback;

    public static interface Callback {
        public void begin();
        public void end();
    }

    public A(Callback callback) {
        this.callback = callback;
    }


    public void doIt() {

        callback.begin();

        System.out.println("A do something ...");

        callback.end();
    }
}