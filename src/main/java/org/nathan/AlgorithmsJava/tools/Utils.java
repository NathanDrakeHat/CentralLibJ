package org.nathan.AlgorithmsJava.tools;

import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

    public static List<Integer> randomIntegerList(int low, int high, int len) {
        return Arrays.stream(randomIntArray(low, high, len)).boxed().collect(Collectors.toList());
    }

    public static <T> void serializeObjectTimeSuffix(T t, String prefix, String type_suffix, Type... types) throws IOException {
        StringBuilder file_name = new StringBuilder(prefix);
        file_name.append(types[0].getTypeName());
        if (types.length > 1) {
            file_name.append("<");
            for (int i = 1; i < types.length - 1; i++) {
                file_name.append(types[i].getTypeName());
                file_name.append(",");
            }
            file_name.append(types[types.length - 1].getTypeName());
            file_name.append(">");
        }
        file_name.append("|");
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime now = LocalDateTime.now();
        file_name.append(dtf.format(now));
        file_name.append(type_suffix);
        FileOutputStream file_out = new FileOutputStream(file_name.toString());
        ObjectOutputStream out = new ObjectOutputStream(file_out);
        out.writeObject(t);
        out.flush();
        out.close();
    }

    public static Object loadObject(String file_name) {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object res = null;
        try {
            //noinspection ConstantConditions
            res = in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException ignore) {
        }
        return res;
    }

    public static String TYPE_SUFFIX = ".txt";

    /**
     * usage: GenericClass<Type> v = newGenericArray(len);
     * @param length len
     * @param array array
     * @param <E> any
     * @return typed array
     */
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
     * @param typeToken Gson class
     * @param <T> any
     * @return readable type name
     */
    public static <T> String getReadableTypeName(TypeToken<T> typeToken){
        return processRawTypeName(typeToken.toString());
    }

    private static String processRawTypeName(String type_string){
        var origin_types = Arrays.stream(type_string.split("[<>, ]")).filter(s->!s.equals("")).distinct().collect(Collectors.toList());
        for(var origin_type : origin_types){
            var split = origin_type.split("\\.");
            type_string = type_string.replaceAll(origin_type, split[split.length - 1]);
        }
        return type_string;
    }
}