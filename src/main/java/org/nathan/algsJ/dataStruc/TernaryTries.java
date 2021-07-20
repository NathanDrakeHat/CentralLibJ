package org.nathan.algsJ.dataStruc;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;

public class TernaryTries<Value>{
  private int n;
  Node<Value> root;

  public static class Node<Value>{
    private char c;
    private boolean contain;
    private Node<Value> left, mid, right;
    private Value val;

    public char getChar(){
      return c;
    }

    @Override
    public String toString(){
      return "Node{" +
              "char=" + c +
              ", contain=" + contain +
              ", val=" + val +
              '}';
    }

    public boolean isContain(){
      return contain;
    }

    public Node<Value> getLeft(){
      return left;
    }

    public Node<Value> getMid(){
      return mid;
    }

    public Node<Value> getRight(){
      return right;
    }

    public Value getVal(){
      return val;
    }
  }

  public Node<Value> getRoot(){
    return root;
  }

  public int size(){
    return n;
  }

  public boolean contains(@NotNull String key){
    var n = get(root, key, 0);
    return n != null && n.contain;
  }

  public Value get(@NotNull String key){
    if(key.length() == 0){ throw new IllegalArgumentException("key must have length >= 1"); }
    Node<Value> x = get(root, key, 0);
    if(x == null){ return null; }
    return x.val;
  }

  private Node<Value> get(Node<Value> x, String key, int d){
    if(x == null){ return null; }
    if(key.length() == 0){ throw new IllegalArgumentException("key must have length >= 1"); }
    char c = key.charAt(d);
    if(c < x.c){ return get(x.left, key, d); }
    else if(c > x.c){ return get(x.right, key, d); }
    else if(d < key.length() - 1){ return get(x.mid, key, d + 1); }
    else{ return x; }
  }

  private enum Children{
    LEFT, MID, RIGHT
  }

  public boolean remove(@NotNull String key){
    if(key.length() == 0){ throw new IllegalArgumentException("key must have length >= 1"); }
    var ret = remove(root, null, null, key, 0);
    if(ret){
      n--;
    }
    return ret;
  }

  private boolean remove(Node<Value> n, Node<Value> p, Children direct, String key, int d){
    if(n == null){ return false; }
    char c = key.charAt(d);
    if(c < n.c){
      var ret = remove(n.left, n, Children.LEFT, key, d);
      removeSelf(n, p, direct);
      return ret;
    }
    else if(c > n.c){
      var ret = remove(n.right, n, Children.RIGHT, key, d);
      removeSelf(n, p, direct);
      return ret;
    }
    else if(d < key.length() - 1){
      var ret = remove(n.mid, n, Children.MID, key, d + 1);
      removeSelf(n, p, direct);
      return ret;
    }
    else if(n.contain){
      n.contain = false;
      removeSelf(n, p, direct);
      return true;
    }
    else{
      return false;
    }
  }

  private void removeSelf(Node<Value> n, Node<Value> p, Children direct){
    if(!n.contain && n.left == null && n.mid == null && n.right == null && p != null){
      switch(direct){
        case MID -> p.mid = null;
        case LEFT -> p.left = null;
        case RIGHT -> p.right = null;
      }
    }
    if(p == null && !n.contain && n.left == null && n.mid == null && n.right == null){
      root = null;
    }
  }

  public void put(@NotNull String key, Value val){
    if(!contains(key)){ n++; }
    root = put(root, key, val, 0);
  }

  private Node<Value> put(Node<Value> x, String key, Value val, int d){
    char c = key.charAt(d);
    if(x == null){
      x = new Node<>();
      x.c = c;
    }
    if(c < x.c){ x.left = put(x.left, key, val, d); }
    else if(c > x.c){ x.right = put(x.right, key, val, d); }
    else if(d < key.length() - 1){ x.mid = put(x.mid, key, val, d + 1); }
    else{
      x.val = val;
      x.contain = true;
    }
    return x;
  }

  public String longestPrefixOf(@NotNull String query){
    if(query.length() == 0){ return null; }
    int length = 0;
    Node<Value> x = root;
    int i = 0;
    while(x != null && i < query.length()) {
      char c = query.charAt(i);
      if(c < x.c){ x = x.left; }
      else if(c > x.c){ x = x.right; }
      else{
        i++;
        if(x.contain){ length = i; }
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
    if(x == null){ return queue; }
    if(x.contain){ queue.add(prefix); }
    collect(x.mid, new StringBuilder(prefix), queue);
    return queue;
  }

  private void collect(Node<Value> x, StringBuilder prefix, Queue<String> queue){
    if(x == null){ return; }
    collect(x.left, prefix, queue);
    if(x.contain){ queue.add(prefix.toString() + x.c); }
    collect(x.mid, prefix.append(x.c), queue);
    prefix.deleteCharAt(prefix.length() - 1);
    collect(x.right, prefix, queue);
  }
}
