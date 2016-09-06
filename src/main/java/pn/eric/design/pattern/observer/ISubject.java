package pn.eric.design.pattern.observer;

/**
 * @author Shadow
 * @date 2016/9/6 21:32
 */
public interface ISubject {
    void registerObserver(IObsever Observer);
    void notifyObserver(String msg);
}
