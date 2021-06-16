package org.nathan.algorithmsJ.structures;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

// TODO add k-d tree
// TODO migrate to OSTree

@SuppressWarnings("unused")
public class OrderStatisticTree<K> implements Iterable<K>{ // get rank of node from left to right
  private final Node<K> sentinel = new Node<>(null, BLACK);
  @NotNull
  final Comparator<K> comparator;
  Node<K> root = sentinel;

  public OrderStatisticTree(@NotNull Comparator<K> comparator){
    this.comparator = comparator;
  }

  public K floor(K key){
    if(root == sentinel){
      throw new NoSuchElementException("calls floor() with empty symbol table");
    }
    else{
      Node<K> x = floor(root, key);
      if(x == sentinel){
        throw new NoSuchElementException("argument to floor() is too small");
      }
      else{
        return x.key;
      }
    }
  }

  private Node<K> floor(Node<K> x, K key){
    if(x == sentinel){
      return sentinel;
    }
    else{
      int cmp = comparator.compare(key, x.key);
      if(cmp == 0){
        return x;
      }
      else if(cmp < 0){
        return floor(x.left, key);
      }
      else{
        var t = floor(x.right, key);
        return t != sentinel ? t : x;
      }
    }
  }

  public K ceiling(K key){
    if(root == sentinel){
      throw new NoSuchElementException("calls ceiling() with empty symbol table");
    }
    else{
      var x = ceiling(root, key);
      if(x == sentinel){
        throw new NoSuchElementException("argument to ceiling() is too small");
      }
      else{
        return x.key;
      }
    }
  }

  private Node<K> ceiling(Node<K> x, K key){
    if(x == sentinel){
      return sentinel;
    }
    else{
      int cmp = comparator.compare(key, x.key);
      if(cmp == 0){
        return x;
      }
      else if(cmp > 0){
        return ceiling(x.right, key);
      }
      else{
        var t = ceiling(x.left, key);
        return t != sentinel ? t : x;
      }
    }
  }

  public K getKeyOfRank(int rank){
    Node<K> n = getNodeOfRank(rank);
    return n.key;
  }

  Node<K> getNodeOfRank(int ith){
    int rank = root.left.size + 1;
    if(rank == ith){
      return root;
    }
    else if(ith < rank){
      return getNodeOfRank(root.left, ith);
    }
    else{
      return getNodeOfRank(root.right, ith - rank);
    }
  }

  private Node<K> getNodeOfRank(Node<K> current, int ith){
    int rank = current.left.size + 1;
    if(rank == ith){
      return current;
    }
    else if(ith < rank){
      return getNodeOfRank(current.left, ith);
    }
    else{
      return getNodeOfRank(current.right, ith - rank);
    }
  }

  public int getRankOfKey(K key){
    Node<K> n = search(key);
    return getRankOfNode(n);
  }

  int getRankOfNode(Node<K> node){
    int rank = node.left.size + 1;
    while(node != root) {
      if(node == node.parent.right){
        rank += node.parent.left.size + 1;
      }
      node = node.parent;
    }
    return rank;
  }

  public Node<K> search(K key){
    if(root == sentinel){
      throw new NoSuchElementException();
    }
    return search(root, key);
  }

  private Node<K> search(Node<K> n, K key){
    if(n.key == key){
      return n;
    }
    else if(comparator.compare(key, n.key) < 0 && n.left != sentinel){
      return search(n.left, key);
    }
    else if(comparator.compare(key, n.key) > 0 && n.right != sentinel){
      return search(n.right, key);
    }
    return sentinel;
  }

  private Node<K> getSentinel(){
    return sentinel;
  }

  public Node<K> getRoot(){
    return this.root;
  }

  private void setRoot(Node<K> n){
    root = n;
    n.parent = sentinel;
  }

  public Node<K> getMinimum(){
    return getMinimum(root);
  }

  public Node<K> getMaximum(){
    return getMaximum(root);
  }

  public void insertKey(K key){
    Node<K> n = new Node<>(key); // default red
    insertNode(n);
  }

