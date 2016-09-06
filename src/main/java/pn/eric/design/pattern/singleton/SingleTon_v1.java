package pn.eric.design.pattern.singleton;

/**
 * @author Shadow
 * @date
 */
public class SingleTon_v1 {

        public static class SingleTonInstance{
            private static final SingleTonInstance singleTon = new SingleTonInstance();
        }

    private SingleTon_v1(){}

    public static SingleTonInstance getSingleTon(){
        return SingleTonInstance.singleTon;
    }
}
