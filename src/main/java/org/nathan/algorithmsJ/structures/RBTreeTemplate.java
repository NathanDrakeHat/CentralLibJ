package org.nathan.algorithmsJ.structures;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Function;

record RBTreeTemplate<Key, Tree>(@NotNull RBNode<Key> sentinel,
                                 @NotNull Comparator<Key> comparator, @NotNull Tree tree,
                                 @NotNull Function<Tree, RBNode<Key>> getRoot,
                                 @NotNull BiConsumer<Tree, RBNode<Key>> setRoot){
  RBTreeTemplate(
          @NotNull RBNode<Key> sentinel,
          @NotNull Comparator<Key> comparator,
          @NotNull Tree tree,
          @NotNull Function<Tree, RBNode<Key>> getRoot,
          @NotNull BiConsumer<Tree, RBNode<Key>> setRoot){
    this.sentinel = sentinel;
    this.comparator = comparator;
    this.tree = tree;
    this.getRoot = getRoot;
    this.setRoot = setRoot;
  }

  @SuppressWarnings("SuspiciousNameCombination")
  void insert(RBNode<Key> z){
    var y = sentinel;
    var x = getRoot.apply(tree);
    while(x != sentinel) {
      y = x;
      if(x instanceof OSTree.Node<?, ?> xo){
        xo.size++;
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
      setRoot.accept(tree, z);
    }
    else if(comparator.compare(z.getKey(), y.getKey()) < 0){
      y.setLeft(z);
    }
    else if(comparator.compare(z.getKey(), y.getKey()) > 0){
      y.setRight(z);
    }
    else{
      throw new RuntimeException("not possible error.");
    }
    z.setLeft(sentinel);
    z.setRight(sentinel);
    z.setRed();
    if(z instanceof OSTree.Node<?, ?> zo){
      zo.size = 1;
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
    getRoot.apply(tree).setBlack();
  }

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

    if(y.getRight() != sentinel){
      y = minimumNodeOf(y.getRight());
    }
    while(y != sentinel && y instanceof OSTree.Node<?, ?> yo) {
      yo.size = yo.right.size + yo.left.size + 1;
      y = y.getParent();
    }

    if(y_origin_color == RBNode.BLACK){
      deleteFixUp(x);
    }
  }

  private void deleteFixUp(RBNode<Key> x){
    while(x != getRoot.apply(tree) && x.isBlack()) {
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
          x = getRoot.apply(tree);
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
          x = getRoot.apply(tree);
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
      setRoot.accept(tree, v);
    }
    else if(u == u.getParent().getLeft()){
      u.getParent().setLeft(v);
    }
    else{
      u.getParent().setRight(v);
    }
    v.setParent(u.getParent());
  }

  private void leftRotate(RBNode<Key> x){
    var y = x.getRight();

    x.setRight(y.getLeft());
    if(y.getLeft() != sentinel){
      y.getLeft().setParent(x);
    }

    y.setParent(x.getParent());
    if(x.getParent() == sentinel){
      setRoot.accept(tree, y);
    }
    else if(x == x.getParent().getLeft()){
      x.getParent().setLeft(y);
    }
    else{
      x.getParent().setRight(y);
    }

    y.setLeft(x);
    x.setParent(y);
    if(x instanceof OSTree.Node<?, ?> xo && y instanceof OSTree.Node<?, ?> yo){
      yo.size = xo.size;
      xo.size = xo.left.size + xo.right.size + 1;
    }
  }

  private void rightRotate(RBNode<Key> x){
    var y = x.getLeft();

    x.setLeft(y.getRight());
    if(y.getRight() != sentinel){
      y.getRight().setParent(x);
    }

    y.setParent(x.getParent());
    if(x.getParent() == sentinel){
      setRoot.accept(tree, y);
    }
    else if(x == x.getParent().getRight()){
      x.getParent().setRight(y);
    }
    else{
      x.getParent().setLeft(y);
    }

    y.setRight(x);
    x.setParent(y);

    if(x instanceof OSTree.Node<?, ?> xo && y instanceof OSTree.Node<?, ?> yo){
      yo.size = xo.size;
      xo.size = xo.left.size + xo.right.size + 1;
    }
  }
}
