package org.nathan.AlgorithmsJava.structures;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

// TODO read book
@SuppressWarnings("unused")
public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private RedBlackBST<Key, Value>.Node root;

    public RedBlackBST() {
    }

    private boolean isRed(RedBlackBST<Key, Value>.Node x) {
        if (x == null) {
            return false;
        }
        else {
            return x.color;
        }
    }

    private int size(RedBlackBST<Key, Value>.Node x) {
        return x == null ? 0 : x.size;
    }

    public int size() {
        return size(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        else {
            return get(root, key);
        }
    }

    private Value get(RedBlackBST<Key, Value>.Node x, Key key) {
        while (true) {
            if (x != null) {
                int cmp = key.compareTo(x.key);
                if (cmp < 0) {
                    x = x.left;
                    continue;
                }

                if (cmp > 0) {
                    x = x.right;
                    continue;
                }

                return x.val;
            }

            return null;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        else if (val == null) {
            delete(key);
        }
        else {
            root = put(root, key, val);
            root.color = false;
        }
    }

    private RedBlackBST<Key, Value>.Node put(RedBlackBST<Key, Value>.Node h, Key key, Value val) {
        if (h == null) {
            return new RedBlackBST<Key, Value>.Node(key, val, true, 1);
        }
        else {
            int cmp = key.compareTo(h.key);
            if (cmp < 0) {
                h.left = put(h.left, key, val);
            }
            else if (cmp > 0) {
                h.right = put(h.right, key, val);
            }
            else {
                h.val = val;
            }

            if (isRed(h.right) && !isRed(h.left)) {
                h = rotateLeft(h);
            }

            if (isRed(h.left) && isRed(h.left.left)) {
                h = rotateRight(h);
            }

            if (isRed(h.left) && isRed(h.right)) {
                flipColors(h);
            }

            h.size = size(h.left) + size(h.right) + 1;
            return h;
        }
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("BST underflow");
        }
        else {
            if (!isRed(root.left) && !isRed(root.right)) {
                root.color = true;
            }

            root = deleteMin(root);
            if (!isEmpty()) {
                root.color = false;
            }

        }
    }

    private RedBlackBST<Key, Value>.Node deleteMin(RedBlackBST<Key, Value>.Node h) {
        if (h.left == null) {
            return null;
        }
        else {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }

            h.left = deleteMin(h.left);
            return balance(h);
        }
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("BST underflow");
        }
        else {
            if (!isRed(root.left) && !isRed(root.right)) {
                root.color = true;
            }

            root = deleteMax(root);
            if (!isEmpty()) {
                root.color = false;
            }

        }
    }

    private RedBlackBST<Key, Value>.Node deleteMax(RedBlackBST<Key, Value>.Node h) {
        if (isRed(h.left)) {
            h = rotateRight(h);
        }

        if (h.right == null) {
            return null;
        }
        else {
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }

            h.right = deleteMax(h.right);
            return balance(h);
        }
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        else if (contains(key)) {
            if (!isRed(root.left) && !isRed(root.right)) {
                root.color = true;
            }

            root = delete(root, key);
            if (!isEmpty()) {
                root.color = false;
            }

        }
    }

    private RedBlackBST<Key, Value>.Node delete(RedBlackBST<Key, Value>.Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }

            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }

            if (key.compareTo(h.key) == 0 && h.right == null) {
                return null;
            }

            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }

            if (key.compareTo(h.key) == 0) {
                RedBlackBST<Key, Value>.Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            }
            else {
                h.right = delete(h.right, key);
            }
        }

        return balance(h);
    }

    private RedBlackBST<Key, Value>.Node rotateRight(RedBlackBST<Key, Value>.Node h) {
        RedBlackBST<Key, Value>.Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = true;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    private RedBlackBST<Key, Value>.Node rotateLeft(RedBlackBST<Key, Value>.Node h) {
        RedBlackBST<Key, Value>.Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = true;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColors(RedBlackBST<Key, Value>.Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private RedBlackBST<Key, Value>.Node moveRedLeft(RedBlackBST<Key, Value>.Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }

        return h;
    }

    private RedBlackBST<Key, Value>.Node moveRedRight(RedBlackBST<Key, Value>.Node h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }

        return h;
    }

    private RedBlackBST<Key, Value>.Node balance(RedBlackBST<Key, Value>.Node h) {
        if (isRed(h.right)) {
            h = rotateLeft(h);
        }

        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }

        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    public int height() {
        return height(root);
    }

    private int height(RedBlackBST<Key, Value>.Node x) {
        return x == null ? -1 : 1 + Math.max(height(x.left), height(x.right));
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {
            return min(root).key;
        }
    }

    private RedBlackBST<Key, Value>.Node min(RedBlackBST<Key, Value>.Node x) {
        return x.left == null ? x : min(x.left);
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {
            return max(root).key;
        }
    }

    private RedBlackBST<Key, Value>.Node max(RedBlackBST<Key, Value>.Node x) {
        return x.right == null ? x : max(x.right);
    }

    public Key floor(@NotNull Key key) {
        if (isEmpty()) {
            throw new NoSuchElementException("calls floor() with empty symbol table");
        }
        else {
            RedBlackBST<Key, Value>.Node x = floor(root, key);
            if (x == null) {
                throw new NoSuchElementException("argument to floor() is too small");
            }
            else {
                return x.key;
            }
        }
    }

    private RedBlackBST<Key, Value>.Node floor(RedBlackBST<Key, Value>.Node x, Key key) {
        if (x == null) {
            return null;
        }
        else {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x;
            }
            else if (cmp < 0) {
                return floor(x.left, key);
            }
            else {
                RedBlackBST<Key, Value>.Node t = floor(x.right, key);
                return t != null ? t : x;
            }
        }
    }

    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        else if (isEmpty()) {
            throw new NoSuchElementException("calls ceiling() with empty symbol table");
        }
        else {
            RedBlackBST<Key, Value>.Node x = ceiling(root, key);
            if (x == null) {
                throw new NoSuchElementException("argument to ceiling() is too small");
            }
            else {
                return x.key;
            }
        }
    }

    private RedBlackBST<Key, Value>.Node ceiling(RedBlackBST<Key, Value>.Node x, Key key) {
        if (x == null) {
            return null;
        }
        else {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x;
            }
            else if (cmp > 0) {
                return ceiling(x.right, key);
            }
            else {
                RedBlackBST<Key, Value>.Node t = ceiling(x.left, key);
                return t != null ? t : x;
            }
        }
    }

    public Key select(int rank) {
        if (rank >= 0 && rank < size()) {
            return select(root, rank);
        }
        else {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
    }

    private Key select(RedBlackBST<Key, Value>.Node x, int rank) {
        if (x == null) {
            return null;
        }
        else {
            int leftSize = size(x.left);
            if (leftSize > rank) {
                return select(x.left, rank);
            }
            else {
                return leftSize < rank ? select(x.right, rank - leftSize - 1) : x.key;
            }
        }
    }

    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        else {
            return rank(key, root);
        }
    }

    private int rank(Key key, RedBlackBST<Key, Value>.Node x) {
        if (x == null) {
            return 0;
        }
        else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                return rank(key, x.left);
            }
            else {
                return cmp > 0 ? 1 + size(x.left) + rank(key, x.right) : size(x.left);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Iterable<Key> keys() {
        return (Iterable<Key>) (isEmpty() ? new LinkedList<>() : keys(min(), max()));
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }
        else if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }
        else {
            Queue<Key> queue = new LinkedList<>();
            keys(root, queue, lo, hi);
            return queue;
        }
    }

    private void keys(RedBlackBST<Key, Value>.Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x != null) {
            int cmp_lo = lo.compareTo(x.key);
            int cmp_hi = hi.compareTo(x.key);
            if (cmp_lo < 0) {
                keys(x.left, queue, lo, hi);
            }

            if (cmp_lo <= 0 && cmp_hi >= 0) {
                queue.add(x.key);
            }

            if (cmp_hi > 0) {
                keys(x.right, queue, lo, hi);
            }

        }
    }

    public int size(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to size() is null");
        }
        else if (hi == null) {
            throw new IllegalArgumentException("second argument to size() is null");
        }
        else if (lo.compareTo(hi) > 0) {
            return 0;
        }
        else {
            return contains(hi) ? rank(hi) - rank(lo) + 1 : rank(hi) - rank(lo);
        }
    }

    private class Node {
        private Key key;
        private Value val;
        private RedBlackBST<Key, Value>.Node left;
        private RedBlackBST<Key, Value>.Node right;
        private boolean color;
        private int size;

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }
}
