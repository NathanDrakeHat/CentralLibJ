package org.nathan.AlgorithmsJava.tools;

import java.util.Arrays;

public class Functions {
    //usage: GenericClass<Type> v = newGenericArray(len);
    @SafeVarargs
    static <E> E[] newGenericArray(int length, E... array) {
        return Arrays.copyOf(array, length);
    }
}
