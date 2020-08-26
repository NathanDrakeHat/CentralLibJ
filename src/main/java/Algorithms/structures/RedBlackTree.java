package Algorithms.structures;

import Algorithms.tools.Pair;

import java.util.*;
import java.util.function.BiConsumer;


public final class RedBlackTree<K, V> implements Iterable<Pair<K, V>> {


    static final class ColorNode<P, Q> {
        P key;
        Q value;
        COLOR color;
        ColorNode<P, Q> parent;
        ColorNode<P, Q> left;
        ColorNode<P, Q> right;

        ColorNode(COLOR color) {
            this.color = color;
        }

        ColorNode(P key, Q val) {
            Objects.requireNonNull(key);
            color = COLOR.RED;
            this.key = key;
            this.value = val;
        }

        boolean isRed() {
            return color == COLOR.RED;
        }

        boolean isBlack() {
            return color == COLOR.BLACK;
        }

        void setRed() {
            color = COLOR.RED;
        }

        void setBlack() {
            color = COLOR.BLACK;
        }
    }

    private final class StackIterator implements Iterator<Pair<K, V>> {
        private ColorNode<K, V> ptr;
        private final Deque<ColorNode<K, V>> stack = new LinkedList<>();
        private boolean poppedBefore = false;
        private boolean finish = false;

        public StackIterator() {
            ptr = root;
            if (ptr == sentinel) finish = true;
        }

        @Override
        public boolean hasNext() {
            return !finish && ptr != null;
        }

        @Override
        public Pair<K, V> next() {
            while (ptr != null) {
                if (ptr.left != sentinel && !poppedBefore) // if popped before, walk to right
                {
                    stack.push(ptr);
                    ptr = ptr.left;
                } else {
                    var t = ptr;
                    if (ptr.right != sentinel) {
                        ptr = ptr.right;
                        poppedBefore = false;
                    } else {
                        if (stack.size() != 0) {
                            ptr = stack.pop();
                            poppedBefore = true;
                        } else ptr = null;
                    }
                    return new Pair<>(t.key, t.value);
                }
            }
            finish = true;
            throw new IllegalStateException("Iterate finish.");
        }
    }

    enum COLOR {RED, BLACK}

    ColorNode<K, V> root;
    private final Comparator<K> k_comparator;
    private final ColorNode<K, V> sentinel = new ColorNode<>(COLOR.BLACK);// sentinel: denote leaf and parent of root


    public RedBlackTree(Comparator<K> k_comparator) {
        Objects.requireNonNull(k_comparator);
        this.k_comparator = k_comparator;
        root = sentinel;
    }

    public V getValueOfMinKey() {
        if (root != null && sentinel != root) {
            return getMinimum(root).value;
        } else {
            throw new NoSuchElementException("null tree");
        }
    }

    public K getMinKey() {
        if (root != null && sentinel != root) {
            return getMinimum(root).key;
        } else {
            throw new NoSuchElementException("null tree");
        }
    }

    public V getValueOfMaxKey() {
        if (root != null && root != sentinel) {
            return getMaximum(root).value;
        } else {
            throw new NoSuchElementException("null tree");
        }
    }

    public K getMaxKey() {
        if (root != null && root != sentinel) {
            return getMaximum(root).key;
        } else {
            throw new NoSuchElementException("null tree");
        }
    }

    private void resetRoot(ColorNode<K, V> r) {
        root = r;
        root.parent = sentinel;
    }

    public void inOrderForEach(BiConsumer<K, V> bc) { // inorder print
        if (sentinel == root) {
            return;
        }
        Objects.requireNonNull(bc);
        inorderTreeWalk(root, bc);
    }

    private void inorderTreeWalk(ColorNode<K, V> n, BiConsumer<K, V> bc) {
        if (n != sentinel & n != null) {
            inorderTreeWalk(n.left, bc);
            bc.accept(n.key, n.value);
            inorderTreeWalk(n.right, bc);
        }
    }

    public int getCount() {
        if (root == sentinel) {
            return 0;
        }
        return getCount(root);
    }

    private int getCount(ColorNode<K, V> n) { //overload trick
        if (n.right != sentinel && n.left == sentinel) {
            return getCount(n.right) + 1;
        } else if (n.right == sentinel && n.left != sentinel) {
            return getCount(n.left) + 1;
        } else if (n.right != sentinel) {
            return getCount(n.left) + getCount(n.right) + 1;
        } else {
            return 1;
        }
    }

    public int getHeight() {
        if (root == sentinel) {
            return 0;
        }
        int height = 1;
        int left_max = getHeight(root.left, height);
        int right_max = getHeight(root.right, height);
        return Math.max(left_max, right_max) - 1;
    }

