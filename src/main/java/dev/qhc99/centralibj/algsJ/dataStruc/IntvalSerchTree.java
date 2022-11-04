package dev.qhc99.centralibj.algsJ.dataStruc;


import dev.qhc99.centralibj.utils.tuples.Triad;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * interval search tree
 */
public class IntvalSerchTree<Key, Val> implements Iterable<Triad<Key, Key, Val>> {
  
  final Node<Key, Val> sentinel = new Node<>(RBTreeTemplate.BLACK);
   Node<Key, Val> root = sentinel;
  
  final Comparator<Key> comparator;
  
  final RBTreeTemplate<Key, Node<Key, Val>> template;
  private boolean iterating;

  private void modified() {
    iterating = false;
  }

  public IntvalSerchTree( Comparator<Key> comparator) {
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
            (n, c) -> n.color = c,
            (current_node, input_node) -> current_node.max = comparator.compare(current_node.max, input_node.max) < 0 ? input_node.max : current_node.max,
            null,
            template -> (n) -> {
              var p = n;
              if (p.right != sentinel) {
                p = template.minimumNodeOf(p.right);
              }

              while (p != sentinel) {
                updateIntvalSerchNode(p);
                p = p.parent;
              }
            },
            template -> (node, parent, sentinel) -> {
              updateIntvalSerchNode(node);
              node = node.parent;
              if (node != sentinel) {
                updateIntvalSerchNode(node);
              }
            },
            template -> (node, parent, sentinel) -> {
              updateIntvalSerchNode(node);
              node = node.parent;
              if (node != sentinel) {
                updateIntvalSerchNode(node);
              }
            },
            n->{
              throw new RuntimeException("duplicate");
            });

  }

  void updateIntvalSerchNode(IntvalSerchTree.Node<Key, ?> n) {
    if (n.left != sentinel && n.right != sentinel) {
      n.max = comparator.compare(n.right.max, n.left.max) < 0 ? n.left.max : n.right.max;
      n.max = comparator.compare(n.max, n.high) < 0 ? n.high : n.max;
    }
    else if (n.left != sentinel) {
      n.max = comparator.compare(n.high, n.left.max) < 0 ? n.left.max : n.high;
    }
    else if (n.right != sentinel) {
      n.max = comparator.compare(n.high, n.right.max) < 0 ? n.right.max : n.high;
    }
    else {
      n.max = n.high;
    }
  }

  public static <V> IntvalSerchTree<Integer, V> ofInt() {
    return new IntvalSerchTree<>(Integer::compareTo);
  }

  public static <V> IntvalSerchTree<Double, V> ofDouble() {
    return new IntvalSerchTree<>(Double::compareTo);
  }

  public void insertInterval( Key low,  Key high, Val val) {
    modified();
    Node<Key, Val> n = new Node<>(low, high, val);
    template.insert(n);
  }

  public void deleteInterval( Key low) {
    modified();
    var n = template.getNodeOfKey(root, low);
    if (n != sentinel) {
      template.delete(n);
    }
  }

  public List<Triad<Key, Key, Val>> intersects( Key lo,  Key high) {
    List<Triad<Key, Key, Val>> res = new ArrayList<>();
    var funcIntersect = new Object() {
      boolean apply(Key lo, Key hi, Key k) {
        return comparator.compare(lo, k) <= 0 && comparator.compare(hi, k) >= 0;
      }
    };
    var func = new Object() {
      void apply(Node<Key, Val> n) {
        if (n == sentinel) {
          return;
        }
        else if (funcIntersect.apply(n.low, n.high, lo) || funcIntersect.apply(n.low, n.high, high)) {
          res.add(new Triad<>(n.low, n.high, n.value));
        }
        else if (n.left == sentinel || comparator.compare(n.left.max, lo) < 0) {
          apply(n.right);
        }
        else {
          apply(n.left);
        }
      }
    };
    func.apply(root);
    return res;
  }

  
  @Override
  public Iterator<Triad<Key, Key, Val>> iterator() {
    iterating = true;
    return new BSTIterator<>(sentinel, n -> new Triad<>(n.low, n.high, n.value), () -> this.root, n -> n.right,
            n -> n.left, () -> this.iterating);
  }

  static class Node<Key, Val> {
    Node<Key, Val> parent;
    Node<Key, Val> left;
    Node<Key, Val> right;
    Key low;
    Key high;
    Key max;
    Val value;
    boolean color;

    public Node(boolean c) {
      color = c;
    }

    public Node(Key lo, Key hi, Val val) {
      low = lo;
      high = hi;
      max = high;
      value = val;
    }
  }
}
