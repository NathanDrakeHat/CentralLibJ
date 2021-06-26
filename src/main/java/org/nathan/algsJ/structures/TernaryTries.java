
package org.nathan.algsJ.structures;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;

public class TernaryTries<Value>{
  private int n;              
  private Node<Value> root;   

  private static class Node<Value>{
    private char c;                        
    private Node<Value> left, mid, right;  
    private Value val;                     
  }

  public int size(){
    return n;
  }

  public boolean contains(@NotNull String key){
    return get(key) != null;
  }
  
  public Value get(@NotNull String key){
    if(key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
    Node<Value> x = get(root, key, 0);
    if(x == null) return null;
    return x.val;
  }

  private Node<Value> get(Node<Value> x, String key, int d){
    if(x == null) return null;
    if(key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
    char c = key.charAt(d);
    if(c < x.c) return get(x.left, key, d);
    else if(c > x.c) return get(x.right, key, d);
    else if(d < key.length() - 1) return get(x.mid, key, d + 1);
    else return x;
  }
  
  public void put(@NotNull String key, Value val){
    if(!contains(key)) n++;
    else if(val == null) n--;       
    root = put(root, key, val, 0);
  }

  private Node<Value> put(Node<Value> x, String key, Value val, int d){
    char c = key.charAt(d);
    if(x == null){
      x = new Node<>();
      x.c = c;
    }
    if(c < x.c) x.left = put(x.left, key, val, d);
    else if(c > x.c) x.right = put(x.right, key, val, d);
    else if(d < key.length() - 1) x.mid = put(x.mid, key, val, d + 1);
    else x.val = val;
    return x;
  }
  
  public String longestPrefixOf(@NotNull String query){
    if(query.length() == 0) return null;
    int length = 0;
    Node<Value> x = root;
    int i = 0;
    while(x != null && i < query.length()) {
      char c = query.charAt(i);
      if(c < x.c) x = x.left;
      else if(c > x.c) x = x.right;
      else{
        i++;
        if(x.val != null) length = i;
        x = x.mid;
      }
    }
    return query.substring(0, length);
  }
  
  public Iterable<String> keys(){
    Queue<String> queue = new ArrayDeque<>();
    collect(root, new StringBuilder(), queue);
    return queue;
  }
  
  public Iterable<String> keysWithPrefix(@NotNull String prefix){
    Queue<String> queue = new ArrayDeque<>();
    Node<Value> x = get(root, prefix, 0);
    if(x == null) return queue;
    if(x.val != null) queue.add(prefix);
    collect(x.mid, new StringBuilder(prefix), queue);
    return queue;
  }

  private void collect(Node<Value> x, StringBuilder prefix, Queue<String> queue){
    if(x == null) return;
    collect(x.left, prefix, queue);
    if(x.val != null) queue.add(prefix.toString() + x.c);
    collect(x.mid, prefix.append(x.c), queue);
    prefix.deleteCharAt(prefix.length() - 1);
    collect(x.right, prefix, queue);
  }
  
  public Iterable<String> keysThatMatch(String pattern){
    Queue<String> queue = new ArrayDeque<>();
    collect(root, new StringBuilder(), 0, pattern, queue);
    return queue;
  }

  private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> queue){
    if(x == null) return;
    char c = pattern.charAt(i);
    if(c == '.' || c < x.c) collect(x.left, prefix, i, pattern, queue);
    if(c == '.' || c == x.c){
      if(i == pattern.length() - 1 && x.val != null) queue.add(prefix.toString() + x.c);
      if(i < pattern.length() - 1){
        collect(x.mid, prefix.append(x.c), i + 1, pattern, queue);
        prefix.deleteCharAt(prefix.length() - 1);
      }
    }
    if(c == '.' || c > x.c) collect(x.right, prefix, i, pattern, queue);
  }
}
