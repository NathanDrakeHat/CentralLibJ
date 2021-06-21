package org.nathan.algsJ.structures;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.utils.LambdaUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.nathan.centralUtils.utils.LambdaUtils.stripCE;


/**
 * red black tree code template
 *
 * @param <Key> key of node
 */
class RBTreeTemplate<Key, Node> {
  /**
   * annotate which function to augment new operation
   */
  @Target(ElementType.METHOD)
  @interface Template {

  }

  final @NotNull Node sentinel;
  final @NotNull Comparator<Key> comparator;
  final @NotNull LambdaUtils.Gettable<Node> getRoot;
  final @NotNull Consumer<Node> setRoot;
  static final boolean RED = false;
  static final boolean BLACK = true;
  @NotNull
  final Function<Node, Node> getParent;
  @NotNull
  final BiConsumer<Node, Node> setParent;
  @NotNull
  final Function<Node, Node> getLeft;
  @NotNull
  final BiConsumer<Node, Node> setLeft;
  @NotNull
  final Function<Node, Node> getRight;
  @NotNull
  final BiConsumer<Node, Node> setRight;
  @NotNull
  final Function<Node, Boolean> getColor;
  @NotNull
  final BiConsumer<Node, Boolean> setColor;
  @NotNull
  final Function<Node, Key> getKey;

