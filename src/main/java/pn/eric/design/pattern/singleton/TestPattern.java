package pn.eric.design.pattern.singleton;

/**
 * @author Shadow
 * @date
 */
public class TestPattern {
    public static void main(String[] args) {
        SingleTon singleTon = SingleTon.getSingleTon();

        SingleTon singleTon1 = SingleTon.getSingleTon();

        System.out.println(singleTon);
        System.out.println(singleTon1);


        System.out.println("---------------------------------------------");
        SingleTon_v1.SingleTonInstance singleTon_v1 = SingleTon_v1.getSingleTon();

        SingleTon_v1.SingleTonInstance singleTon_v11 = SingleTon_v1.getSingleTon();

        System.out.println(singleTon);
        System.out.println(singleTon1);


        SinglTon_Enum singleTonE1 = SinglTon_Enum.instance;

        SinglTon_Enum singleTonE2 = SinglTon_Enum.instance;



        System.out.println(singleTonE1.equals(singleTonE2));
    }
}
