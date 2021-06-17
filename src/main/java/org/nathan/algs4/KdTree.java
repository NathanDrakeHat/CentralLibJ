package org.nathan.algs4;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.nathan.centralUtils.tuples.Tuple;
import org.nathan.centralUtils.utils.LambdaUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class KdTree{
  private static class IntvalSerchTree<Key> implements Iterable<Tuple<Key, Key>>{

    @SuppressWarnings("NullableProblems")
    @Override
    public Iterator<Tuple<Key, Key>> iterator(){
      return new org.nathan.algorithmsJ.structures.BSTIterator<>(sentinel, n -> new Tuple<>(n.low, n.high),
              () -> this.root, n -> n.right, n -> n.left, () -> true);
    }

    static class Node<Key>{
      Node<Key> parent;
      Node<Key> left;
      Node<Key> right;
      Key low;
      Key high;
      Key max;
      boolean color;

      public Node(boolean c){
        color = c;
      }

      public Node(Key lo, Key hi){
        low = lo;
        high = hi;
        max = high;
      }
    }

    final Node<Key> sentinel = new Node<>(RBTreeTemplate.BLACK);
    Node<Key> root = sentinel;
    final Comparator<Key> comparator;
    final RBTreeTemplate<Key, Node<Key>> template;

    public IntvalSerchTree(Comparator<Key> comparator){
      this.comparator = comparator;
      template = new RBTreeTemplate<>(
              sentinel, comparator,
              n -> n.low,
              () -> this.root,
              r -> this.root = r,
              n -> n.parent,
              (n, p) -> n.parent = p,
              n -> n.left,
              (n, l) -> n.left = l,
              n -> n.right,
              (n, r) -> n.right = r,
              n -> n.color,
              (n, c) -> n.color = c);
    }

    public void insertInterval(Key low, Key high){
      var n = new Node<>(low, high);
      template.insert(n);

    }

    public void deleteInterval(Key low){
      var n = template.getNodeOfKey(root, low);
      if(n == sentinel){
        throw new NoSuchElementException();
      }
      template.delete(n);
    }
  }

  private static class RBTreeTemplate<Key, Node>{

    final Node sentinel;
    final Comparator<Key> comparator;
    final Callable<Node> getRoot;
    final Consumer<Node> setRoot;
    static final boolean RED = false;
    static final boolean BLACK = true;
    final Function<Node, Node> getParent;
    final BiConsumer<Node, Node> setParent;
    final Function<Node, Node> getLeft;
    final BiConsumer<Node, Node> setLeft;
    final Function<Node, Node> getRight;
    final BiConsumer<Node, Node> setRight;
    final Function<Node, Boolean> getColor;
    final BiConsumer<Node, Boolean> setColor;
    final Function<Node, Key> getKey;

    RBTreeTemplate(Node sentinel,
                   Comparator<Key> comparator,
                   Function<Node, Key> getKey,
                   Callable<Node> getRoot,
                   Consumer<Node> setRoot,
                   Function<Node, Node> getParent,
                   BiConsumer<Node, Node> setParent,
                   Function<Node, Node> getLeft,
                   BiConsumer<Node, Node> setLeft,
                   Function<Node, Node> getRight,
                   BiConsumer<Node, Node> setRight,
                   Function<Node, Boolean> getColor,
                   BiConsumer<Node, Boolean> setColor){
      this.sentinel = sentinel;
      this.comparator = comparator;
      this.getRoot = getRoot;
      this.setRoot = setRoot;
      this.getParent = getParent;
      this.setParent = setParent;
      this.getRight = getRight;
      this.setRight = setRight;
      this.getLeft = getLeft;
      this.setLeft = setLeft;
      this.getColor = getColor;
      this.setColor = setColor;
      this.getKey = getKey;
    }

    @SuppressWarnings({"SuspiciousNameCombination", "unchecked"})
    void insert(Node z){
      var y = sentinel;
      Node x;
      try{
        x = getRoot.call();
      }
      catch(Exception e){
        throw new RuntimeException(e);
      }
      while(x != sentinel) {
        y = x;
        {//
          if(x instanceof IntvalSerchTree.Node<?>){
            var xi = (IntvalSerchTree.Node<Key>) x;
            var zi = (IntvalSerchTree.Node<Key>) z;
            xi.max = comparator.compare(xi.max, zi.max) < 0 ? zi.max : xi.max;
          }
        }
        if(comparator.compare(getKey.apply(z), getKey.apply(x)) < 0){
          x = getLeft.apply(x);
        }
        else if(comparator.compare(getKey.apply(z), getKey.apply(x)) > 0){
          x = getRight.apply(x);
        }
        else{
          throw new IllegalArgumentException("duplicate key.");
        }
      }
      setParent.accept(z, y);
      if(y == sentinel){
        setRoot.accept(z);
      }
      else if(comparator.compare(getKey.apply(z), getKey.apply(y)) < 0){
        setLeft.accept(y, z);
      }
      else if(comparator.compare(getKey.apply(z), getKey.apply(y)) > 0){
        setRight.accept(y, z);
      }
      else{
        throw new RuntimeException("impossible error.");
      }
      setLeft.accept(z, sentinel);
      setRight.accept(z, sentinel);
      setColor.accept(z, RED);
      {//

      }
      insertFixUp(z);
    }

    private void insertFixUp(Node z){
      while(getColor.apply(getParent.apply(z)) == RED) {
        if(getParent.apply(z) == getLeft.apply(getParent.apply(getParent.apply(z)))){
          var y = getRight.apply(getParent.apply(getParent.apply(z)));
          if(getColor.apply(y) == RED){
            setColor.accept(getParent.apply(z), BLACK);
            setColor.accept(y, BLACK);
            setColor.accept(getParent.apply(getParent.apply(z)), RED);
            z = getParent.apply(getParent.apply(z));
          }
          else{
            if(z == getRight.apply(getParent.apply(z))){
              z = getParent.apply(z);
              leftRotate(z);
            }
            setColor.accept(getParent.apply(z), BLACK);
            setColor.accept(getParent.apply(getParent.apply(z)), RED);
            rightRotate(getParent.apply(getParent.apply(z)));
          }
        }
        else{
          var y = getLeft.apply(getParent.apply(getParent.apply(z)));
          if(getColor.apply(y) == RED){
            setColor.accept(getParent.apply(z), BLACK);
            setColor.accept(y, BLACK);
            setColor.accept(getParent.apply(getParent.apply(z)), RED);
            z = getParent.apply(getParent.apply(z));
          }
          else{
            if(z == getLeft.apply(getParent.apply(z))){
              z = getParent.apply(z);
              rightRotate(z);
            }
            setColor.accept(getParent.apply(z), BLACK);
            setColor.accept(getParent.apply(getParent.apply(z)), RED);
            leftRotate(getParent.apply(getParent.apply(z)));
          }
        }
      }
      try{
        setColor.accept(getRoot.call(), BLACK);
      }
      catch(Exception e){
        throw new RuntimeException(e);
      }
    }

    @SuppressWarnings("unchecked")
    void delete(Node z){
      var y = z;
      var y_origin_color = getColor.apply(y);
      Node x;
      if(getLeft.apply(z) == sentinel){
        x = getRight.apply(z);
        RBTransplant(z, getRight.apply(z));
      }
      else if(getRight.apply(z) == sentinel){
        x = getLeft.apply(z);
        RBTransplant(z, getLeft.apply(z));
      }
      else{
        y = minimumNodeOf(getRight.apply(z));
        y_origin_color = getColor.apply(y);
        x = getRight.apply(y);
        if(getParent.apply(y) == z){
          setParent.accept(x, y);
        }
        else{
          RBTransplant(y, getRight.apply(y));
          setRight.accept(y, getRight.apply(z));
          setParent.accept(getRight.apply(y), y);
        }
        RBTransplant(z, y);
        setLeft.accept(y, getLeft.apply(z));
        setParent.accept(getLeft.apply(y), y);
        setColor.accept(y, getColor.apply(z));
      }

      {//
        if(y instanceof IntvalSerchTree.Node<?>){
          var p = y;
          if(getRight.apply(p) != sentinel){
            p = minimumNodeOf(getRight.apply(p));
          }
          var pi = (IntvalSerchTree.Node<Key>) p;
          while(pi != sentinel) {
            updateIntvalSerchNode(pi);
            pi = pi.parent;
          }
        }
      }

      if(y_origin_color == BLACK){
        deleteFixUp(x);
      }
    }

    private void deleteFixUp(Node x){
      while(true) {
        try{
          if(!(x != getRoot.call() && getColor.apply(x) == BLACK)){ break; }
        }
        catch(Exception e){
          throw new RuntimeException(e);
        }
        if(x == getLeft.apply(getParent.apply(x))){
          var w = getRight.apply(getParent.apply(x));
          if(getColor.apply(w) == RED){
            setColor.accept(w, BLACK);
            setColor.accept(getParent.apply(x), RED);
            leftRotate(getParent.apply(x));
            w = getRight.apply(getParent.apply(x));
          }
          if(getColor.apply(getLeft.apply(w)) == BLACK && getColor.apply(getRight.apply(w)) == BLACK){
            setColor.accept(w, RED);
            x = getParent.apply(x);
          }
          else{
            if(getColor.apply(getRight.apply(w)) == BLACK){
              setColor.accept(getLeft.apply(w), BLACK);
              setColor.accept(w, RED);
              rightRotate(w);
              w = getRight.apply(getParent.apply(x));
            }
            setColor.accept(w, getColor.apply(getParent.apply(x)));
            setColor.accept(getParent.apply(x), BLACK);
            setColor.accept(getRight.apply(w), BLACK);
            leftRotate(getParent.apply(x));
            try{
              x = getRoot.call();
            }
            catch(Exception e){
              throw new RuntimeException(e);
            }
          }
        }
        else{
          var w = getLeft.apply(getParent.apply(x));
          if(getColor.apply(w) == RED){
            setColor.accept(w, BLACK);
            setColor.accept(getParent.apply(x), RED);
            rightRotate(getParent.apply(x));
            w = getLeft.apply(getParent.apply(x));
          }
          if(getColor.apply(getLeft.apply(w)) == BLACK && getColor.apply(getRight.apply(w)) == BLACK){
            setColor.accept(w, RED);
            x = getParent.apply(x);
          }
          else{
            if(getColor.apply(getLeft.apply(w)) == BLACK){
              setColor.accept(getRight.apply(w), BLACK);
              setColor.accept(w, RED);
              leftRotate(w);
              w = getLeft.apply(getParent.apply(x));
            }
            setColor.accept(w, getColor.apply(getParent.apply(x)));
            setColor.accept(getParent.apply(x), BLACK);
            setColor.accept(getLeft.apply(w), BLACK);
            rightRotate(getParent.apply(x));
            try{
              x = getRoot.call();
            }
            catch(Exception e){
              throw new RuntimeException(e);
            }
          }
        }
      }
      setColor.accept(x, BLACK);
    }

    private Node minimumNodeOf(Node x){
      while(getLeft.apply(x) != sentinel) {
        x = getLeft.apply(x);
      }
      return x;
    }

    private void RBTransplant(Node u, Node v){
      if(getParent.apply(u) == sentinel){
        setRoot.accept(v);
      }
      else if(u == getLeft.apply(getParent.apply(u))){
        setLeft.accept(getParent.apply(u), v);
      }
      else{
        setRight.accept(getParent.apply(u), v);
      }
      setParent.accept(v, getParent.apply(u));
    }

    @SuppressWarnings("unchecked")
    private void leftRotate(Node x){
      var y = getRight.apply(x);

      setRight.accept(x, getLeft.apply(y));
      if(getLeft.apply(y) != sentinel){
        setParent.accept(getLeft.apply(y), x);
      }

      setParent.accept(y, getParent.apply(x));
      if(getParent.apply(x) == sentinel){
        setRoot.accept(y);
      }
      else if(x == getLeft.apply(getParent.apply(x))){
        setLeft.accept(getParent.apply(x), y);
      }
      else{
        setRight.accept(getParent.apply(x), y);
      }

      setLeft.accept(y, x);
      setParent.accept(x, y);
      {//
        if(x instanceof IntvalSerchTree.Node<?>){
          var xi = (IntvalSerchTree.Node<Key>) x;
          updateIntvalSerchNode(xi);
          xi = xi.parent;
          if(xi != sentinel){
            updateIntvalSerchNode(xi);
          }
        }
      }
    }

    @SuppressWarnings("unchecked")
    private void rightRotate(Node x){
      var y = getLeft.apply(x);

      setLeft.accept(x, getRight.apply(y));
      if(getRight.apply(y) != sentinel){
        setParent.accept(getRight.apply(y), x);
      }

      setParent.accept(y, getParent.apply(x));
      if(getParent.apply(x) == sentinel){
        setRoot.accept(y);
      }
      else if(x == getRight.apply(getParent.apply(x))){
        setRight.accept(getParent.apply(x), y);
      }
      else{
        setLeft.accept(getParent.apply(x), y);
      }

      setRight.accept(y, x);
      setParent.accept(x, y);
      {//
        if(x instanceof IntvalSerchTree.Node<?>){
          var xi = (IntvalSerchTree.Node<Key>) x;
          updateIntvalSerchNode(xi);
          xi = xi.parent;
          if(xi != sentinel){
            updateIntvalSerchNode(xi);
          }
        }
      }
    }

    private void updateIntvalSerchNode(IntvalSerchTree.Node<Key> n){
      if(n.left != sentinel && n.right != sentinel){
        n.max = comparator.compare(n.right.max, n.left.max) < 0 ? n.left.max : n.right.max;
        n.max = comparator.compare(n.max, n.high) < 0 ? n.high : n.max;
      }
      else if(n.left != sentinel){
        n.max = comparator.compare(n.high, n.left.max) < 0 ? n.left.max : n.high;
      }
      else if(n.right != sentinel){
        n.max = comparator.compare(n.high, n.right.max) < 0 ? n.right.max : n.high;
      }
      else{
        n.max = n.high;
      }
    }

    /**
     * @param n   node
     * @param key key
     * @return node with key or sentinel
     */
    Node getNodeOfKey(Node n, Key key){
      if(comparator.compare(getKey.apply(n), key) == 0){
        return n;
      }
      else if(getLeft.apply(n) != sentinel && comparator.compare(getKey.apply(n), key) > 0){
        return getNodeOfKey(getLeft.apply(n), key);
      }
      else if(getRight.apply(n) != sentinel && comparator.compare(getKey.apply(n), key) < 0){
        return getNodeOfKey(getRight.apply(n), key);
      }
      return sentinel;
    }
  }

  public static class BSTIterator<Node, Target> implements Iterator<Target>{
    private final Deque<Node> stack = new LinkedList<>();
    private Node ptr;
    private boolean poppedBefore = false;
    private boolean finish = false;

    private final Function<Node, Target> getTarget;

    private final Node sentinel;

    private final Function<Node, Node> getLeft;

    private final Function<Node, Node> getRight;

    private final Callable<Boolean> iterating;

    public BSTIterator(
            Node sentinel,
            Function<Node, Target> getTarget,
            Callable<Node> getRoot,
            Function<Node, Node> getRight,
            Function<Node, Node> getLeft,
            Callable<Boolean> getIterating){
      this.getTarget = getTarget;
      this.sentinel = sentinel;
      ptr = LambdaUtils.stripCE(getRoot);
      this.getRight = getRight;
      this.getLeft = getLeft;
      this.iterating = getIterating;
      if(ptr == sentinel){
        finish = true;
      }
    }

    @Override
    public boolean hasNext(){
      if(!LambdaUtils.stripCE(iterating)){
        throw new IllegalStateException("concurrent modification");
      }
      return !finish && ptr != null;
    }

    @Override
    public Target next(){
      while(ptr != null) {
        if(getLeft.apply(ptr) != sentinel && !poppedBefore){
          stack.push(ptr);
          ptr = getLeft.apply(ptr);
        }
        else{
          var t = ptr;
          if(getRight.apply(ptr) != sentinel){
            ptr = getRight.apply(ptr);
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
          return getTarget.apply(t);
        }
      }
      finish = true;
      throw new NoSuchElementException("Iterate finish.");
    }
  }



  public KdTree(){}

  public boolean isEmpty(){

    return false;
  }

  public int size(){
    return 0;
  }

  public void insert(Point2D p){

  }

  public boolean contains(Point2D p){
    return false;
  }

  public void draw(){

  }

  public Iterable<Point2D> range(RectHV rect){
    return null;
  }

  public Point2D nearest(Point2D p){
    return null;
  }
}
