package org.nathan.algorithmsJ.structures;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;

/**
 * interval search tree
 */
public class IntvalSerchTree<Key> implements Iterable<Tuple<Key,Key>>{
  @NotNull
  final Node<Key> sentinel = new Node<>(RBTreeTemplate.BLACK);
  @NotNull Node<Key> root = sentinel;
  @NotNull
  final Comparator<Key> comparator;
  @NotNull
  final RBTreeTemplate<Key, Node<Key>> template;
  private boolean iterating;

  private void modified(){
    iterating = false;
  }

  public IntvalSerchTree(@NotNull Comparator<Key> comparator){
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

  public void insertInterval(@NotNull Key low, @NotNull Key high){
    modified();
    var n = new Node<>(low, high);
    template.insert(n);
  }

  public void deleteInterval(@NotNull Key low){
    modified();
    var n = template.getNodeOfKey(root, low);
    if(n == sentinel){
      throw new NoSuchElementException();
    }
    template.delete(n);
  }

  @NotNull
  @Override
  public Iterator<Tuple<Key,Key>> iterator(){
    iterating = true;
    return new BSTIterator<>(sentinel,n->new Tuple<>(n.low, n.high), ()->this.root, n->n.right, n->n.left,()->this.iterating);
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
}
