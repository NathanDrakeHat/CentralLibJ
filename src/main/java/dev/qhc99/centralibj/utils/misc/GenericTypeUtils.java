package dev.qhc99.centralibj.utils.misc;

import java.util.Arrays;

public class GenericTypeUtils {
    /**
     * guava type token string processing
     * 
     * @param type_string guava type token string
     * @return human readable string
     */
    @SuppressWarnings("unused")
    private static String processRawTypeName(String type_string) {
        var origin_types = Arrays.stream(type_string.split("[<>, ]")).filter(s -> !s.equals("")).distinct().toList();
        for (var origin_type : origin_types) {
            var split = origin_type.split("\\.");
            type_string = type_string.replaceAll(origin_type, split[split.length - 1]);
        }
        return type_string;
    }
}
