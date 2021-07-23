package org.nathan.centralibj.algsJ.dataStruc;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralibj.utils.tuples.Triad;

import java.util.*;

/**
 * interval search tree
 */
public class IntvalSerchTree<Key, Val> implements Iterable<Triad<Key, Key, Val>>{
  @NotNull
  final Node<Key, Val> sentinel = new Node<>(RBTreeTemplate.BLACK);
  @NotNull Node<Key, Val> root = sentinel;
  @NotNull
  final Comparator<Key> comparator;
  @NotNull
  final RBTreeTemplate<Key, Node<Key, Val>> template;
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

  public void insertInterval(@NotNull Key low, @NotNull Key high, Val val){
    modified();
    Node<Key, Val> n = new Node<>(low, high, val);
    template.insert(n);
  }

  public void deleteInterval(@NotNull Key low){
    modified();
    var n = template.getNodeOfKey(root, low);
    if(n != sentinel){
      template.delete(n);
    }
  }

  public List<Triad<Key, Key, Val>> intersects(@NotNull Key lo, @NotNull Key high){
    List<Triad<Key, Key, Val>> res = new ArrayList<>();
    var funcIntersect = new Object(){
      boolean apply(Key lo, Key hi, Key k){
        return comparator.compare(lo, k) <= 0 && comparator.compare(hi, k) >= 0;
      }
    };
    var func = new Object(){
      void apply(Node<Key, Val> n){
        if(n == sentinel){
          return;
        }
        else if(funcIntersect.apply(n.low, n.high, lo) || funcIntersect.apply(n.low, n.high, high)){
          res.add(new Triad<>(n.low, n.high, n.value));
        }
        else if(n.left == sentinel || comparator.compare(n.left.max, lo) < 0){
          apply(n.right);
        }
        else{
          apply(n.left);
        }
      }
    };
    func.apply(root);
    return res;
  }

  @NotNull
  @Override
  public Iterator<Triad<Key, Key, Val>> iterator(){
    iterating = true;
    return new BSTIterator<>(sentinel, n -> new Triad<>(n.low, n.high, n.value), () -> this.root, n -> n.right,
            n -> n.left, () -> this.iterating);
  }

  static class Node<Key, Val>{
    Node<Key, Val> parent;
    Node<Key, Val> left;
    Node<Key, Val> right;
    Key low;
    Key high;
    Key max;
    Val value;
    boolean color;

    public Node(boolean c){
      color = c;
    }

    public Node(Key lo, Key hi, Val val){
      low = lo;
      high = hi;
      max = high;
      value = val;
    }
  }
}
