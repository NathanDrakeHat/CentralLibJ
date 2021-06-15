package org.nathan.algorithmsJ.structures;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;
import java.util.function.BiConsumer;

public class RedBlackTree<K, V> implements Iterable<Tuple<K, V>>{
  @NotNull
  final Comparator<K> k_comparator;
  @NotNull
  final Node<K, V> sentinel = new Node<>(BLACK);
  @NotNull Node<K, V> root = sentinel;
  private boolean iterating = false;

  public RedBlackTree(@NotNull Comparator<K> k_comparator){
    this.k_comparator = k_comparator;
  }

  /**
   * 1d range search
   *
   * @param low  low
   * @param high high (inclusive)
   * @return list of key-value in range
   */
  public List<Tuple<K, V>> keyRangeSearch(@NotNull K low, @NotNull K high){
    List<Tuple<K, V>> res = new ArrayList<>();
    if(root == sentinel){
      return res;
    }
    keyRangeSearch(root, low, high, res);
    return res;
  }

  public void keyRangeSearch(Node<K, V> n, @NotNull K low, @NotNull K high, List<Tuple<K, V>> l){
    if(n == sentinel){
      return;
    }

    if(k_comparator.compare(n.key, low) > 0){
      keyRangeSearch(n.left, low, high, l);
    }

    if(k_comparator.compare(n.key, low) >= 0 && k_comparator.compare(n.key, high) <= 0){
      l.add(new Tuple<>(n.key, n.value));
    }

    if(k_comparator.compare(n.key, high) < 0){
      keyRangeSearch(n.right, low, high, l);
    }
  }

  public V getValueOfMinKey(){
    if(sentinel != root){
      return treeMinimum(root).value;
    }
    else{
      throw new NoSuchElementException("null tree");
    }
  }

  public K getMinKey(){
    if(sentinel != root){
      return treeMinimum(root).key;
    }
    else{
      throw new NoSuchElementException("null tree");
    }
  }

  public V getValueOfMaxKey(){
    if(root != sentinel){
      return treeMaximum(root).value;
    }
    else{
      throw new NoSuchElementException("null tree");
    }
  }

  public K getMaxKey(){
    if(root != sentinel){
      return treeMaximum(root).key;
    }
    else{
      throw new NoSuchElementException("null tree");
    }
  }

  private void setLeaf(Node<K, V> n){
    n.left = sentinel;
    n.right = sentinel;
  }

  public void inOrderForEach(@NotNull BiConsumer<K, V> bc){
    if(sentinel == root){
      return;
    }
    inorderTreeWalk(root, bc);
  }

  private void inorderTreeWalk(Node<K, V> n, BiConsumer<K, V> bc){
    if(n != sentinel){
      inorderTreeWalk(n.left, bc);
      bc.accept(n.key, n.value);
      inorderTreeWalk(n.right, bc);
    }
  }

  public int getCount(){
    if(root == sentinel){
      return 0;
    }
    return getCount(root);
  }

  private int getCount(Node<K, V> n){
    if(n.right != sentinel && n.left == sentinel){
      return getCount(n.right) + 1;
    }
    else if(n.right == sentinel && n.left != sentinel){
      return getCount(n.left) + 1;
    }
    else if(n.right != sentinel){
      return getCount(n.left) + getCount(n.right) + 1;
    }
    else{
      return 1;
    }
  }

  public int getHeight(){
    if(root == sentinel){
      return 1;
    }
    int height = 1;
    int left_max = getHeight(root.left, height);
    int right_max = getHeight(root.right, height);
    return Math.max(left_max, right_max);
  }

  private int getHeight(Node<K, V> n, int height){
    if(n != sentinel){
      int left_max = getHeight(n.left, height + 1);
      int right_max = getHeight(n.right, height + 1);
      return Math.max(left_max, right_max);
    }
    return height;
  }

  public void insert(@NotNull K key, V val){
    modified();
    insert(new Node<>(key, val));
  }

  @SuppressWarnings("SuspiciousNameCombination")
  private void insert(Node<K, V> z){
    var y = sentinel;
    var x = root;
    while(x != sentinel) {
      y = x;
      if(k_comparator.compare(z.key, x.key) < 0){
        x = x.left;
      }
      else{
        x = x.right;
      }
    }
    z.parent = y;
    if(y == sentinel){
      root = z;
    }
    else if(k_comparator.compare(z.key, y.key) < 0){
      y.left = z;
    }
    else{
      y.right = z;
    }
    setLeaf(z);
    z.color = RED;
    RBInsertFixUp(z);
  }

