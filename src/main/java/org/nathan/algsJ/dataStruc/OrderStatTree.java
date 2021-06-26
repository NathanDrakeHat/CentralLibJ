package org.nathan.algsJ.dataStruc;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;

import static org.nathan.algsJ.dataStruc.RBTreeTemplate.RED;

/**
 * order statistic tree
 *
 * @param <K> key
 * @param <V> value
 */
public class OrderStatTree<K, V> implements Iterable<Tuple<K, V>> {
  @NotNull
  final Comparator<K> comparator;
  @NotNull
  final Node<K, V> sentinel = new Node<>(RBTreeTemplate.BLACK);
  @NotNull Node<K, V> root = sentinel;
  private boolean iterating = false;
  @NotNull
  private final RBTreeTemplate<K, Node<K, V>> template;

  public OrderStatTree(@NotNull Comparator<K> comparator) {
    this.comparator = comparator;
    template = new RBTreeTemplate<>(
            sentinel, comparator,
            n -> n.key,
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

  /**
   * 1d range search
   *
   * @param low  low
   * @param high high (inclusive)
   * @return list of key-value in range
   */
  public List<Tuple<K, V>> keyRangeSearch(@NotNull K low, @NotNull K high) {
    List<Tuple<K, V>> res = new ArrayList<>();
    if (root == sentinel) {
      return res;
    }
    keyRangeSearch(root, low, high, res);
    return res;
  }

  private void keyRangeSearch(Node<K, V> n, @NotNull K low, @NotNull K high, List<Tuple<K, V>> l) {
    if (n == sentinel) {
      return;
    }

    if (comparator.compare(n.key, low) > 0) {
      keyRangeSearch(n.left, low, high, l);
    }

    if (comparator.compare(n.key, low) >= 0 && comparator.compare(n.key, high) <= 0) {
      l.add(new Tuple<>(n.key, n.value));
    }

    if (comparator.compare(n.key, high) < 0) {
      keyRangeSearch(n.right, low, high, l);
    }
  }

  public V getValueOfMinKey() {
    if (sentinel != root) {
      return minimumNodeOf(root).value;
    }
    else {
      throw new NoSuchElementException("null tree");
    }
  }

  public K getMinKey() {
    if (sentinel != root) {
      return minimumNodeOf(root).key;
    }
    else {
      throw new NoSuchElementException("null tree");
    }
  }

  public V getValueOfMaxKey() {
    if (root != sentinel) {
      return maximumNodeOf(root).value;
    }
    else {
      throw new NoSuchElementException("null tree");
    }
  }

  public K getMaxKey() {
    if (root != sentinel) {
      return maximumNodeOf(root).key;
    }
    else {
      throw new NoSuchElementException("null tree");
    }
  }

  public K floorOfKey(K key) {
    if (root == sentinel) {
      throw new NoSuchElementException("calls floor() with empty symbol table");
    }
    else {
      Node<K, V> x = floor(root, key);
      if (x == sentinel) {
        throw new NoSuchElementException("argument to floor() is too small");
      }
      else {
        return x.key;
      }
    }
  }

  private Node<K, V> floor(Node<K, V> x, K key) {
    if (x == sentinel) {
      return sentinel;
    }
    else {
      int cmp = comparator.compare(key, x.key);
      if (cmp == 0) {
        return x;
      }
      else if (cmp < 0) {
        return floor(x.left, key);
      }
      else {
        var t = floor(x.right, key);
        return t != sentinel ? t : x;
      }
    }
  }

  public K ceilingOfKey(K key) {
    if (root == sentinel) {
      throw new NoSuchElementException("calls ceiling() with empty symbol table");
    }
    else {
      var x = ceiling(root, key);
      if (x == sentinel) {
        throw new NoSuchElementException("argument to ceiling() is too small");
      }
      else {
        return x.key;
      }
    }
  }

  private Node<K, V> ceiling(Node<K, V> x, K key) {
    if (x == sentinel) {
      return sentinel;
    }
    else {
      int cmp = comparator.compare(key, x.key);
      if (cmp == 0) {
        return x;
      }
      else if (cmp > 0) {
        return ceiling(x.right, key);
      }
      else {
        var t = ceiling(x.left, key);
        return t != sentinel ? t : x;
      }
    }
  }

  public K getKeyOfRank(int rank) {
    if (rank <= 0 || rank > size()) {
      throw new IndexOutOfBoundsException();
    }
    Node<K, V> n = getNodeOfRank(rank);
    return n.key;
  }

  Node<K, V> getNodeOfRank(int ith) {
    return getNodeOfRank(root, ith);
  }

  Node<K, V> getNodeOfRank(Node<K, V> current, int ith) {
    int rank = current.left.size + 1;
    if (rank == ith) {
      return current;
    }
    else if (ith < rank) {
      return getNodeOfRank(current.left, ith);
    }
    else {
      return getNodeOfRank(current.right, ith - rank);
    }
  }

  public int getRankOfKey(K key) {
    Node<K, V> n = template.getNodeOfKey(root, key);
    if (n == sentinel) {
      throw new NoSuchElementException();
    }
    return getRankOfNode(n);
  }

  int getRankOfNode(Node<K, V> node) {
    int rank = node.left.size + 1;
    while (node != root) {
      if (node == node.parent.right) {
        rank += node.parent.left.size + 1;
      }
      node = node.parent;
    }
    return rank;
  }

  public int size() {
    return root.size;
  }

  public int getHeight() {
    if (root == sentinel) {
      return 0;
    }
    int height = 1;
    int left_max = getHeight(root.left, height);
    int right_max = getHeight(root.right, height);
    return Math.max(left_max, right_max);
  }

  private int getHeight(Node<K, V> n, int height) {
    if (n != sentinel) {
      int left_max = getHeight(n.left, height + 1);
      int right_max = getHeight(n.right, height + 1);
      return Math.max(left_max, right_max);
    }
    else {
      return height;
    }
  }

  public void insertKV(@NotNull K key, V val) {
    modified();
    var n = new Node<>(key, val);
    template.insert(n);
  }

  public void updateKV(@NotNull K key, V val) {
    modified();
    var n = (Node<K, V>) template.getNodeOfKey(root, key);
    if (n == sentinel) {
      throw new NoSuchElementException();
    }
    n.value = val;
  }

  public void deleteKey(@NotNull K key) {
    modified();
    var n = (Node<K, V>) template.getNodeOfKey(root, key);
    if (n != sentinel) {
      template.delete(n);
    }
    else {
      throw new NoSuchElementException();
    }
  }

  public boolean containKey(@NotNull K key) {
    if (root == sentinel) {
      return false;
    }
    else return sentinel != template.getNodeOfKey(root, key);
  }

  public V getValOfKey(@NotNull K key) {
    if (root == sentinel) {
      throw new NoSuchElementException();
    }
    var res = template.getNodeOfKey(root, key).value;
    if (res == sentinel) {
      throw new NoSuchElementException();
    }
    return res;
  }

  private Node<K, V> minimumNodeOf(Node<K, V> x) {
    while (x.left != sentinel) {
      x = x.left;
    }
    return x;
  }

  private Node<K, V> maximumNodeOf(Node<K, V> x) {
    while (x.right != sentinel) {
      x = x.right;
    }
    return x;
  }

  @Override
  public @NotNull Iterator<Tuple<K, V>> iterator() {
    iterating = true;
    return new BSTIterator<>(sentinel, n -> new Tuple<>(n.key, n.value), () -> this.root, n -> n.right, n -> n.left, () -> this.iterating);
  }


  private void modified() {
    iterating = false;
  }

  static final class Node<key, val> {
    key key;
    val value;
    boolean color;
    Node<key, val> parent;
    Node<key, val> left;
    Node<key, val> right;
    int size;

    Node(boolean color) {
      this.color = color;
    }

    Node(@NotNull key key, val val) {
      color = RED;
      this.key = key;
      this.value = val;
    }

    @Override
    public String toString() {
      return "Node{" +
              "key=" + key +
              ", value=" + value +
              ", size=" + size +
              '}';
    }
  }
}