package org.qhc99.centralibj.algsJ.dataStruc;


import org.qhc99.centralibj.utils.tuples.Tuple;

import java.util.*;

import static org.qhc99.centralibj.algsJ.dataStruc.RBTreeTemplate.RED;

/**
 * order statistic tree
 *
 * @param <K> key
 * @param <V> value
 */
public class OrderStatTree<K, V> implements Iterable<Tuple<K, V>> {
  final Comparator<K> comparator;
  final Node<K, V> sentinel = new Node<>(RBTreeTemplate.BLACK);
  Node<K, V> root = sentinel;
  private boolean iterating = false;
  private final RBTreeTemplate<K, Node<K, V>> template;

  public OrderStatTree(Comparator<K> comparator) {
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
            (n, c) -> n.color = c,
            (current_node, input_node) -> current_node.size++,
            input_node -> input_node.size = 1,
            template -> n -> {
              if (n.right != sentinel) {
                n = minimumNodeOf(n.right);
              }
              var yo = n;
              while (yo != sentinel) {
                yo.size = yo.right.size + yo.left.size + 1;
                yo = yo.parent;
              }
            },
            template -> (node, parent, sentinel) -> {
              parent.size = node.size;
              node.size = node.left.size + node.right.size + 1;
            },
            template -> (node, parent, sentinel) -> {
              parent.size = node.size;
              node.size = node.left.size + node.right.size + 1;
            },
            n -> {
              throw new RuntimeException("duplicate");
            });
  }

  public static <V> OrderStatTree<Integer, V> ofInt() {
    return new OrderStatTree<>(Integer::compareTo);
  }

  public static <V> OrderStatTree<Double, V> ofDouble() {
    return new OrderStatTree<>(Double::compareTo);
  }

  /**
   * 1d range search
   *
   * @param low  low
   * @param high high (inclusive)
   * @return list of key-value in range
   */
  public List<Tuple<K, V>> keyRangeSearch(K low, K high) {
    List<Tuple<K, V>> res = new ArrayList<>();
    if (root == sentinel) {
      return res;
    }
    keyRangeSearch(root, low, high, res);
    return res;
  }

  private void keyRangeSearch(Node<K, V> n, K low, K high, List<Tuple<K, V>> l) {
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

  public Optional<K> floorOfOptKey(K key) {
    if (root == sentinel) {
      return Optional.empty();
    }
    else {
      Node<K, V> x = floor(root, key);
      if (x == sentinel) {
        return Optional.empty();
      }
      else {
        return Optional.of(x.key);
      }
    }
  }

  public K floorOfKey(K key) {
    if (root == sentinel) {
      throw new NoSuchElementException();
    }
    else {
      Node<K, V> x = floor(root, key);
      if (x == sentinel) {
        throw new NoSuchElementException();
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

  public Optional<K> ceilingOfOptKey(K key) {
    if (root == sentinel) {
      return Optional.empty();
    }
    else {
      var x = ceiling(root, key);
      if (x == sentinel) {
        return Optional.empty();
      }
      else {
        return Optional.of(x.key);
      }
    }
  }

  public K ceilingOfKey(K key) {
    if (root == sentinel) {
      throw new NoSuchElementException();
    }
    else {
      var x = ceiling(root, key);
      if (x == sentinel) {
        throw new NoSuchElementException();
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

  public void insertKV(K key, V val) {
    modified();
    var n = new Node<>(key, val);
    template.insert(n);
  }

  public void updateKV(K key, V val) {
    modified();
    var n = (Node<K, V>) template.getNodeOfKey(root, key);
    if (n == sentinel) {
      throw new NoSuchElementException();
    }
    n.value = val;
  }

  public void deleteKey(K key) {
    modified();
    var n = (Node<K, V>) template.getNodeOfKey(root, key);
    if (n != sentinel) {
      template.delete(n);
    }
  }

  public boolean containKey(K key) {
    if (root == sentinel) {
      return false;
    }
    else {
      return sentinel != template.getNodeOfKey(root, key);
    }
  }

  public V getValOfKey(K key) {
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
  public Iterator<Tuple<K, V>> iterator() {
    iterating = true;
    return new BSTIterator<>(sentinel, n -> new Tuple<>(n.key, n.value), () -> this.root, n -> n.right, n -> n.left,
            () -> this.iterating);
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

    Node(key key, val val) {
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