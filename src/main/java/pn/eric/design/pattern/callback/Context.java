package pn.eric.design.pattern.callback;

public class Context implements A.Callback {

    private A a;

    public void begin() {
        System.out.println("Context Callback implementation do begin ...");
    }

    public void end() {
        System.out.println("Context Callback implementation do end ...");
    }


    public Context() {
        this.a = new A(this);
    }



    public void doSomething() {
        this.a.doIt();
    }

    public static void main(String args[]) {
        new Context().doSomething();
    }
}