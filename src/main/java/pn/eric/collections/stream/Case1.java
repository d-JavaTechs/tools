package pn.eric.collections.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Shadow
 * @date
 */
public class Case1 {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("a","b","c");


        String[] strArray = new String[]{"a","b","c"};

        stream = Stream.of(strArray);


        stream = Arrays.stream(strArray);

        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();




        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);


        try{
            // 1. Array
            String[] strArray1 = stream.toArray(String[]::new);

            // 2. Collection
            List<String> list1 = stream.collect(Collectors.toList());
            List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
            Set set1 = stream.collect(Collectors.toSet());
            Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));

            // 3. String
            String str = stream.collect(Collectors.joining()).toString();

        }catch (Exception e){

        }

    }
}