  private void insertNode(Node<K> n){
    if(n == sentinel){
      return;
    }

    if(root == sentinel){
      n.color = BLACK;
      setRoot(n);
      root.right = sentinel;
      root.left = sentinel;
    }
    else{
      Node<K> store = sentinel;
      Node<K> ptr = root;
      while(ptr != sentinel) {
        store = ptr;
        ptr.size = ptr.size + 1;
        if(comparator.compare(n.key, ptr.key) < 0){
          ptr = ptr.left;
        }
        else{
          ptr = ptr.right;
        }
      }
      n.parent = store;
      if(comparator.compare(n.key, store.key) < 0){
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

  private void insertFixUp(Node<K> ptr){
    while(ptr.parent.color == RED) {
      if(ptr.parent == ptr.parent.parent.left){
        Node<K> right = ptr.parent.parent.right;
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
        Node<K> left = ptr.parent.parent.left;
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

  public void deleteKey(K key){
    Node<K> target = search(key);
    delete(target);
  }

  private void delete(Node<K> target){
    if(target == sentinel){
      return;
    }
    Node<K> ptr = target;
    boolean ptr_color = ptr.color;
    Node<K> fix_up;
    if(ptr.left == sentinel){
      fix_up = target.right;
      transplant(target, fix_up);
      fix_up.parent.size = fix_up.parent.size - 1;
    }
    else if(ptr.right == sentinel){
      fix_up = target.left;
      transplant(target, fix_up);
      fix_up.parent.size = fix_up.parent.size - 1;
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
        target.right.size = target.right.size - 1;
        ptr.right = target.right;
        target.right.parent = ptr;
        ptr.size = ptr.right.size + 1;
      }
      transplant(target, ptr);
      ptr.left = target.left;
      target.left.parent = ptr;
      ptr.size = ptr.left.size + ptr.size;
      ptr.parent.size = ptr.parent.size - 1;
      ptr.color = target.color;
    }
    if(ptr_color == BLACK){ // delete black node may violate property of red-black tree
      deleteFixUp(fix_up);
    }
  }

  private void deleteFixUp(Node<K> fix_up){
    while(fix_up != root && fix_up.color == BLACK) {
      Node<K> sibling;
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
          continue; // may break while condition
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

  private void transplant(Node<K> a, Node<K> b){
    if(a.parent == sentinel){
      setRoot(b);
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

  private void leftRotate(Node<K> left_node){
    Node<K> right_node = left_node.right;
    //exchange
    left_node.right = right_node.left;
    if(right_node.left != sentinel){ // remember to double link
      right_node.left.parent = left_node;
    }
    //exchange
    right_node.parent = left_node.parent; // double link right_node to left_node parent
    if(left_node.parent == sentinel){
      setRoot(right_node);
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
    right_node.size = left_node.size;
    left_node.size = left_node.left.size + left_node.right.size + 1;
  }

  private void rightRotate(Node<K> right_node){ // mirror of leftRotate
    Node<K> left_node = right_node.left;
    //exchange
    right_node.left = left_node.right;
    if(left_node.right != sentinel){ // remember to double link
      left_node.right.parent = right_node;
    }
    //exchange
    left_node.parent = right_node.parent; // double link right_node to left_node parent
    if(right_node.parent == sentinel){
      setRoot(left_node);
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
    left_node.size = right_node.size;
    right_node.size = right_node.left.size + right_node.right.size + 1;
  }

  private Node<K> getMinimum(Node<K> current){
    Node<K> target = null;
    Node<K> ptr = current;
    while(ptr != sentinel) {
      target = ptr;
      ptr = ptr.left;
    }
    if(target == null){
      throw new NoSuchElementException();
    }
    return target;
  }

  private Node<K> getMaximum(Node<K> current){
    Node<K> target = null;
    Node<K> ptr = current;
    while(ptr != sentinel) {
      target = ptr;
      ptr = ptr.right;
    }
    if(target == null){
      throw new NoSuchElementException();
    }
    return target;
  }

  private Node<K> getSuccessor(Node<K> current){
    if(current.right != sentinel){
      return getMinimum(current.right);
    }
    else{
      Node<K> target = current.parent;
      Node<K> target_right = current;
      while(target != sentinel && target.right == target_right) {
        target_right = target;
        target = target.parent;
      }
      return target;
    }
  }

  private Node<K> getPredecessor(Node<K> current){
    if(current.left != null){
      return getMaximum(current.left);
    }
    else{
      Node<K> target = current.parent;
      Node<K> target_left = current;
      while(target != null && target.left == target_left) {
        target_left = target;
        target = target.parent;
      }
      return target;
    }
  }

  public void inOrderForEach(BiConsumer<K, Integer> bc){ // inorder print
    if(getSentinel() == getRoot()){
      return;
    }
    inorderTreeWalk(getRoot(), bc);
  }

  private void inorderTreeWalk(Node<K> n, BiConsumer<K, Integer> bc){
    if(n != getSentinel()){
      inorderTreeWalk(n.left, bc);
      bc.accept(n.key, n.size);
      inorderTreeWalk(n.right, bc);
    }
  }

  public int getCount(){
    if(getRoot() == getSentinel()){
      return 0;
    }
    return getCount(getRoot());
  }

  private int getCount(Node<K> n){ //overload trick
    if(n.right != getSentinel() && n.left == getSentinel()){
      return getCount(n.right) + 1;
    }
    else if(n.right == getSentinel() && n.left != getSentinel()){
      return getCount(n.left) + 1;
    }
    else if(n.right != getSentinel() && n.left != getSentinel()){
      return getCount(n.left) + getCount(n.right) + 1;
    }
    else{
      return 1;
    }
  }

  public int getHeight(){
    if(getRoot() == getSentinel()){
      return 0;
    }
    int height = 1;
    int left_max = getHeight(getRoot().left, height);
    int right_max = getHeight(getRoot().right, height);
    return Math.max(left_max, right_max) - 1;
  }

  private int getHeight(Node<K> n, int height){
    if(n != getSentinel()){
      int left_max = getHeight(n.left, height + 1);
      int right_max = getHeight(n.right, height + 1);
      return Math.max(left_max, right_max);
    }
    return height;
  }

  private static final boolean RED = false;
  private static final boolean BLACK = true;

  @NotNull
  @Override
  public Iterator<K> iterator(){
    return new BSTIterator();
  }

  static class Node<key>{
    key key;
    boolean color;
    Node<key> parent;
    Node<key> left;
    Node<key> right;
    int size; // subtree size

    Node(key key){
      this.key = key;
      int size = 1;
    }

    Node(key key, boolean color){
      this.key = key;
      this.color = color;
    }
  }

  private final class BSTIterator implements Iterator<K>{
    private final Deque<OrderStatisticTree.Node<K>> stack = new LinkedList<>();
    private OrderStatisticTree.Node<K> ptr;
    private boolean poppedBefore = false;
    private boolean finish = false;

    public BSTIterator(){
      ptr = root;
      if(ptr == sentinel){
        finish = true;
      }
    }

    @Override
    public boolean hasNext(){
      return !finish && ptr != null;
    }

    @Override
    public K next(){
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
          return t.key;
        }
      }
      finish = true;
      throw new NoSuchElementException("Iterate finish.");
    }
  }

}