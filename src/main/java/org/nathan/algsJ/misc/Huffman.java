package org.nathan.algsJ.misc;

import org.jetbrains.annotations.NotNull;
import org.nathan.algsJ.dataStruc.ExtremumHeap;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Huffman{

  static class KNode{
    String sym;
    final KNode[] children;

    KNode(int radix){
      sym = null;
      children = new KNode[radix];
    }

    KNode(String sym){
      children = null;
      this.sym = sym;
    }
  }

  public static @NotNull Map<String, String> encode(@NotNull Map<String, Double> freq, int radix){
    if(radix <= 1){
      throw new IllegalArgumentException();
    }
    var pad = freq.size() - 1 % (radix - 1);
    ExtremumHeap<Double, KNode> freqSymHeap = new ExtremumHeap<>(true, Double::compare);
    for(int i = 0; i < pad; i++){
      freqSymHeap.add(new KNode("pad" + pad), 0.);
    }
    for(var kv : freq.entrySet()){
      freqSymHeap.add(new KNode(kv.getKey()), kv.getValue());
    }

    while(freqSymHeap.heapSize() > 1) {
      double acc = 0;
      KNode parent = new KNode(radix);
      for(int i = 0; i < radix; i++){
        var freqSym = freqSymHeap.extractExtremum();
        acc += freqSym.first();
        parent.children[i] = freqSym.second();
      }
      freqSymHeap.add(parent, acc);
    }

    var freqTree = freqSymHeap.extractExtremum();
    Map<String, String> ans = new HashMap<>();
    var funcGetEncode = new Object(){
      void apply(KNode node, Deque<String> strings){
        if(node.children != null){
          for(int i = 0; i < node.children.length; i++){
            strings.addLast(String.valueOf(i));
            strings.addLast(",");
            apply(node.children[i], strings);
            strings.removeLast();
            strings.removeLast();
          }
        }
        else{
          var b = new StringBuilder();
          for(var s : strings){
            b.append(s);
          }
          b.delete(b.length() - 1, b.length());
          if(!node.sym.startsWith("pad")){
            ans.put(node.sym, b.toString());
          }
        }
      }
    };

    funcGetEncode.apply(freqTree.second(), new ArrayDeque<>());
    return ans;
  }
}
