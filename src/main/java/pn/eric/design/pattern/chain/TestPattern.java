package pn.eric.design.pattern.chain;

import pn.eric.design.pattern.observer.IObsever;
import pn.eric.design.pattern.observer.ISubject;
import pn.eric.design.pattern.observer.Observer;
import pn.eric.design.pattern.observer.Subject;

/**
 * @author Shadow
 * @date
 */
public class TestPattern {
    public static void main(String[] args) {
        Chain chain = new Chain("chain");
        Chain chain1 = new Chain("chain1");
        Chain chain2 = new Chain("chain2");

        chain.setChain(chain1);
        chain1.setChain(chain2);

        chain.doJob();
    }
}
