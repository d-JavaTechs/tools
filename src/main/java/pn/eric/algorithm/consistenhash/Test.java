package pn.eric.algorithm.consistenhash;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by eric on 16/10/23.
 */
public class Test {
    public static void main(String[] args) {

        Set<String> nodes = new HashSet<String>();
        nodes.add("0");
        nodes.add("1");
        //nodes.add("C");
        ConsistentHash<String> consistentHash = new ConsistentHash<String>(new HashFunction(), 160, nodes);

        //consistentHash.add("D");
        System.out.println(consistentHash.getSize());  //640

        System.out.println(consistentHash.get("0"));
        System.out.println(consistentHash.get("1"));
        System.out.println(consistentHash.get("2"));
        System.out.println(consistentHash.get("3"));
        System.out.println(consistentHash.get("4"));
        System.out.println(consistentHash.get("5"));
        System.out.println(consistentHash.get("6"));
        System.out.println(consistentHash.get("7"));
        System.out.println(consistentHash.get("8"));
        System.out.println(consistentHash.get("9"));
    }
}
