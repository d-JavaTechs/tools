package pn.eric.instrment;

/**
 * Created by eric on 16/9/15.
 */
public class TestSize {
    public static void main(String []args) {
        System.out.println(MySizeOf.sizeOf(new Integer(1)));
        System.out.println(MySizeOf.sizeOf(new String("a")));
        System.out.println(MySizeOf.fullSizeOf(new String("a")));
        System.out.println(MySizeOf.sizeOf(new char[1]));
    }
}
