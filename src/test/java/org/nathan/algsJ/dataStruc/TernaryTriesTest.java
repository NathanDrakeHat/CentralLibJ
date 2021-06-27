package org.nathan.algsJ.dataStruc;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TernaryTriesTest{

  TernaryTries<Byte> case1 = new TernaryTries<>();

  {
    case1.put("by", (byte) 0);
    case1.put("she", (byte) 0);
    case1.put("shells", (byte) 0);
    case1.put("sea", (byte) 0);
    case1.put("sells", (byte) 0);
    case1.put("shore", (byte) 0);
    case1.put("the", (byte) 0);
  }

  Set<String> keys = new HashSet<>();

  {
    keys.add("by");
    keys.add("she");
    keys.add("shells");
    keys.add("sea");
    keys.add("sells");
    keys.add("shore");
    keys.add("the");

  }

  @Test
  void longestPrefixOfTest(){
    assertEquals("she", case1.longestPrefixOf("shell"));
    assertEquals("shells", case1.longestPrefixOf("shellsort"));
  }

  @Test
  void keysTest(){
    var ks = case1.keys();
    for(var k : ks){
      assertTrue(keys.contains(k));
    }
  }


  @Test
  void keysWithPrefixTest(){
    var d = new ArrayDeque<>();
    d.add("she");
    d.add("shells");
    d.add("shore");
    var res = case1.keysWithPrefix("sh");
    for(var i : res){
      assertTrue(d.contains(i));
    }
  }
}