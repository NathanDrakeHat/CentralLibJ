package org.nathan.algorithmsJ.structures;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static org.nathan.centralUtils.utils.LambdaUtils.stripCE;

/**
 * where to add new operation
 */
@Target(ElementType.METHOD)
@interface Template{

}

/**
 * red black tree code template
 *
 * @param <Key> key of node
 */
@SuppressWarnings("ClassCanBeRecord")
class RBTreeTemplate<Key>{
  final @NotNull RBNode<Key> sentinel;
  final @NotNull Comparator<Key> comparator;
  final @NotNull Callable<RBNode<Key>> getRoot;
  final @NotNull Consumer<RBNode<Key>> setRoot;

  RBTreeTemplate(@NotNull RBNode<Key> sentinel,
                 @NotNull Comparator<Key> comparator,
                 @NotNull Callable<RBNode<Key>> getRoot,
                 @NotNull Consumer<RBNode<Key>> setRoot){
    this.sentinel = sentinel;
    this.comparator = comparator;
    this.getRoot = getRoot;
    this.setRoot = setRoot;
  }

  /**
   * insert a node <b>WITHOUT</b> duplicate key
   *
   * @param z node or sentinel
   */
  @Template
  @SuppressWarnings("SuspiciousNameCombination")
  void insert(RBNode<Key> z){
    var y = sentinel;
    var x = stripCE(getRoot);
    while(x != sentinel) {
      y = x;
      {//
        if(x instanceof OrderStatTree.Node<?, ?> xo){
          xo.size++;
        }
        else if(x instanceof IntvalSerchTree.Node<Key> xi){
          var zi = (IntvalSerchTree.Node<Key>) z;
          xi.max = comparator.compare(xi.max, zi.max) < 0 ? zi.max : xi.max;
        }
      }
      if(comparator.compare(z.getKey(), x.getKey()) < 0){
        x = x.getLeft();
      }
      else if(comparator.compare(z.getKey(), x.getKey()) > 0){
        x = x.getRight();
      }
      else{
        throw new IllegalArgumentException("duplicate key.");
      }
    }
    z.setParent(y);
    if(y == sentinel){
      setRoot.accept(z);
    }
    else if(comparator.compare(z.getKey(), y.getKey()) < 0){
      y.setLeft(z);
    }
    else if(comparator.compare(z.getKey(), y.getKey()) > 0){
      y.setRight(z);
    }
    else{
      throw new RuntimeException("impossible error.");
    }
    z.setLeft(sentinel);
    z.setRight(sentinel);
    z.setRed();
    {//
      if(z instanceof OrderStatTree.Node<?, ?> zo){
        zo.size = 1;
      }
    }
    insertFixUp(z);
  }

  private void insertFixUp(RBNode<Key> z){
    while(z.getParent().isRed()) {
      if(z.getParent() == z.getParent().getParent().getLeft()){
        var y = z.getParent().getParent().getRight();
        if(y.isRed()){
          z.getParent().setBlack();
          y.setBlack();
          z.getParent().getParent().setRed();
          z = z.getParent().getParent();
        }
        else{
          if(z == z.getParent().getRight()){
            z = z.getParent();
            leftRotate(z);
          }
          z.getParent().setBlack();
          z.getParent().getParent().setRed();
          rightRotate(z.getParent().getParent());
        }
      }
      else{
        var y = z.getParent().getParent().getLeft();
        if(y.isRed()){
          z.getParent().setBlack();
          y.setBlack();
          z.getParent().getParent().setRed();
          z = z.getParent().getParent();
        }
        else{
          if(z == z.getParent().getLeft()){
            z = z.getParent();
            rightRotate(z);
          }
          z.getParent().setBlack();
          z.getParent().getParent().setRed();
          leftRotate(z.getParent().getParent());
        }
      }
    }
    stripCE(getRoot).setBlack();
  }

  @Template
  void delete(RBNode<Key> z){
    var y = z;
    var y_origin_color = y.getColor();
    RBNode<Key> x;
    if(z.getLeft() == sentinel){
      x = z.getRight();
      RBTransplant(z, z.getRight());
    }
    else if(z.getRight() == sentinel){
      x = z.getLeft();
      RBTransplant(z, z.getLeft());
    }
    else{
      y = minimumNodeOf(z.getRight());
      y_origin_color = y.getColor();
      x = y.getRight();
      if(y.getParent() == z){
        x.setParent(y);
      }
      else{
        RBTransplant(y, y.getRight());
        y.setRight(z.getRight());
        y.getRight().setParent(y);
      }
      RBTransplant(z, y);
      y.setLeft(z.getLeft());
      y.getLeft().setParent(y);
      y.setColor(z.getColor());
    }

    {//
      if(y instanceof OrderStatTree.Node<?, ?>){
        if(y.getRight() != sentinel){
          y = minimumNodeOf(y.getRight());
        }
        //noinspection PatternVariableCanBeUsed
        var yo = (OrderStatTree.Node<?, ?>) y;
        while(yo != sentinel) {
          yo.size = yo.right.size + yo.left.size + 1;
          yo = yo.parent;
        }
      }
      else if(y instanceof IntvalSerchTree.Node<Key>){
        var p = y;
        if(p.getRight() != sentinel){
          p = minimumNodeOf(p.getRight());
        }
        var pi = (IntvalSerchTree.Node<Key>)p;
        while(pi != sentinel){
          updateISNode(pi);
          pi = pi.parent;
        }
      }
    }

    if(y_origin_color == RBNode.BLACK){
      deleteFixUp(x);
    }
  }

