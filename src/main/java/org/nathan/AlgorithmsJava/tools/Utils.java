package org.nathan.AlgorithmsJava.tools;

import com.google.common.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static int[] randomIntArray(int low, int high, int len) {
        var rand = new Random();
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = rand.nextInt(high - low) + low;
        }
        return res;
    }

    public static double[] randomDoubleArray(double low, double high, int len) {
        var rand = new Random();
        double[] res = new double[len];
        for (int i = 0; i < len; i++) {
            res[i] = rand.nextDouble() * (high - low) + low;
        }
        return res;
    }

    public static List<Integer> shuffledSequence(int low, int high) {
        List<Integer> l = new ArrayList<>();
        for (int i = low; i < high; i++) {
            l.add(i);
        }
        Collections.shuffle(l);
        return l;
    }

    @SuppressWarnings("unused")
    public static void serializeToFile(Object object, String fullName) {
        try (var file_out = new FileOutputStream(fullName);
             var out = new ObjectOutputStream(file_out)) {
            out.writeObject(object);
            out.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    public static Object deserializeFile(String fullName) {
        try (var in = new ObjectInputStream(new FileInputStream(fullName))) {
            return in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * usage: GenericClass<Type> v = newGenericArray(len);
     *
     * @param length len
     * @param array  array
     * @param <E>    any
     * @return typed array
     */
    @SuppressWarnings("unused")
    @SafeVarargs
    public static <E> E[] newGenericArray(int length, E... array) {
        return Arrays.copyOf(array, length);
    }

    /**
     * TypeToken should be initialized as anonymous class (note the curly braces initialization):
     * <pre>
     *     var typeToken = new TypeToken<HashMap<List<String>, List<Integer>>>(){};
     * </pre>
     * readable type example:
     * <pre>
     *     java.utils.ArrayList<Integer> -> ArrayList<Integer>
     * </pre>
     *
     * @param typeToken Gson class
     * @param <T>       any
     * @return readable type name
     */
    @SuppressWarnings("UnstableApiUsage")
    public static <T> String getReadableTypeName(@NotNull TypeToken<T> typeToken) {
        return processRawTypeName(typeToken.toString());
    }

    private static String processRawTypeName(String type_string) {
        var origin_types =
                Arrays.stream(type_string.split("[<>, ]")).filter(s -> !s.equals("")).distinct().collect(Collectors.toList());
        for (var origin_type : origin_types) {
            var split = origin_type.split("\\.");
            type_string = type_string.replaceAll(origin_type, split[split.length - 1]);
        }
        return type_string;
    }
}