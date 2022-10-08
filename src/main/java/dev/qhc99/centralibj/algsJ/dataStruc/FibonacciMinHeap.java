package dev.qhc99.centralibj.algsJ.dataStruc;


import org.jetbrains.annotations.NotNull;

import java.util.*;

// dynamic minimum priority queue
// key must be a number
public class FibonacciMinHeap<K, V>{
  private final Map<V, Node<K, V>> value_Node_map = new HashMap<>();
  private final Comparator<K> keyComparator;
  Node<K, V> rootList = null;
  int count = 0; // number of nodes

  public FibonacciMinHeap(@NotNull Comparator<K> keyComparator){
    this.keyComparator = keyComparator;
  }

  public static <V> FibonacciMinHeap<Integer, V> ofInt(){
    return new FibonacciMinHeap<>(Integer::compareTo);
  }

  public static <V> FibonacciMinHeap<Double, V> ofDouble(){
    return new FibonacciMinHeap<>(Double::compareTo);
  }

  public static <K, V> FibonacciMinHeap<K, V> union(@NotNull FibonacciMinHeap<K, V> f1, @NotNull FibonacciMinHeap<K,
          V> f2){
    var res = new FibonacciMinHeap<K, V>(f1.keyComparator);
    res.rootList = f1.rootList;
    Objects.requireNonNull(f1.rootList);
    Objects.requireNonNull(f2.rootList);
    var f1_right = f1.rootList.right; // concatenate two root list
    var f2_left = f2.rootList.left;
    f1.rootList.right = f2.rootList;
    f2.rootList.left = f1.rootList;
    f1_right.left = f2_left;
    f2_left.right = f1_right;

    if(f1.keyComparator.compare(f2.rootList.key, f1.rootList.key) < 0){
      res.rootList = f2.rootList;
    }
    res.count = f1.count + f2.count;
    return res;
  }

  private static <W, T> void addNodeToList(Node<W, T> x, Node<W, T> list){
    x.parent = list.parent;
    var list_left = list.left;
    list.left = x;
    x.right = list;
    list_left.right = x;
    x.left = list_left;
  }

  private static <W, T> void removeNodeFromList(Node<W, T> z){
    var z_right = z.right;
    var z_left = z.left;
    if(z.parent != null){
      if(z.parent.childList == z){
        if(z.right != z){
          z.parent.childList = z.right;
          z.right.parent = z.parent;
        }
        else{
          z.parent.childList = null;
          z.right = z;
          z.left = z;
          z.parent = null;
          return;
        }
      }
    }
    z_right.left = z_left;
    z_left.right = z_right;

    z.right = z;
    z.left = z;
    z.parent = null;
  }

  private static <W, T> void linkTo(Node<W, T> l, Node<W, T> m){ // larger, minor
    removeNodeFromList(l);
    m.degree++;
    if(m.childList == null){
      m.childList = l;
      l.parent = m;
    }
    else{
      addNodeToList(l, m.childList);
    }
    l.mark = false;
  }

  public K minKey(){
    return rootList.key;
  }

  public V minValue(){
    return rootList.value;
  }

  private void insert(Node<K, V> x){
    count++;
    value_Node_map.put(x.value, x);
    if(rootList == null){
      rootList = x;
    }
    else{
      addNodeToList(x, rootList); // add x to root list
      if(keyComparator.compare(x.key, rootList.key) < 0){
        rootList = x;
      }
    }
  }

  public void insert(K key, V val){
    insert(new Node<>(key, val));
  }

  public V extractMin(){
    if(count <= 0){
      throw new IllegalStateException();
    }
    var z = rootList;
    if(z != null){
      var child_list = z.childList; // add root_list's children list to root list
      var p = child_list;
      if(p != null){
        do{
          var t = p.right;
          p.parent = null;
          addNodeToList(p, rootList);
          p = t;
        }
        while(p != child_list);
      }
      rootList = z.right;
      removeNodeFromList(z);
      if(z == rootList){
        rootList = null;
      }
      else{
        consolidate();
      }
      count--;
    }
    else{
      throw new NoSuchElementException();
    }
    value_Node_map.remove(z.value);
    return z.value;
  }

  private void consolidate(){
    List<Node<K, V>> A = new ArrayList<>();
    int len = upperBound() + 1;
    for(int i = 0; i < len; i++){
      A.add(null);
    }
    var w = rootList;
    if(w == null){
      return;
    }
    var dict = new HashSet<Node<K, V>>();
    do{ // for w in root list start
      var x = w; // x current node
      var next = x.right;
      int d = x.degree;
      while(A.get(d) != null) {
        var y = A.get(d); // y stored node
        if(keyComparator.compare(x.key, y.key) > 0){ // exchange pointer
          var t = x;
          //noinspection SuspiciousNameCombination
          x = y;
          y = t;
        }
        linkTo(y, x);
        A.set(d, null);
        d++;
      }
      A.set(d, x);
      // for w in root list end
      dict.add(w);
      w = next;
    }
    while(!dict.contains(w));
    rootList = null;
    for(int i = 0; i <= upperBound(); i++){
      var t = A.get(i);
      if(t != null){
        if(rootList == null){
          t.right = t;
          t.left = t;
          rootList = t;
        }
        else{
          addNodeToList(t, rootList);
          if(keyComparator.compare(t.key, rootList.key) < 0){
            rootList = t;
          }
        }
      }
    }
  }

  public void decreaseKey(@NotNull V val, K new_key){
    var x = value_Node_map.get(val);
    if(keyComparator.compare(new_key, x.key) > 0){
      throw new IllegalArgumentException("New key should smaller than old key.");
    }
    else{
      x.key = new_key;
      var y = x.parent;
      if(y != null){
        if(keyComparator.compare(x.key, y.key) < 0){
          cut(x, y);
          cascadingCut(y);
        }
      }
      if(keyComparator.compare(x.key, minKey()) <= 0){
        rootList = x;
      }
    }
  }

  void decreaseKey(Node<K, V> x, K new_key){
    // move x to root list
    // set parent mark true if parent mark is false
    // else successively move true mark parents to root list
    x.key = new_key;
    var y = x.parent;
    if(y != null){
      if(keyComparator.compare(x.key, y.key) < 0){
        cut(x, y);
        cascadingCut(y);
      }
    }
    if(keyComparator.compare(x.key, minKey()) <= 0){
      rootList = x;
    }
  }

  private void cut(Node<K, V> a, Node<K, V> b){
    removeNodeFromList(a);
    b.degree--;
    addNodeToList(a, rootList);
    a.mark = false;
  }

  private void cascadingCut(Node<K, V> y){
    var z = y.parent;
    if(z != null){
      if(!y.mark){
        y.mark = true;
      }
      else{
        cut(y, z);
        cascadingCut(z);
      }
    }
  }

  private void delete(Node<K, V> x){
    decreaseKey(x, minKey());
    extractMin();
  }

  public boolean contains(V x){
    return value_Node_map.containsKey(x);
  }

  public int count(){
    return count;
  }

  private int upperBound(){
    return (int) (Math.log(count) / Math.log(2));
  }

  static class Node<W, T>{
    W key;
    T value;
    Node<W, T> parent = null;
    Node<W, T> childList = null; // dual direction linked, circular list
    Node<W, T> left;
    Node<W, T> right;
    int degree = 0; // number of children
    boolean mark = false; // whether the node had lost a child when it be made another node's child

    Node(W key, T val){
      this.key = key;
      this.value = val;
      left = this;
      right = this;
    }

    @Override
    public String toString(){
      return String.format("key: %s", key.toString());
    }
  }

}