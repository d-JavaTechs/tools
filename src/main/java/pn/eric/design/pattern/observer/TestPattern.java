package pn.eric.design.pattern.observer;

/**
 * @author Shadow
 * @date
 */
public class TestPattern {
    public static void main(String[] args) {

        IObsever obsever = new Observer();
        IObsever obsever1 = new Observer();


        ISubject subject = new Subject();


        subject.registerObserver(obsever);
        subject.registerObserver(obsever1);


        subject.notifyObserver("changed");
    }
}