    private int getHeight(ColorNode<K, V> n, int height) {
        if (n != sentinel) {
            int left_max = getHeight(n.left, height + 1);
            int right_max = getHeight(n.right, height + 1);
            return Math.max(left_max, right_max);
        }
        return height;
    }

    public void insert(K key, V val) {
        Objects.requireNonNull(key);
        insert(new ColorNode<>(key, val));
    }

    private void insert(ColorNode<K, V> n) {
        if (n == sentinel) {
            return;
        }
        if (root == sentinel) {
            resetRoot(n);
            root.right = sentinel;
            root.left = sentinel;
        } else {
            var store = sentinel;
            var ptr = root;
            while (ptr != sentinel) {
                store = ptr;
                if (k_comparator.compare(n.key, ptr.key) < 0) {
                    ptr = ptr.left;
                } else {
                    ptr = ptr.right;
                }
            }
            n.parent = store;
            if (k_comparator.compare(n.key, store.key) < 0) {
                store.left = n;
            } else {
                store.right = n;
            }
            n.left = sentinel;
            n.right = sentinel;
        }
        insertFixUp(n);
    }

    private void insertFixUp(ColorNode<K, V> ptr) {
        while (ptr.parent.isRed()) {
            if (ptr.parent == ptr.parent.parent.left) {
                var right = ptr.parent.parent.right;
                if (right.isRed()) { // case1: sibling is red
                    ptr.parent.setBlack();
                    right.setBlack();
                    ptr.parent.parent.setRed();
                    ptr = ptr.parent.parent;
                    continue;
                } else if (ptr == ptr.parent.right) { //case 2 convert to case 3
                    ptr = ptr.parent;
                    leftRotate(ptr);
                }
                ptr.parent.setBlack(); // case3
                ptr.parent.parent.setRed();
                rightRotate(ptr.parent.parent); // ptr.getParent will be black and then break
                ptr = ptr.parent;
            } else {
                var left = ptr.parent.parent.left;
                if (left.isRed()) {
                    ptr.parent.setBlack();
                    left.setBlack();
                    ptr.parent.parent.setRed();
                    ptr = ptr.parent.parent;
                    continue;
                } else if (ptr == ptr.parent.left) {
                    ptr = ptr.parent;
                    rightRotate(ptr);
                }
                ptr.parent.setBlack();
                ptr.parent.parent.setRed();
                leftRotate(ptr.parent.parent);
                ptr = ptr.parent;
            }
        }
        root.setBlack();
    }

    public void delete(K key) {
        Objects.requireNonNull(key);
        delete(search(root, key));
    }

    private void delete(ColorNode<K, V> target) {
        if (target == null || target == sentinel) {
            throw new NoSuchElementException("null tree");
        }
        var ptr = target;
        var ptr_color = ptr.color;
        ColorNode<K, V> fix_up;
        if (ptr.left == sentinel) {
            fix_up = target.right;
            transplant(target, fix_up);
        } else if (ptr.right == sentinel) {
            fix_up = target.left;
            transplant(target, fix_up);
        } else {
            ptr = getSuccessor(target);
            ptr_color = ptr.color;
            fix_up = ptr.right;
            if (ptr.parent == target) {
                fix_up.parent = ptr; // in case of sentinel refer to target
            } else {
                transplant(ptr, ptr.right);
                ptr.right = target.right;
                target.right.parent = ptr;
            }
            transplant(target, ptr);
            ptr.left = target.left;
            target.left.parent = ptr;
            ptr.color = target.color;
        }
        if (ptr_color == COLOR.BLACK) { // delete black node may violate property of red-black tree
            deleteFixUp(fix_up);
        }
    }

