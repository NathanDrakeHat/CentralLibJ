package org.nathan.algorithmsJava.structures;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;
import java.util.function.BiConsumer;


public class RedBlackTree<K, V> implements Iterable<Tuple<K, V>>{
  @NotNull
  private final Comparator<K> k_comparator;
  @NotNull
  private final Node<K, V> sentinel = new Node<>(BLACK);// sentinel: denote leaf and parent of root
  @NotNull Node<K, V> root;
  private boolean iterating = false;

  public RedBlackTree(@NotNull Comparator<K> k_comparator){
    this.k_comparator = k_comparator;
    root = sentinel;
  }

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
    if(k_comparator.compare(n.key, low) >= 0 && k_comparator.compare(n.key, high) <= 0){
      l.add(new Tuple<>(n.key, n.value));
    }

    if(k_comparator.compare(n.key, low) > 0){
      keyRangeSearch(n.left, low, high, l);
    }

    if(k_comparator.compare(n.key, high) < 0){
      keyRangeSearch(n.right, low, high, l);
    }
  }

  public V getValueOfMinKey(){
    if(sentinel != root){
      return getMinimum(root).value;
    }
    else{
      throw new NoSuchElementException("null tree");
    }
  }

  public K getMinKey(){
    if(sentinel != root){
      return getMinimum(root).key;
    }
    else{
      throw new NoSuchElementException("null tree");
    }
  }

  public V getValueOfMaxKey(){
    if(root != sentinel){
      return getMaximum(root).value;
    }
    else{
      throw new NoSuchElementException("null tree");
    }
  }

  public K getMaxKey(){
    if(root != sentinel){
      return getMaximum(root).key;
    }
    else{
      throw new NoSuchElementException("null tree");
    }
  }

  private void resetRoot(Node<K, V> r){
    root = r;
    root.parent = sentinel;
  }

  public void inOrderForEach(@NotNull BiConsumer<K, V> bc){ // inorder print
    if(sentinel == root){
      return;
    }
    inorderTreeWalk(root, bc);
  }

  private void inorderTreeWalk(Node<K, V> n, BiConsumer<K, V> bc){
    if(n != sentinel & n != null){
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

  private int getCount(Node<K, V> n){ //overload trick
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
      return 0;
    }
    int height = 1;
    int left_max = getHeight(root.left, height);
    int right_max = getHeight(root.right, height);
    return Math.max(left_max, right_max) - 1;
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

  private void insert(Node<K, V> n){
    if(n == sentinel){
      return;
    }
    if(root == sentinel){
      resetRoot(n);
      root.right = sentinel;
      root.left = sentinel;
    }
    else{
      var store = sentinel;
      var ptr = root;
      while(ptr != sentinel) {
        store = ptr;
        if(k_comparator.compare(n.key, ptr.key) < 0){
          ptr = ptr.left;
        }
        else{
          ptr = ptr.right;
        }
      }
      n.parent = store;
      if(k_comparator.compare(n.key, store.key) < 0){
        store.left = n;
      }
      else{
        store.right = n;
      }
      n.left = sentinel;
      n.right = sentinel;
    }
    insertFixUp(n);
  }

  private void insertFixUp(Node<K, V> ptr){
    while(ptr.parent.color == RED) {
      if(ptr.parent == ptr.parent.parent.left){
        var right = ptr.parent.parent.right;
        if(right.color == RED){ // case1: sibling is red
          ptr.parent.color = BLACK;
          right.color = BLACK;
          ptr.parent.parent.color = RED;
          ptr = ptr.parent.parent;
          continue;
        }
        else if(ptr == ptr.parent.right){ //case 2 convert to case 3
          ptr = ptr.parent;
          leftRotate(ptr);
        }
        ptr.parent.color = BLACK; // case3
        ptr.parent.parent.color = RED;
        rightRotate(ptr.parent.parent); // ptr.getParent will be black and then break
        ptr = ptr.parent;
      }
      else{
        var left = ptr.parent.parent.left;
        if(left.color == RED){
          ptr.parent.color = BLACK;
          left.color = BLACK;
          ptr.parent.parent.color = RED;
          ptr = ptr.parent.parent;
          continue;
        }
        else if(ptr == ptr.parent.left){
          ptr = ptr.parent;
          rightRotate(ptr);
        }
        ptr.parent.color = BLACK;
        ptr.parent.parent.color = RED;
        leftRotate(ptr.parent.parent);
        ptr = ptr.parent;
      }
    }
    root.color = BLACK;
  }

  public void delete(@NotNull K key){
    modified();
    delete(search(root, key));
  }

  private void delete(Node<K, V> target){
    if(target == null || target == sentinel){
      throw new NoSuchElementException("null tree");
    }
    var ptr = target;
    var ptr_color = ptr.color;
    Node<K, V> fix_up;
    if(ptr.left == sentinel){
      fix_up = target.right;
      transplant(target, fix_up);
    }
    else if(ptr.right == sentinel){
      fix_up = target.left;
      transplant(target, fix_up);
    }
    else{
      ptr = getSuccessor(target);
      ptr_color = ptr.color;
      fix_up = ptr.right;
      if(ptr.parent == target){
        fix_up.parent = ptr; // in case of sentinel refer to target
      }
      else{
        transplant(ptr, ptr.right);
        ptr.right = target.right;
        target.right.parent = ptr;
      }
      transplant(target, ptr);
      ptr.left = target.left;
      target.left.parent = ptr;
      ptr.color = target.color;
    }
    if(ptr_color == BLACK){ // delete black node may violate property of red-black tree
      deleteFixUp(fix_up);
    }
  }

  private void deleteFixUp(Node<K, V> fix_up){
    while(fix_up != root && fix_up.color == BLACK) {
      Node<K, V> sibling;
      if(fix_up == fix_up.parent.left){
        sibling = fix_up.parent.right;
        if(sibling.color == RED){ // case1:sibling is black, convert to case 2, 3 or 4
          sibling.color = BLACK; // , which denote that sibling is black
          fix_up.parent.color = RED;
          leftRotate(fix_up.parent);
          sibling = fix_up.parent.right;
        }
        if(sibling.left.color == BLACK && sibling.right.color == BLACK){ // case2: sibling children is black
          sibling.color = RED;
          fix_up = fix_up.parent;
          continue;
        }
        else if(sibling.right.color == BLACK){ // case3: sibling left red, right black. convert case4
          sibling.left.color = BLACK;
          sibling.color = RED;
          rightRotate(sibling);
          sibling = fix_up.parent.right;
        }
        sibling.color = fix_up.parent.color; // case4: sibling right red
        fix_up.parent.color = BLACK;
        sibling.right.color = BLACK;
        leftRotate(fix_up.parent);
      }
      else{
        sibling = fix_up.parent.left;
        if(sibling.color == RED){
          sibling.color = BLACK;
          fix_up.parent.color = RED;
          rightRotate(fix_up.parent);
          sibling = fix_up.parent.left;
        }
        if(sibling.left.color == BLACK && sibling.right.color == BLACK){
          sibling.color = RED;
          fix_up = fix_up.parent;
          continue;
        }
        else if(sibling.left.color == BLACK){
          sibling.right.color = BLACK;
          sibling.color = RED;
          leftRotate(sibling);
          sibling = fix_up.parent.left;
        }
        sibling.color = fix_up.parent.color;
        fix_up.parent.color = BLACK;
        sibling.left.color = BLACK;
        rightRotate(fix_up.parent);
      }
      fix_up = root;
    }
    fix_up.color = BLACK;
  }

  private void transplant(Node<K, V> a, Node<K, V> b){
    if(a.parent == sentinel){
      resetRoot(b);
    }
    else if(a.parent.right == a){
      a.parent.right = b;
      b.parent = a.parent; // permissible if b is sentinel
    }
    else{
      a.parent.left = b;
      b.parent = a.parent;
    }
  }

  public V search(@NotNull K key){
    if(root == sentinel){
      throw new NoSuchElementException();
    }
    return search(root, key).value;
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
    throw new NoSuchElementException();
  }

  private void leftRotate(Node<K, V> left_node){
    var right_node = left_node.right;
    //exchange
    left_node.right = right_node.left;
    if(right_node.left != sentinel){ // remember to double link
      right_node.left.parent = left_node;
    }
    //exchange
    right_node.parent = left_node.parent; // double link right_node to left_node parent
    if(left_node.parent == sentinel){
      resetRoot(right_node);
    }
    else if(left_node.parent.left == left_node){
      left_node.parent.left = right_node;
    }
    else{
      left_node.parent.right = right_node;
    }
    //exchange
    right_node.left = left_node;
    left_node.parent = right_node;
  }

  private void rightRotate(Node<K, V> right_node){ // mirror of leftRotate
    var left_node = right_node.left;
    //exchange
    right_node.left = left_node.right;
    if(left_node.right != sentinel){ // remember to double link
      left_node.right.parent = right_node;
    }
    //exchange
    left_node.parent = right_node.parent; // double link right_node to left_node parent
    if(right_node.parent == sentinel){
      resetRoot(left_node);
    }
    else if(right_node.parent.right == right_node){
      right_node.parent.right = left_node;
    }
    else{
      right_node.parent.left = left_node;
    }
    //exchange
    left_node.right = right_node;
    right_node.parent = left_node;
  }

  private Node<K, V> getMinimum(Node<K, V> current){
    Node<K, V> target = null;
    var ptr = current;
    while(ptr != sentinel) {
      target = ptr;
      ptr = ptr.left;
    }
    if(target == null){
      throw new NoSuchElementException();
    }
    return target;
  }

  private Node<K, V> getMaximum(Node<K, V> current){
    Node<K, V> target = null;
    var ptr = current;
    while(ptr != sentinel) {
      target = ptr;
      ptr = ptr.right;
    }
    if(target == null){
      throw new NoSuchElementException();
    }
    return target;
  }

  private Node<K, V> getSuccessor(Node<K, V> current){
    if(current.right != sentinel){
      return getMinimum(current.right);
    }
    else{
      var target = current.parent;
      var target_right = current;
      while((target != sentinel) && target.right == target_right) {
        target_right = target;
        target = target.parent;
      }
      return target;
    }
  }

  @SuppressWarnings("unused")
  private Node<K, V> getPredecessor(Node<K, V> current){
    if(current.left != sentinel){
      return getMaximum(current.left);
    }
    else{
      var target = current.parent;
      var target_left = current;
      while((target != sentinel) && (target.left == target_left)) {
        target_left = target;
        target = target.parent;

      }
      return target;
    }
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
        if(ptr.left != sentinel && !poppedBefore) // if popped before, walk to right
        {
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
        if(ptr.right != sentinel && !poppedBefore) // if popped before, walk to right
        {
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

  private static final boolean RED = false;
  private static final boolean BLACK = true;

  static class Node<P, Q>{
    P key;
    Q value;
    boolean color;
    Node<P, Q> parent;
    Node<P, Q> left;
    Node<P, Q> right;

    Node(boolean color){
      this.color = color;
    }

    Node(@NotNull P key, Q val){
      color = RED;
      this.key = key;
      this.value = val;
    }
  }
}