  @SuppressWarnings("SuspiciousNameCombination")
  private void RBInsertFixUp(Node<K, V> z){
    while(z.parent.color == RED) {
      if(z.parent == z.parent.parent.left){
        var y = z.parent.parent.right;
        if(y.color == RED){
          z.parent.color = BLACK;
          y.color = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        }
        else{
          if(z == z.parent.right){
            z = z.parent;
            leftRotate(z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          rightRotate(z.parent.parent);
        }
      }
      else{
        var y = z.parent.parent.left;
        if(y.color == RED){
          z.parent.color = BLACK;
          y.color = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        }
        else{
          if(z == z.parent.left){
            z = z.parent;
            rightRotate(z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          leftRotate(z.parent.parent);
        }
      }
    }
    root.color = BLACK;
  }

  public void delete(@NotNull K key){
    modified();
    if(root == sentinel){
      return;
    }
    var n = search(root, key);
    if(n != sentinel){
      delete(n);
    }
  }

  private void delete(Node<K, V> z){
    var y = z;
    var y_origin_color = y.color;
    Node<K, V> x;
    if(z.left == sentinel){
      x = z.right;
      RBTransplant(z, z.right);
    }
    else if(z.right == sentinel){
      x = z.left;
      RBTransplant(z, z.left);
    }
    else{
      y = treeMinimum(z.right);
      y_origin_color = y.color;
      x = y.right;
      if(y.parent == z){
        x.parent = y;
      }
      else{
        RBTransplant(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }
      RBTransplant(z, y);
      y.left = z.left;
      y.left.parent = y;
      y.color = z.color;
    }
    if(y_origin_color == BLACK){
      deleteFixUp(x);
    }
  }

  private void deleteFixUp(Node<K, V> x){
    while(x != root && x.color == BLACK) {
      if(x == x.parent.left){
        var w = x.parent.right;
        if(w.color == RED){
          w.color = BLACK;
          x.parent.color = RED;
          leftRotate(x.parent);
          w = x.parent.right;
        }
        if(w.left.color == BLACK && w.right.color == BLACK){
          w.color = RED;
          x = x.parent;
        }
        else{
          if(w.right.color == BLACK){
            w.left.color = BLACK;
            w.color = RED;
            rightRotate(w);
            w = x.parent.right;
          }
          w.color = x.parent.color;
          x.parent.color = BLACK;
          w.right.color = BLACK;
          leftRotate(x.parent);
          x = root;
        }
      }
      else{
        var w = x.parent.left;
        if(w.color == RED){
          w.color = BLACK;
          x.parent.color = RED;
          rightRotate(x.parent);
          w = x.parent.left;
        }
        if(w.right.color == BLACK && w.left.color == BLACK){
          w.color = RED;
          x = x.parent;
        }
        else{
          if(w.left.color == BLACK){
            w.right.color = BLACK;
            w.color = RED;
            leftRotate(w);
            w = x.parent.left;
          }
          w.color = x.parent.color;
          x.parent.color = BLACK;
          w.left.color = BLACK;
          rightRotate(x.parent);
          x = root;
        }
      }
    }
    x.color = BLACK;
  }

  private void RBTransplant(Node<K, V> u, Node<K, V> v){
    if(u.parent == sentinel){
      root = v;
    }
    else if(u == u.parent.left){
      u.parent.left = v;
    }
    else{
      u.parent.right = v;
    }
    v.parent = u.parent;
  }

  public V search(@NotNull K key){
    if(root == sentinel){
      throw new NoSuchElementException();
    }
    var res = search(root, key).value;
    if(res == sentinel){
      throw new NoSuchElementException();
    }
    return res;
  }

  private Node<K, V> search(Node<K, V> n, K key){
    if(k_comparator.compare(n.key, key) == 0){
      return n;
    }
    else if(k_comparator.compare(n.key, key) > 0 && n.left != sentinel){
      return search(n.left, key);
    }
    else if(k_comparator.compare(n.key, key) < 0 && n.right != sentinel){
      return search(n.right, key);
    }
    return sentinel;
  }

  @SuppressWarnings("SuspiciousNameCombination")
  private void leftRotate(Node<K, V> x){
    var y = x.right;

    x.right = y.left;
    if(y.left != sentinel){
      y.left.parent = x;
    }

    y.parent = x.parent;
    if(x.parent == sentinel){
      root = y;
    }
    else if(x == x.parent.left){
      x.parent.left = y;
    }
    else{
      x.parent.right = y;
    }

    y.left = x;
    x.parent = y;
  }

  @SuppressWarnings("SuspiciousNameCombination")
  private void rightRotate(Node<K, V> x){
    var y = x.left;

    x.left = y.right;
    if(y.right != sentinel){
      y.right.parent = x;
    }

    y.parent = x.parent;
    if(x.parent == sentinel){
      root = y;
    }
    else if(x == x.parent.right){
      x.parent.right = y;
    }
    else{
      x.parent.left = y;
    }

    y.right = x;
    x.parent = y;
  }

  private Node<K, V> treeMinimum(Node<K, V> x){
    while(x.left != sentinel) {
      x = x.left;
    }
    return x;
  }

  private Node<K, V> treeMaximum(Node<K, V> x){
    while(x.right != sentinel) {
      x = x.right;
    }
    return x;
  }

  @Override
  public @NotNull Iterator<Tuple<K, V>> iterator(){
    return new BSTIterator();
  }

  public Iterator<Tuple<K, V>> reverseIterator(){
    return new ReverseBSTIterator();
  }

  private void modified(){
    iterating = false;
  }

  private final class BSTIterator implements Iterator<Tuple<K, V>>{
    private final Deque<Node<K, V>> stack = new LinkedList<>();
    private Node<K, V> ptr;
    private boolean poppedBefore = false;
    private boolean finish = false;

    public BSTIterator(){
      iterating = true;
      ptr = root;
      if(ptr == sentinel){
        finish = true;
      }
    }

    @Override
    public boolean hasNext(){
      if(!iterating){
        throw new IllegalStateException("concurrent modification");
      }
      return !finish && ptr != null;
    }

    @Override
    public Tuple<K, V> next(){
      while(ptr != null) {
        if(ptr.left != sentinel && !poppedBefore){
          stack.push(ptr);
          ptr = ptr.left;
        }
        else{
          var t = ptr;
          if(ptr.right != sentinel){
            ptr = ptr.right;
            poppedBefore = false;
          }
          else{
            if(stack.size() != 0){
              ptr = stack.pop();
              poppedBefore = true;
            }
            else{
              ptr = null;
            }
          }
          return new Tuple<>(t.key, t.value);
        }
      }
      finish = true;
      throw new NoSuchElementException("Iterate finish.");
    }
  }

  private final class ReverseBSTIterator implements Iterator<Tuple<K, V>>{
    private final Deque<Node<K, V>> stack = new LinkedList<>();
    private Node<K, V> ptr;
    private boolean poppedBefore = false;
    private boolean finish = false;

    public ReverseBSTIterator(){
      iterating = true;
      ptr = root;
      if(ptr == sentinel){
        finish = true;
      }
    }

    @Override
    public boolean hasNext(){
      if(!iterating){
        throw new IllegalStateException("concurrent modification");
      }
      return !finish && ptr != null;
    }

    @Override
    public Tuple<K, V> next(){
      while(ptr != null) {
        if(ptr.right != sentinel && !poppedBefore){
          stack.push(ptr);
          ptr = ptr.right;
        }
        else{
          var t = ptr;
          if(ptr.left != sentinel){
            ptr = ptr.left;
            poppedBefore = false;
          }
          else{
            if(stack.size() != 0){
              ptr = stack.pop();
              poppedBefore = true;
            }
            else{
              ptr = null;
            }
          }
          return new Tuple<>(t.key, t.value);
        }
      }
      finish = true;
      throw new NoSuchElementException("Iterate finish.");
    }
  }

  static final boolean RED = false;
  static final boolean BLACK = true;

  static class Node<key, val>{
    key key;
    val value;
    boolean color;
    Node<key, val> parent;
    Node<key, val> left;
    Node<key, val> right;

    Node(boolean color){
      this.color = color;
    }

    Node(@NotNull key key, val val){
      color = RED;
      this.key = key;
      this.value = val;
    }
  }
}