package org.nathan.algorithmsJ.structures;

import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

// TODO add more BST applications (intersection search, k-d tree, etc)

@SuppressWarnings("unused")
public final class OrderStatisticTree{ // get rank of node from left to right
  private final Node sentinel = new Node(0, Color.BLACK);
  private Node root = sentinel;

  public double floor(double key){
    if(root == sentinel){
      throw new NoSuchElementException("calls floor() with empty symbol table");
    }
    else{
      Node x = floor(root, key);
      if(x == sentinel){
        throw new NoSuchElementException("argument to floor() is too small");
      }
      else{
        return x.key;
      }
    }
  }

  private Node floor(Node x, double key){
    if(x == sentinel){
      return sentinel;
    }
    else{
      int cmp = Double.compare(key, x.key);
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

  public double ceiling(double key){
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

  private Node ceiling(Node x, double key){
    if(x == sentinel){
      return sentinel;
    }
    else{
      int cmp = Double.compare(key, x.key);
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

  public double getKeyOfRank(int rank){
    Node n = rankSelect(rank);
    return n.key;
  }

  private Node rankSelect(int ith){
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

  private Node rankSelectChild(Node current, int ith){
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

  public int getRankOfKey(double key){
    Node n = search(key);
    return getNodeRank(n);
  }

  private int getNodeRank(Node node){
    int rank = node.left.size + 1;
    while(node != root) {
      if(node == node.parent.right){
        rank += node.parent.left.size + 1;
      }
      node = node.parent;
    }
    return rank;
  }

  public Node search(double key){
    if(root == sentinel){
      throw new NoSuchElementException();
    }
    return search(root, key);
  }

  private Node search(Node n, double key){
    if(n.key == key){
      return n;
    }
    else if(key < n.key && n.left != sentinel){
      return search(n.left, key);
    }
    else if(key > n.key && n.right != sentinel){
      return search(n.right, key);
    }
    return sentinel;
  }

  private Node getSentinel(){
    return sentinel;
  }

  public Node getRoot(){
    return this.root;
  }

  private void setRoot(Node n){
    root = n;
    n.parent = sentinel;
  }

  public Node getMinimum(){
    return getMinimum(root);
  }

  public Node getMaximum(){
    return getMaximum(root);
  }

  public void insertKey(double key){
    Node n = new Node(key); // default red
    insertNode(n);
  }

  private void insertNode(Node n){
    if(n == sentinel){
      return;
    }

    if(root == sentinel){
      n.color = Color.BLACK;
      setRoot(n);
      root.right = sentinel;
      root.left = sentinel;
    }
    else{
      Node store = sentinel;
      Node ptr = root;
      while(ptr != sentinel) {
        store = ptr;
        ptr.size = ptr.size + 1;
        if(n.key < ptr.key){
          ptr = ptr.left;
        }
        else{
          ptr = ptr.right;
        }
      }
      n.parent = store;
      if(n.key < store.key){
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

  private void insertFixUp(Node ptr){
    while(ptr.parent.color == Color.RED) {
      if(ptr.parent == ptr.parent.parent.left){
        Node right = ptr.parent.parent.right;
        if(right.color == Color.RED){ // case1: sibling is red
          ptr.parent.color = Color.BLACK;
          right.color = Color.BLACK;
          ptr.parent.parent.color = Color.RED;
          ptr = ptr.parent.parent;
          continue;
        }
        else if(ptr == ptr.parent.right){ //case 2 convert to case 3
          ptr = ptr.parent;
          leftRotate(ptr);
        }
        ptr.parent.color = Color.BLACK; // case3
        ptr.parent.parent.color = Color.RED;
        rightRotate(ptr.parent.parent); // ptr.getParent will be black and then break
        ptr = ptr.parent;
      }
      else{
        Node left = ptr.parent.parent.left;
        if(left.color == Color.RED){
          ptr.parent.color = Color.BLACK;
          left.color = Color.BLACK;
          ptr.parent.parent.color = Color.RED;
          ptr = ptr.parent.parent;
          continue;
        }
        else if(ptr == ptr.parent.left){
          ptr = ptr.parent;
          rightRotate(ptr);
        }
        ptr.parent.color = Color.BLACK;
        ptr.parent.parent.color = Color.RED;
        leftRotate(ptr.parent.parent);
        ptr = ptr.parent;
      }
    }
    root.color = Color.BLACK;
  }

  public void deleteKey(double key){
    Node target = search(key);
    delete(target);
  }

  private void delete(Node target){
    if(target == sentinel){
      return;
    }
    Node ptr = target;
    Color ptr_color = ptr.color;
    Node fix_up;
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
    if(ptr_color == Color.BLACK){ // delete black node may violate property of red-black tree
      deleteFixUp(fix_up);
    }
  }

  private void deleteFixUp(Node fix_up){
    while(fix_up != root && fix_up.color == Color.BLACK) {
      Node sibling;
      if(fix_up == fix_up.parent.left){
        sibling = fix_up.parent.right;
        if(sibling.color == Color.RED){ // case1:sibling is black, convert to case 2, 3 or 4
          sibling.color = Color.BLACK; // , which denote that sibling is black
          fix_up.parent.color = Color.RED;
          leftRotate(fix_up.parent);
          sibling = fix_up.parent.right;
        }
        if(sibling.left.color == Color.BLACK && sibling.right.color == Color.BLACK){ // case2: sibling children is black
          sibling.color = Color.RED;
          fix_up = fix_up.parent;
          continue; // may break while condition
        }
        else if(sibling.right.color == Color.BLACK){ // case3: sibling left red, right black. convert case4
          sibling.left.color = Color.BLACK;
          sibling.color = Color.RED;
          rightRotate(sibling);
          sibling = fix_up.parent.right;
        }
        sibling.color = fix_up.parent.color; // case4: sibling right red
        fix_up.parent.color = Color.BLACK;
        sibling.right.color = Color.BLACK;
        leftRotate(fix_up.parent);
      }
      else{
        sibling = fix_up.parent.left;
        if(sibling.color == Color.RED){
          sibling.color = Color.BLACK;
          fix_up.parent.color = Color.RED;
          rightRotate(fix_up.parent);
          sibling = fix_up.parent.left;
        }
        if(sibling.left.color == Color.BLACK && sibling.right.color == Color.BLACK){
          sibling.color = Color.RED;
          fix_up = fix_up.parent;
          continue;
        }
        else if(sibling.left.color == Color.BLACK){
          sibling.right.color = Color.BLACK;
          sibling.color = Color.RED;
          leftRotate(sibling);
          sibling = fix_up.parent.left;
        }
        sibling.color = fix_up.parent.color;
        fix_up.parent.color = Color.BLACK;
        sibling.left.color = Color.BLACK;
        rightRotate(fix_up.parent);
      }
      fix_up = root;
    }
    fix_up.color = Color.BLACK;
  }

  private void transplant(Node a, Node b){
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

  private void leftRotate(Node left_node){
    Node right_node = left_node.right;
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

  private void rightRotate(Node right_node){ // mirror of leftRotate
    Node left_node = right_node.left;
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

  private Node getMinimum(Node current){
    Node target = null;
    Node ptr = current;
    while(ptr != sentinel) {
      target = ptr;
      ptr = ptr.left;
    }
    if(target == null){
      throw new NoSuchElementException();
    }
    return target;
  }

  private Node getMaximum(Node current){
    Node target = null;
    Node ptr = current;
    while(ptr != sentinel) {
      target = ptr;
      ptr = ptr.right;
    }
    if(target == null){
      throw new NoSuchElementException();
    }
    return target;
  }

  private Node getSuccessor(Node current){
    if(current.right != sentinel){
      return getMinimum(current.right);
    }
    else{
      Node target = current.parent;
      Node target_right = current;
      while(target != sentinel && target.right == target_right) {
        target_right = target;
        target = target.parent;
      }
      return target;
    }
  }

  private Node getPredecessor(Node current){
    if(current.left != null){
      return getMaximum(current.left);
    }
    else{
      Node target = current.parent;
      Node target_left = current;
      while(target != null && target.left == target_left) {
        target_left = target;
        target = target.parent;
      }
      return target;
    }
  }

  public void inOrderForEach(BiConsumer<Double, Integer> bc){ // inorder print
    if(getSentinel() == getRoot()){
      return;
    }
    inorderTreeWalk(getRoot(), bc);
  }

  private void inorderTreeWalk(Node n, BiConsumer<Double, Integer> bc){
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

  private int getCount(Node n){ //overload trick
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

  private int getHeight(Node n, int height){
    if(n != getSentinel()){
      int left_max = getHeight(n.left, height + 1);
      int right_max = getHeight(n.right, height + 1);
      return Math.max(left_max, right_max);
    }
    return height;
  }

  enum Color{
    RED,
    BLACK
  }

  // sentinel: denote leaf and parent of root
  static class Node{
    double key;
    Color color;
    Node parent;
    Node left;
    Node right;
    int size; // subtree size

    Node(double key){
      this.key = key;
      this.color = Color.RED;
      int size = 1;
    }

    Node(double key, Color color){
      this.key = key;
      this.color = color;
    }
  }

}