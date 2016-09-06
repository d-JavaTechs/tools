package pn.eric.design.pattern.observer;

import java.util.*;

/**
 * @author Shadow
 * @date 2016/9/6 21:32
 */
public class Subject implements  ISubject{

    Set list  = new HashSet<Observer>();
    @Override
    public void registerObserver(IObsever observer) {
        list.add(observer);
    }

    @Override
    public void notifyObserver(String msg) {
        Iterator it = list.iterator();
        while (it.hasNext()){
            Observer observer = (Observer)it.next();
            observer.getSubjectInfo(this.getClass().getName() + "：  "+msg);
        }
    }
}
