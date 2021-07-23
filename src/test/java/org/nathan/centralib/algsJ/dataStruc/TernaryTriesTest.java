package org.nathan.centralib.algsJ.dataStruc;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TernaryTriesTest{

  TernaryTries<Byte> case1 = new TernaryTries<>();

  {
    case1.put("by", null);
    case1.put("she", null);
    case1.put("shells", null);
    case1.put("sea", null);
    case1.put("sells", null);
    case1.put("shore", null);
    case1.put("the", null);
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

  @Test
  void removeTest(){
    TernaryTries<Byte> data = new TernaryTries<>();
    data.put("shells", null);
    data.put("by", null);
    data.put("the", null);
    data.put("sells", null);
    data.put("shore", null);
    data.put("she", null);
    data.put("sea", null);
    assertEquals(7, data.size());


    assertTrue(data.remove("by"));
    assertFalse(data.contains("by"));
    assertTrue(data.contains("she"));
    assertTrue(data.contains("shells"));
    assertTrue(data.contains("sea"));
    assertTrue(data.contains("sells"));
    assertTrue(data.contains("shore"));
    assertTrue(data.contains("the"));
    assertEquals(6, data.size());

    assertTrue(data.remove("she"));
    assertFalse(data.contains("she"));
    assertTrue(data.contains("shells"));
    assertTrue(data.contains("sea"));
    assertTrue(data.contains("sells"));
    assertTrue(data.contains("shore"));
    assertTrue(data.contains("the"));
    assertEquals(5, data.size());

    assertTrue(data.remove("shells"));
    assertFalse(data.contains("shells"));
    assertTrue(data.contains("sea"));
    assertTrue(data.contains("sells"));
    assertTrue(data.contains("shore"));
    assertTrue(data.contains("the"));
    assertEquals(4, data.size());

    assertFalse(data.remove("aaaa"));

    assertTrue(data.remove("sea"));
    assertFalse(data.contains("sea"));
    assertTrue(data.contains("sells"));
    assertTrue(data.contains("shore"));
    assertTrue(data.contains("the"));
    assertEquals(3, data.size());

    assertTrue(data.remove("sells"));
    assertFalse(data.contains("sells"));
    assertTrue(data.contains("shore"));
    assertTrue(data.contains("the"));
    assertEquals(2, data.size());

    assertTrue(data.remove("shore"));
    assertFalse(data.contains("shore"));
    assertTrue(data.contains("the"));
    assertEquals(1, data.size());

    assertTrue(data.remove("the"));
    assertFalse(data.contains("the"));

    assertNull(data.root);
    assertEquals(0, data.size());
  }
}