  private void deleteFixUp(RBNode<Key> x){
    while(x != stripCE(getRoot) && x.isBlack()) {
      if(x == x.getParent().getLeft()){
        var w = x.getParent().getRight();
        if(w.isRed()){
          w.setBlack();
          x.getParent().setRed();
          leftRotate(x.getParent());
          w = x.getParent().getRight();
        }
        if(w.getLeft().isBlack() && w.getRight().isBlack()){
          w.setRed();
          x = x.getParent();
        }
        else{
          if(w.getRight().isBlack()){
            w.getLeft().setBlack();
            w.setRed();
            rightRotate(w);
            w = x.getParent().getRight();
          }
          w.setColor(x.getParent().getColor());
          x.getParent().setBlack();
          w.getRight().setBlack();
          leftRotate(x.getParent());
          x = stripCE(getRoot);
        }
      }
      else{
        var w = x.getParent().getLeft();
        if(w.isRed()){
          w.setBlack();
          x.getParent().setRed();
          rightRotate(x.getParent());
          w = x.getParent().getLeft();
        }
        if(w.getLeft().isBlack() && w.getRight().isBlack()){
          w.setRed();
          x = x.getParent();
        }
        else{
          if(w.getLeft().isBlack()){
            w.getRight().setBlack();
            w.setRed();
            leftRotate(w);
            w = x.getParent().getLeft();
          }
          w.setColor(x.getParent().getColor());
          x.getParent().setBlack();
          w.getLeft().setBlack();
          rightRotate(x.getParent());
          x = stripCE(getRoot);
        }
      }
    }
    x.setBlack();
  }

  private RBNode<Key> minimumNodeOf(RBNode<Key> x){
    while(x.getLeft() != sentinel) {
      x = x.getLeft();
    }
    return x;
  }

  private void RBTransplant(RBNode<Key> u, RBNode<Key> v){
    if(u.getParent() == sentinel){
      setRoot.accept(v);
    }
    else if(u == u.getParent().getLeft()){
      u.getParent().setLeft(v);
    }
    else{
      u.getParent().setRight(v);
    }
    v.setParent(u.getParent());
  }

  @Template
  private void leftRotate(RBNode<Key> x){
    var y = x.getRight();

    x.setRight(y.getLeft());
    if(y.getLeft() != sentinel){
      y.getLeft().setParent(x);
    }

    y.setParent(x.getParent());
    if(x.getParent() == sentinel){
      setRoot.accept(y);
    }
    else if(x == x.getParent().getLeft()){
      x.getParent().setLeft(y);
    }
    else{
      x.getParent().setRight(y);
    }

    y.setLeft(x);
    x.setParent(y);
    {//
      if(x instanceof OrderStatTree.Node<?, ?> xo && y instanceof OrderStatTree.Node<?, ?> yo){
        yo.size = xo.size;
        xo.size = xo.left.size + xo.right.size + 1;
      }
      else if(x instanceof IntvalSerchTree.Node<Key> xi){
        updateISNode(xi);
        xi = xi.parent;
        if(xi != sentinel){
          updateISNode(xi);
        }
      }
    }
  }

  @Template
  private void rightRotate(RBNode<Key> x){
    var y = x.getLeft();

    x.setLeft(y.getRight());
    if(y.getRight() != sentinel){
      y.getRight().setParent(x);
    }

    y.setParent(x.getParent());
    if(x.getParent() == sentinel){
      setRoot.accept(y);
    }
    else if(x == x.getParent().getRight()){
      x.getParent().setRight(y);
    }
    else{
      x.getParent().setLeft(y);
    }

    y.setRight(x);
    x.setParent(y);
    {//
      if(x instanceof OrderStatTree.Node<?, ?> xo && y instanceof OrderStatTree.Node<?, ?> yo){
        yo.size = xo.size;
        xo.size = xo.left.size + xo.right.size + 1;
      }
      else if(x instanceof IntvalSerchTree.Node<Key> xi){
        updateISNode(xi);
        xi = xi.parent;
        if(xi != sentinel){
          updateISNode(xi);
        }
      }
    }
  }

  private void updateISNode(IntvalSerchTree.Node<Key> n){
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
  RBNode<Key> getNodeOfKey(RBNode<Key> n, Key key){
    if(comparator.compare(n.getKey(), key) == 0){
      return n;
    }
    else if(n.getLeft() != sentinel && comparator.compare(n.getKey(), key) > 0){
      return getNodeOfKey(n.getLeft(), key);
    }
    else if(n.getRight() != sentinel && comparator.compare(n.getKey(), key) < 0){
      return getNodeOfKey(n.getRight(), key);
    }
    return sentinel;
  }
}
