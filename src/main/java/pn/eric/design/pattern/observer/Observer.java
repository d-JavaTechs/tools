package pn.eric.design.pattern.observer;

/**
 * @author Shadow
 * @date
 */
public class Observer implements IObsever {
    @Override
    public void getSubjectInfo(String msg) {
        System.out.println(msg);
    }
}
