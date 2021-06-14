package org.nathan.algorithmsJ.structures;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

// TODO add more BST applications (intersection search, k-d tree, etc)

@SuppressWarnings("unused")
public class OrderStatisticTree<Key>{ // get rank of node from left to right
  private final Node<Key>sentinel = new Node<>(null, BLACK);
  @NotNull private final Comparator<Key> comparator;
  private Node<Key>root = sentinel;

  public OrderStatisticTree(@NotNull Comparator<Key> comparator){
    this.comparator = comparator;
  }

  public Key floor(Key key){
    if(root == sentinel){
      throw new NoSuchElementException("calls floor() with empty symbol table");
    }
    else{
      Node<Key>x = floor(root, key);
      if(x == sentinel){
        throw new NoSuchElementException("argument to floor() is too small");
      }
      else{
        return x.key;
      }
    }
  }

  private Node<Key>floor(Node<Key>x, Key key){
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

  public Key ceiling(Key key){
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

  private Node<Key>ceiling(Node<Key>x, Key key){
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

  public Key getKeyOfRank(int rank){
    Node<Key>n = rankSelect(rank);
    return n.key;
  }

  private Node<Key>rankSelect(int ith){
    int rank = root.left.size + 1;
    if(rank == ith){
      return root;
    }
    else if(ith < rank){
      return rankSelectChild(root.left, ith);
    }
    else{
      return rankSelectChild(root.right, ith - rank);
    }
  }

  private Node<Key>rankSelectChild(Node<Key>current, int ith){
    int rank = current.left.size + 1;
    if(rank == ith){
      return current;
    }
    else if(ith < rank){
      return rankSelectChild(current.left, ith);
    }
    else{
      return rankSelectChild(current.right, ith - rank);
    }
  }

  public int getRankOfKey(Key key){
    Node<Key>n = search(key);
    return getNodeRank(n);
  }

  private int getNodeRank(Node<Key>node){
    int rank = node.left.size + 1;
    while(node != root) {
      if(node == node.parent.right){
        rank += node.parent.left.size + 1;
      }
      node = node.parent;
    }
    return rank;
  }

  public Node<Key>search(Key key){
    if(root == sentinel){
      throw new NoSuchElementException();
    }
    return search(root, key);
  }

  private Node<Key>search(Node<Key>n, Key key){
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

  private Node<Key>getSentinel(){
    return sentinel;
  }

  public Node<Key>getRoot(){
    return this.root;
  }

  private void setRoot(Node<Key>n){
    root = n;
    n.parent = sentinel;
  }

  public Node<Key>getMinimum(){
    return getMinimum(root);
  }

  public Node<Key>getMaximum(){
    return getMaximum(root);
  }

  public void insertKey(Key key){
    Node<Key>n = new Node<>(key); // default red
    insertNode(n);
  }

  private void insertNode(Node<Key>n){
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
      Node<Key>store = sentinel;
      Node<Key>ptr = root;
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

  private void insertFixUp(Node<Key>ptr){
    while(ptr.parent.color == RED) {
      if(ptr.parent == ptr.parent.parent.left){
        Node<Key>right = ptr.parent.parent.right;
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
        Node<Key>left = ptr.parent.parent.left;
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

  public void deleteKey(Key key){
    Node<Key>target = search(key);
    delete(target);
  }

  private void delete(Node<Key>target){
    if(target == sentinel){
      return;
    }
    Node<Key>ptr = target;
    boolean ptr_color = ptr.color;
    Node<Key>fix_up;
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

  private void deleteFixUp(Node<Key>fix_up){
    while(fix_up != root && fix_up.color == BLACK) {
      Node<Key>sibling;
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

  private void transplant(Node<Key>a, Node<Key>b){
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

  private void leftRotate(Node<Key>left_node){
    Node<Key>right_node = left_node.right;
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

  private void rightRotate(Node<Key>right_node){ // mirror of leftRotate
    Node<Key>left_node = right_node.left;
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

  private Node<Key>getMinimum(Node<Key>current){
    Node<Key>target = null;
    Node<Key>ptr = current;
    while(ptr != sentinel) {
      target = ptr;
      ptr = ptr.left;
    }
    if(target == null){
      throw new NoSuchElementException();
    }
    return target;
  }

  private Node<Key>getMaximum(Node<Key>current){
    Node<Key>target = null;
    Node<Key>ptr = current;
    while(ptr != sentinel) {
      target = ptr;
      ptr = ptr.right;
    }
    if(target == null){
      throw new NoSuchElementException();
    }
    return target;
  }

  private Node<Key>getSuccessor(Node<Key>current){
    if(current.right != sentinel){
      return getMinimum(current.right);
    }
    else{
      Node<Key>target = current.parent;
      Node<Key>target_right = current;
      while(target != sentinel && target.right == target_right) {
        target_right = target;
        target = target.parent;
      }
      return target;
    }
  }

  private Node<Key>getPredecessor(Node<Key>current){
    if(current.left != null){
      return getMaximum(current.left);
    }
    else{
      Node<Key>target = current.parent;
      Node<Key>target_left = current;
      while(target != null && target.left == target_left) {
        target_left = target;
        target = target.parent;
      }
      return target;
    }
  }

  public void inOrderForEach(BiConsumer<Key, Integer> bc){ // inorder print
    if(getSentinel() == getRoot()){
      return;
    }
    inorderTreeWalk(getRoot(), bc);
  }

  private void inorderTreeWalk(Node<Key>n, BiConsumer<Key, Integer> bc){
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

  private int getCount(Node<Key>n){ //overload trick
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

  private int getHeight(Node<Key>n, int height){
    if(n != getSentinel()){
      int left_max = getHeight(n.left, height + 1);
      int right_max = getHeight(n.right, height + 1);
      return Math.max(left_max, right_max);
    }
    return height;
  }
  
  private static final boolean RED = false;
  private static final boolean BLACK = true;

  static class Node<K>{
    K key;
    boolean color;
    Node<K>parent;
    Node<K>left;
    Node<K>right;
    int size; // subtree size

    Node(K key){
      this.key = key;
      int size = 1;
    }

    Node(K key, boolean color){
      this.key = key;
      this.color = color;
    }
  }

}