    private void deleteFixUp(ColorNode<K, V> fix_up) {
        while (fix_up != root && fix_up.isBlack()) {
            ColorNode<K, V> sibling;
            if (fix_up == fix_up.parent.left) {
                sibling = fix_up.parent.right;
                if (sibling.isRed()) { // case1:sibling is black, convert to case 2, 3 or 4
                    sibling.setBlack(); // , which denote that sibling is black
                    fix_up.parent.setRed();
                    leftRotate(fix_up.parent);
                    sibling = fix_up.parent.right;
                }
                if (sibling.left.isBlack() && sibling.right.isBlack()) { // case2: sibling children is black
                    sibling.setRed();
                    fix_up = fix_up.parent;
                    continue;
                } else if (sibling.right.isBlack()) { // case3: sibling left red, right black. convert case4
                    sibling.left.setBlack();
                    sibling.setRed();
                    rightRotate(sibling);
                    sibling = fix_up.parent.right;
                }
                sibling.color = fix_up.parent.color; // case4: sibling right red
                fix_up.parent.setBlack();
                sibling.right.setBlack();
                leftRotate(fix_up.parent);
            } else {
                sibling = fix_up.parent.left;
                if (sibling.isRed()) {
                    sibling.setBlack();
                    fix_up.parent.setRed();
                    rightRotate(fix_up.parent);
                    sibling = fix_up.parent.left;
                }
                if (sibling.left.isBlack() && sibling.right.isBlack()) {
                    sibling.setRed();
                    fix_up = fix_up.parent;
                    continue;
                } else if (sibling.left.isBlack()) {
                    sibling.right.setBlack();
                    sibling.setRed();
                    leftRotate(sibling);
                    sibling = fix_up.parent.left;
                }
                sibling.color = fix_up.parent.color;
                fix_up.parent.setBlack();
                sibling.left.setBlack();
                rightRotate(fix_up.parent);
            }
            fix_up = root;
        }
        fix_up.setBlack();
    }

    private void transplant(ColorNode<K, V> a, ColorNode<K, V> b) {
        if (a.parent == sentinel) {
            resetRoot(b);
        } else if (a.parent.right == a) {
            a.parent.right = b;
            b.parent = a.parent; // permissible if b is sentinel
        } else {
            a.parent.left = b;
            b.parent = a.parent;
        }
    }

    public V search(K key) {
        if (root == sentinel) {
            throw new NoSuchElementException();
        }
        Objects.requireNonNull(key);
        return search(root, key).value;
    }

    private ColorNode<K, V> search(ColorNode<K, V> n, K key) {
        if (k_comparator.compare(n.key, key) == 0) {
            return n;
        } else if (k_comparator.compare(n.key, key) > 0 && n.left != sentinel) {
            return search(n.left, key);
        } else if (k_comparator.compare(n.key, key) < 0 && n.right != sentinel) {
            return search(n.right, key);
        }
        throw new NoSuchElementException();
    }

    private void leftRotate(ColorNode<K, V> left_node) {
        var right_node = left_node.right;
        //exchange
        left_node.right = right_node.left;
        if (right_node.left != sentinel) { // remember to double link
            right_node.left.parent = left_node;
        }
        //exchange
        right_node.parent = left_node.parent; // double link right_node to left_node parent
        if (left_node.parent == sentinel) {
            resetRoot(right_node);
        } else if (left_node.parent.left == left_node) {
            left_node.parent.left = right_node;
        } else {
            left_node.parent.right = right_node;
        }
        //exchange
        right_node.left = left_node;
        left_node.parent = right_node;
    }

    private void rightRotate(ColorNode<K, V> right_node) { // mirror of leftRotate
        var left_node = right_node.left;
        //exchange
        right_node.left = left_node.right;
        if (left_node.right != sentinel) { // remember to double link
            left_node.right.parent = right_node;
        }
        //exchange
        left_node.parent = right_node.parent; // double link right_node to left_node parent
        if (right_node.parent == sentinel) {
            resetRoot(left_node);
        } else if (right_node.parent.right == right_node) {
            right_node.parent.right = left_node;
        } else {
            right_node.parent.left = left_node;
        }
        //exchange
        left_node.right = right_node;
        right_node.parent = left_node;
    }

    private ColorNode<K, V> getMinimum(ColorNode<K, V> current) {
        ColorNode<K, V> target = null;
        var ptr = current;
        while (ptr != sentinel) {
            target = ptr;
            ptr = ptr.left;
        }
        if (target == null) {
            throw new NoSuchElementException();
        }
        return target;
    }

    private ColorNode<K, V> getMaximum(ColorNode<K, V> current) {
        ColorNode<K, V> target = null;
        var ptr = current;
        while (ptr != sentinel) {
            target = ptr;
            ptr = ptr.right;
        }
        if (target == null) {
            throw new NoSuchElementException();
        }
        return target;
    }

    private ColorNode<K, V> getSuccessor(ColorNode<K, V> current) {
        if (current.right != sentinel) {
            return getMinimum(current.right);
        } else {
            var target = current.parent;
            var target_right = current;
            while ((target != sentinel) && target.right == target_right) {
                target_right = target;
                target = target.parent;
            }
            return target;
        }
    }

    @SuppressWarnings("unused")
    private ColorNode<K, V> getPredecessor(ColorNode<K, V> current) {
        if (current.left != sentinel) {
            return getMaximum(current.left);
        } else {
            var target = current.parent;
            var target_left = current;
            while ((target != sentinel) && (target.left == target_left)) {
                target_left = target;
                target = target.parent;

            }
            return target;
        }
    }

    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new StackIterator();
    }
}