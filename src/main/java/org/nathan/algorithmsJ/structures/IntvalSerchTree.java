package org.nathan.algorithmsJ.structures;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * interval search tree
 */
public class IntvalSerchTree<Key>{
  static class Node<Key> implements RBNode<Key>{
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

    @Override
    public Key getKey(){
      return low;
    }

    @Override
    public RBNode<Key> getParent(){
      return parent;
    }

    @Override
    public void setParent(RBNode<Key> p){
      parent = (Node<Key>) p;
    }

    @Override
    public RBNode<Key> getLeft(){
      return left;
    }

    @Override
    public void setLeft(RBNode<Key> l){
      left = (Node<Key>) l;
    }

    @Override
    public RBNode<Key> getRight(){
      return right;
    }

    @Override
    public void setRight(RBNode<Key> r){
      right = (Node<Key>) r;
    }

    @Override
    public boolean getColor(){
      return color;
    }

    @Override
    public void setColor(boolean color){
      this.color = color;
    }
  }

  @NotNull final Node<Key> sentinel = new Node<>(RBNode.BLACK);
  @NotNull Node<Key> root = sentinel;
  @NotNull final Comparator<Key> comparator;
  @NotNull final RBTreeTemplate<Key> template;

  public IntvalSerchTree(@NotNull Comparator<Key> comparator){
    this.comparator = comparator;
    template = new RBTreeTemplate<>(sentinel,comparator,()->this.root, (r)->this.root= (Node<Key>) r);
  }

  public void insertInterval(@NotNull Key low, @NotNull Key high){
    var n = new Node<>(low, high);
    template.insert(n);

  }

  public void deleteInterval(@NotNull Key low){
    var n = template.getNodeOfKey(root,low);
    if(n == sentinel){
      throw new NoSuchElementException();
    }
    template.delete(n);
  }
}
