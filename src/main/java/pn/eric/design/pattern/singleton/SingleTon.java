package pn.eric.design.pattern.singleton;

/**
 * @author Shadow
 * @date
 */
public class SingleTon {

        private static class SingleTonInstance{
            private static final SingleTon singleTon = new SingleTon();
        }

    private SingleTon(){}

    public static SingleTon getSingleTon(){
        return SingleTonInstance.singleTon;
    }
}
