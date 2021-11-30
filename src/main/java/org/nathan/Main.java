package org.nathan;


import java.util.function.Function;

class Main {
  public static void main(String[] args) {
    Function<Object, Object> id1 = (a) -> a;
    Function<Object, Object> id2 = (a) -> a;
    Function<Object, Object> id3 = (a) -> a;
    var res = id1.apply(id2.apply(id3));

    System.out.println(res == id3);
  }
}


