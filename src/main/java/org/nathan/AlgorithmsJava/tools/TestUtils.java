package org.nathan.AlgorithmsJava.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TestUtils {
    public static List<Integer> shuffleOfRange(int low, int high){
        List<Integer> l = new ArrayList<>();
        for (int i = low; i < high; i++){
            l.add(i);
        }
        Collections.shuffle(l);
        return l;
    }
}
