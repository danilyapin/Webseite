package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        List<Integer> linkedList = new ArrayList<>();
        List<Integer> arrayList = new ArrayList<>();

        measureTime(linkedList);
        measureTime(arrayList);
    }

    private static void measureTime(List<Integer> list){
        long start = System.currentTimeMillis();

        for(int i = 0; i < 100000; i++){
            list.add(i);
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
