package org.nathan.AlgorithmsJava.structures;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

// TODO read book
public class RedBlackBST<Key extends Comparable<Key>,Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private RedBlackBST<Key, Value>.Node root;

    public RedBlackBST() {
    }

    private boolean isRed(RedBlackBST<Key, Value>.Node x) {
        if (x == null) {
            return false;
        } else {
            return x.color;
        }
    }

    private int size(RedBlackBST<Key, Value>.Node x) {
        return x == null ? 0 : x.size;
    }

    public int size() {
        return this.size(this.root);
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        } else {
            return get(this.root, key);
        }
    }

    private Value get(RedBlackBST<Key, Value>.Node x, Key key) {
        while(true) {
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
        return this.get(key) != null;
    }

    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        } else if (val == null) {
            this.delete(key);
        } else {
            this.root = this.put(this.root, key, val);
            this.root.color = false;
        }
    }

    private RedBlackBST<Key, Value>.Node put(RedBlackBST<Key, Value>.Node h, Key key, Value val) {
        if (h == null) {
            return new RedBlackBST<Key,Value>.Node(key, val, true, 1);
        } else {
            int cmp = key.compareTo(h.key);
            if (cmp < 0) {
                h.left = this.put(h.left, key, val);
            } else if (cmp > 0) {
                h.right = this.put(h.right, key, val);
            } else {
                h.val = val;
            }

            if (this.isRed(h.right) && !this.isRed(h.left)) {
                h = this.rotateLeft(h);
            }

            if (this.isRed(h.left) && this.isRed(h.left.left)) {
                h = this.rotateRight(h);
            }

            if (this.isRed(h.left) && this.isRed(h.right)) {
                this.flipColors(h);
            }

            h.size = this.size(h.left) + this.size(h.right) + 1;
            return h;
        }
    }

    public void deleteMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("BST underflow");
        } else {
            if (!this.isRed(this.root.left) && !this.isRed(this.root.right)) {
                this.root.color = true;
            }

            this.root = this.deleteMin(this.root);
            if (!this.isEmpty()) {
                this.root.color = false;
            }

        }
    }

    private RedBlackBST<Key, Value>.Node deleteMin(RedBlackBST<Key, Value>.Node h) {
        if (h.left == null) {
            return null;
        } else {
            if (!this.isRed(h.left) && !this.isRed(h.left.left)) {
                h = this.moveRedLeft(h);
            }

            h.left = this.deleteMin(h.left);
            return this.balance(h);
        }
    }

    public void deleteMax() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("BST underflow");
        } else {
            if (!this.isRed(this.root.left) && !this.isRed(this.root.right)) {
                this.root.color = true;
            }

            this.root = this.deleteMax(this.root);
            if (!this.isEmpty()) {
                this.root.color = false;
            }

        }
    }

    private RedBlackBST<Key, Value>.Node deleteMax(RedBlackBST<Key, Value>.Node h) {
        if (this.isRed(h.left)) {
            h = this.rotateRight(h);
        }

        if (h.right == null) {
            return null;
        } else {
            if (!this.isRed(h.right) && !this.isRed(h.right.left)) {
                h = this.moveRedRight(h);
            }

            h.right = this.deleteMax(h.right);
            return this.balance(h);
        }
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        } else if (this.contains(key)) {
            if (!this.isRed(this.root.left) && !this.isRed(this.root.right)) {
                this.root.color = true;
            }

            this.root = this.delete(this.root, key);
            if (!this.isEmpty()) {
                this.root.color = false;
            }

        }
    }

    private RedBlackBST<Key, Value>.Node delete(RedBlackBST<Key, Value>.Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!this.isRed(h.left) && !this.isRed(h.left.left)) {
                h = this.moveRedLeft(h);
            }

            h.left = this.delete(h.left, key);
        } else {
            if (this.isRed(h.left)) {
                h = this.rotateRight(h);
            }

            if (key.compareTo(h.key) == 0 && h.right == null) {
                return null;
            }

            if (!this.isRed(h.right) && !this.isRed(h.right.left)) {
                h = this.moveRedRight(h);
            }

            if (key.compareTo(h.key) == 0) {
                RedBlackBST<Key, Value>.Node x = this.min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = this.deleteMin(h.right);
            } else {
                h.right = this.delete(h.right, key);
            }
        }

        return this.balance(h);
    }

    private RedBlackBST<Key, Value>.Node rotateRight(RedBlackBST<Key, Value>.Node h) {
        RedBlackBST<Key, Value>.Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = true;
        x.size = h.size;
        h.size = this.size(h.left) + this.size(h.right) + 1;
        return x;
    }

    private RedBlackBST<Key, Value>.Node rotateLeft(RedBlackBST<Key, Value>.Node h) {
        RedBlackBST<Key, Value>.Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = true;
        x.size = h.size;
        h.size = this.size(h.left) + this.size(h.right) + 1;
        return x;
    }

    private void flipColors(RedBlackBST<Key, Value>.Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private RedBlackBST<Key, Value>.Node moveRedLeft(RedBlackBST<Key, Value>.Node h) {
        this.flipColors(h);
        if (this.isRed(h.right.left)) {
            h.right = this.rotateRight(h.right);
            h = this.rotateLeft(h);
            this.flipColors(h);
        }

        return h;
    }

    private RedBlackBST<Key, Value>.Node moveRedRight(RedBlackBST<Key, Value>.Node h) {
        this.flipColors(h);
        if (this.isRed(h.left.left)) {
            h = this.rotateRight(h);
            this.flipColors(h);
        }

        return h;
    }

    private RedBlackBST<Key, Value>.Node balance(RedBlackBST<Key, Value>.Node h) {
        if (this.isRed(h.right)) {
            h = this.rotateLeft(h);
        }

        if (this.isRed(h.left) && this.isRed(h.left.left)) {
            h = this.rotateRight(h);
        }

        if (this.isRed(h.left) && this.isRed(h.right)) {
            this.flipColors(h);
        }

        h.size = this.size(h.left) + this.size(h.right) + 1;
        return h;
    }

    public int height() {
        return height(this.root);
    }

    private int height(RedBlackBST<Key, Value>.Node x) {
        return x == null ? -1 : 1 + Math.max(height(x.left), height(x.right));
    }

    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return this.min(this.root).key;
        }
    }

    private RedBlackBST<Key, Value>.Node min(RedBlackBST<Key, Value>.Node x) {
        return x.left == null ? x : this.min(x.left);
    }

    public Key max() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return this.max(this.root).key;
        }
    }

    private RedBlackBST<Key, Value>.Node max(RedBlackBST<Key, Value>.Node x) {
        return x.right == null ? x : this.max(x.right);
    }

    public Key floor(@NotNull Key key) {
        if (isEmpty()) {
            throw new NoSuchElementException("calls floor() with empty symbol table");
        } else {
            RedBlackBST<Key, Value>.Node x = this.floor(this.root, key);
            if (x == null) {
                throw new NoSuchElementException("argument to floor() is too small");
            } else {
                return x.key;
            }
        }
    }

    private RedBlackBST<Key, Value>.Node floor(RedBlackBST<Key, Value>.Node x, Key key) {
        if (x == null) {
            return null;
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x;
            } else if (cmp < 0) {
                return this.floor(x.left, key);
            } else {
                RedBlackBST<Key, Value>.Node t = this.floor(x.right, key);
                return t != null ? t : x;
            }
        }
    }

    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        } else if (this.isEmpty()) {
            throw new NoSuchElementException("calls ceiling() with empty symbol table");
        } else {
            RedBlackBST<Key, Value>.Node x = this.ceiling(this.root, key);
            if (x == null) {
                throw new NoSuchElementException("argument to ceiling() is too small");
            } else {
                return x.key;
            }
        }
    }

    private RedBlackBST<Key, Value>.Node ceiling(RedBlackBST<Key, Value>.Node x, Key key) {
        if (x == null) {
            return null;
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return x;
            } else if (cmp > 0) {
                return this.ceiling(x.right, key);
            } else {
                RedBlackBST<Key, Value>.Node t = this.ceiling(x.left, key);
                return t != null ? t : x;
            }
        }
    }

    public Key select(int rank) {
        if (rank >= 0 && rank < this.size()) {
            return this.select(this.root, rank);
        } else {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
    }

    private Key select(RedBlackBST<Key, Value>.Node x, int rank) {
        if (x == null) {
            return null;
        } else {
            int leftSize = this.size(x.left);
            if (leftSize > rank) {
                return this.select(x.left, rank);
            } else {
                return leftSize < rank ? this.select(x.right, rank - leftSize - 1) : x.key;
            }
        }
    }

    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        } else {
            return this.rank(key, this.root);
        }
    }

    private int rank(Key key, RedBlackBST<Key, Value>.Node x) {
        if (x == null) {
            return 0;
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                return this.rank(key, x.left);
            } else {
                return cmp > 0 ? 1 + this.size(x.left) + this.rank(key, x.right) : this.size(x.left);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Iterable<Key> keys() {
        return (Iterable<Key>)(isEmpty() ? new LinkedList<>() : this.keys(this.min(), this.max()));
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        } else if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        } else {
            Queue<Key> queue = new LinkedList<Key>();
            this.keys(this.root, queue, lo, hi);
            return queue;
        }
    }

    private void keys(RedBlackBST<Key, Value>.Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x != null) {
            int cmplo = lo.compareTo(x.key);
            int cmphi = hi.compareTo(x.key);
            if (cmplo < 0) {
                this.keys(x.left, queue, lo, hi);
            }

            if (cmplo <= 0 && cmphi >= 0) {
                queue.add(x.key);
            }

            if (cmphi > 0) {
                this.keys(x.right, queue, lo, hi);
            }

        }
    }

    public int size(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to size() is null");
        } else if (hi == null) {
            throw new IllegalArgumentException("second argument to size() is null");
        } else if (lo.compareTo(hi) > 0) {
            return 0;
        } else {
            return this.contains(hi) ? this.rank(hi) - this.rank(lo) + 1 : this.rank(hi) - this.rank(lo);
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
