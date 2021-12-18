package org.nathan.centralibj.algsJ.natch;

import org.junit.jupiter.api.Test;
import org.nathan.centralibj.algsJ.strMatch.KMP;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KMPTest{

  @Test
  void searchTest(){
    var res = KMP.search("ABAAAABAAAAAAAAA", "BAAAAAAAAA");
    assertEquals(1, res.size());
    assertEquals(6, res.get(0));

    var res1 = KMP.search("abaabbcaa", "ab");
    assertEquals(2, res1.size());
    assertEquals(0, res1.get(0));

    var res2 = KMP.search("abaabbcaa", "ba");
    assertEquals(1, res2.size());
    assertEquals(1, res2.get(0));

    var res3 = KMP.search("abaabbcaa", "a");
    assertEquals(5, res3.size());
    assertEquals(0, res3.get(0));

    var res4 = KMP.search("abaabbcaa", "abaabbcaa");
    assertEquals(1, res4.size());
    assertEquals(0, res4.get(0));

    var pi = KMP.computePrefixFunction("ababaca");
    System.out.println(Arrays.toString(pi));
  }
}