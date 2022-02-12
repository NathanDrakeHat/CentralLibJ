package org.qhc99.centralibj.algsJ.misc;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Huffman{

  static class KNode{
    double weight;
    String sym;
    final KNode[] children;

    KNode(int radix){
      sym = null;
      children = new KNode[radix];
    }

    KNode(String sym, double w){
      children = null;
      this.sym = sym;
      weight = w;
    }
  }

  public static @NotNull Map<String, String> encode(@NotNull Map<String, Double> freq, int radix){
    if(radix <= 1){
      throw new IllegalArgumentException();
    }
    var pad = freq.size() - 1 % (radix - 1);
    PriorityQueue<KNode> nodeHeap = new PriorityQueue<>(Comparator.comparing((n)->n.weight));
    for(int i = 0; i < pad; i++){
      nodeHeap.add(new KNode("pad" + pad, 0.));
    }
    for(var kv : freq.entrySet()){
      nodeHeap.add(new KNode(kv.getKey(),kv.getValue()));
    }

    while(nodeHeap.size() > 1) {
      double acc = 0;
      KNode parent = new KNode(radix);
      for(int i = 0; i < radix; i++){
        var n = nodeHeap.poll();
        acc += n.weight;
        parent.children[i] = n;
      }
      parent.weight = acc;
      nodeHeap.add(parent);
    }

    var freqTree = nodeHeap.poll();
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

    funcGetEncode.apply(freqTree, new ArrayDeque<>());
    return ans;
  }
}