  RBTreeTemplate(@NotNull Node sentinel,
                 @NotNull Comparator<Key> comparator,
                 @NotNull Function<Node, Key> getKey,
                 @NotNull LambdaUtils.Gettable<Node> getRoot,
                 @NotNull Consumer<Node> setRoot,
                 @NotNull Function<Node, Node> getParent,
                 @NotNull BiConsumer<Node, Node> setParent,
                 @NotNull Function<Node, Node> getLeft,
                 @NotNull BiConsumer<Node, Node> setLeft,
                 @NotNull Function<Node, Node> getRight,
                 @NotNull BiConsumer<Node, Node> setRight,
                 @NotNull Function<Node, Boolean> getColor,
                 @NotNull BiConsumer<Node, Boolean> setColor) {
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

  /**
   * insert a node <b>WITHOUT</b> duplicate key
   *
   * @param z node or sentinel
   */
  @Template
  @SuppressWarnings({"SuspiciousNameCombination", "unchecked"})
  void insert(Node z) {
    var y = sentinel;
    var x = getRoot.get();
    while (x != sentinel) {
      y = x;
      {//
        if (x instanceof OrderStatTree.Node<?, ?> xo) {
          xo.size++;
        } else if (x instanceof IntvalSerchTree.Node<?>) {
          var xi = (IntvalSerchTree.Node<Key>) x;
          var zi = (IntvalSerchTree.Node<Key>) z;
          xi.max = comparator.compare(xi.max, zi.max) < 0 ? zi.max : xi.max;
        }
      }
      if (comparator.compare(getKey.apply(z), getKey.apply(x)) < 0) {
        x = getLeft.apply(x);
      } else if (comparator.compare(getKey.apply(z), getKey.apply(x)) > 0) {
        x = getRight.apply(x);
      } else {
        throw new IllegalArgumentException("duplicate key.");
      }
    }
    setParent.accept(z, y);
    if (y == sentinel) {
      setRoot.accept(z);
    } else if (comparator.compare(getKey.apply(z), getKey.apply(y)) < 0) {
      setLeft.accept(y, z);
    } else if (comparator.compare(getKey.apply(z), getKey.apply(y)) > 0) {
      setRight.accept(y, z);
    } else {
      throw new RuntimeException("impossible error.");
    }
    setLeft.accept(z, sentinel);
    setRight.accept(z, sentinel);
    setColor.accept(z, RED);
    {//
      if (z instanceof OrderStatTree.Node<?, ?> zo) {
        zo.size = 1;
      }
    }
    insertFixUp(z);
  }

  private void insertFixUp(Node z) {
    while (getColor.apply(getParent.apply(z)) == RED) {
      if (getParent.apply(z) == getLeft.apply(getParent.apply(getParent.apply(z)))) {
        var y = getRight.apply(getParent.apply(getParent.apply(z)));
        if (getColor.apply(y) == RED) {
          setColor.accept(getParent.apply(z), BLACK);
          setColor.accept(y, BLACK);
          setColor.accept(getParent.apply(getParent.apply(z)), RED);
          z = getParent.apply(getParent.apply(z));
        } else {
          if (z == getRight.apply(getParent.apply(z))) {
            z = getParent.apply(z);
            leftRotate(z);
          }
          setColor.accept(getParent.apply(z), BLACK);
          setColor.accept(getParent.apply(getParent.apply(z)), RED);
          rightRotate(getParent.apply(getParent.apply(z)));
        }
      } else {
        var y = getLeft.apply(getParent.apply(getParent.apply(z)));
        if (getColor.apply(y) == RED) {
          setColor.accept(getParent.apply(z), BLACK);
          setColor.accept(y, BLACK);
          setColor.accept(getParent.apply(getParent.apply(z)), RED);
          z = getParent.apply(getParent.apply(z));
        } else {
          if (z == getLeft.apply(getParent.apply(z))) {
            z = getParent.apply(z);
            rightRotate(z);
          }
          setColor.accept(getParent.apply(z), BLACK);
          setColor.accept(getParent.apply(getParent.apply(z)), RED);
          leftRotate(getParent.apply(getParent.apply(z)));
        }
      }
    }
    setColor.accept(getRoot.get(), BLACK);
  }

  @SuppressWarnings("unchecked")
  @Template
  void delete(Node z) {
    var y = z;
    var y_origin_color = getColor.apply(y);
    Node x;
    if (getLeft.apply(z) == sentinel) {
      x = getRight.apply(z);
      RBTransplant(z, getRight.apply(z));
    } else if (getRight.apply(z) == sentinel) {
      x = getLeft.apply(z);
      RBTransplant(z, getLeft.apply(z));
    } else {
      y = minimumNodeOf(getRight.apply(z));
      y_origin_color = getColor.apply(y);
      x = getRight.apply(y);
      if (getParent.apply(y) == z) {
        setParent.accept(x, y);
      } else {
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
      if (y instanceof OrderStatTree.Node<?, ?>) {
        if (getRight.apply(y) != sentinel) {
          y = minimumNodeOf(getRight.apply(y));
        }
        //noinspection PatternVariableCanBeUsed
        var yo = (OrderStatTree.Node<?, ?>) y;
        while (yo != sentinel) {
          yo.size = yo.right.size + yo.left.size + 1;
          yo = yo.parent;
        }
      } else if (y instanceof IntvalSerchTree.Node<?>) {
        var p = y;
        if (getRight.apply(p) != sentinel) {
          p = minimumNodeOf(getRight.apply(p));
        }
        var pi = (IntvalSerchTree.Node<Key>) p;
        while (pi != sentinel) {
          updateIntvalSerchNode(pi);
          pi = pi.parent;
        }
      }
    }

    if (y_origin_color == BLACK) {
      deleteFixUp(x);
    }
  }

  private void deleteFixUp(Node x) {
    while (x != getRoot.get() && getColor.apply(x) == BLACK) {
      if (x == getLeft.apply(getParent.apply(x))) {
        var w = getRight.apply(getParent.apply(x));
        if (getColor.apply(w) == RED) {
          setColor.accept(w, BLACK);
          setColor.accept(getParent.apply(x), RED);
          leftRotate(getParent.apply(x));
          w = getRight.apply(getParent.apply(x));
        }
        if (getColor.apply(getLeft.apply(w)) == BLACK && getColor.apply(getRight.apply(w)) == BLACK) {
          setColor.accept(w, RED);
          x = getParent.apply(x);
        } else {
          if (getColor.apply(getRight.apply(w)) == BLACK) {
            setColor.accept(getLeft.apply(w), BLACK);
            setColor.accept(w, RED);
            rightRotate(w);
            w = getRight.apply(getParent.apply(x));
          }
          setColor.accept(w, getColor.apply(getParent.apply(x)));
          setColor.accept(getParent.apply(x), BLACK);
          setColor.accept(getRight.apply(w), BLACK);
          leftRotate(getParent.apply(x));
          x = getRoot.get();
        }
      } else {
        var w = getLeft.apply(getParent.apply(x));
        if (getColor.apply(w) == RED) {
          setColor.accept(w, BLACK);
          setColor.accept(getParent.apply(x), RED);
          rightRotate(getParent.apply(x));
          w = getLeft.apply(getParent.apply(x));
        }
        if (getColor.apply(getLeft.apply(w)) == BLACK && getColor.apply(getRight.apply(w)) == BLACK) {
          setColor.accept(w, RED);
          x = getParent.apply(x);
        } else {
          if (getColor.apply(getLeft.apply(w)) == BLACK) {
            setColor.accept(getRight.apply(w), BLACK);
            setColor.accept(w, RED);
            leftRotate(w);
            w = getLeft.apply(getParent.apply(x));
          }
          setColor.accept(w, getColor.apply(getParent.apply(x)));
          setColor.accept(getParent.apply(x), BLACK);
          setColor.accept(getLeft.apply(w), BLACK);
          rightRotate(getParent.apply(x));
          x = getRoot.get();
        }
      }
    }
    setColor.accept(x, BLACK);
  }

  private Node minimumNodeOf(Node x) {
    while (getLeft.apply(x) != sentinel) {
      x = getLeft.apply(x);
    }
    return x;
  }

  private void RBTransplant(Node u, Node v) {
    if (getParent.apply(u) == sentinel) {
      setRoot.accept(v);
    } else if (u == getLeft.apply(getParent.apply(u))) {
      setLeft.accept(getParent.apply(u), v);
    } else {
      setRight.accept(getParent.apply(u), v);
    }
    setParent.accept(v, getParent.apply(u));
  }

  @SuppressWarnings("unchecked")
  @Template
  private void leftRotate(Node x) {
    var y = getRight.apply(x);

    setRight.accept(x, getLeft.apply(y));
    if (getLeft.apply(y) != sentinel) {
      setParent.accept(getLeft.apply(y), x);
    }

    setParent.accept(y, getParent.apply(x));
    if (getParent.apply(x) == sentinel) {
      setRoot.accept(y);
    } else if (x == getLeft.apply(getParent.apply(x))) {
      setLeft.accept(getParent.apply(x), y);
    } else {
      setRight.accept(getParent.apply(x), y);
    }

    setLeft.accept(y, x);
    setParent.accept(x, y);
    {//
      if (x instanceof OrderStatTree.Node<?, ?> xo && y instanceof OrderStatTree.Node<?, ?> yo) {
        yo.size = xo.size;
        xo.size = xo.left.size + xo.right.size + 1;
      } else if (x instanceof IntvalSerchTree.Node<?>) {
        var xi = (IntvalSerchTree.Node<Key>) x;
        updateIntvalSerchNode(xi);
        xi = xi.parent;
        if (xi != sentinel) {
          updateIntvalSerchNode(xi);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Template
  private void rightRotate(Node x) {
    var y = getLeft.apply(x);

    setLeft.accept(x, getRight.apply(y));
    if (getRight.apply(y) != sentinel) {
      setParent.accept(getRight.apply(y), x);
    }

    setParent.accept(y, getParent.apply(x));
    if (getParent.apply(x) == sentinel) {
      setRoot.accept(y);
    } else if (x == getRight.apply(getParent.apply(x))) {
      setRight.accept(getParent.apply(x), y);
    } else {
      setLeft.accept(getParent.apply(x), y);
    }

    setRight.accept(y, x);
    setParent.accept(x, y);
    {//
      if (x instanceof OrderStatTree.Node<?, ?> xo && y instanceof OrderStatTree.Node<?, ?> yo) {
        yo.size = xo.size;
        xo.size = xo.left.size + xo.right.size + 1;
      } else if (x instanceof IntvalSerchTree.Node<?>) {
        var xi = (IntvalSerchTree.Node<Key>) x;
        updateIntvalSerchNode(xi);
        xi = xi.parent;
        if (xi != sentinel) {
          updateIntvalSerchNode(xi);
        }
      }
    }
  }

  private void updateIntvalSerchNode(IntvalSerchTree.Node<Key> n) {
    if (n.left != sentinel && n.right != sentinel) {
      n.max = comparator.compare(n.right.max, n.left.max) < 0 ? n.left.max : n.right.max;
      n.max = comparator.compare(n.max, n.high) < 0 ? n.high : n.max;
    } else if (n.left != sentinel) {
      n.max = comparator.compare(n.high, n.left.max) < 0 ? n.left.max : n.high;
    } else if (n.right != sentinel) {
      n.max = comparator.compare(n.high, n.right.max) < 0 ? n.right.max : n.high;
    } else {
      n.max = n.high;
    }
  }

  /**
   * @param n   node
   * @param key key
   * @return node with key or sentinel
   */
  Node getNodeOfKey(Node n, Key key) {
    if (comparator.compare(getKey.apply(n), key) == 0) {
      return n;
    } else if (getLeft.apply(n) != sentinel && comparator.compare(getKey.apply(n), key) > 0) {
      return getNodeOfKey(getLeft.apply(n), key);
    } else if (getRight.apply(n) != sentinel && comparator.compare(getKey.apply(n), key) < 0) {
      return getNodeOfKey(getRight.apply(n), key);
    }
    return sentinel;
  }
}
