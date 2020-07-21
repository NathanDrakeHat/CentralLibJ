package structures;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;


public final class RedBlackTree<K, V>
{
    public static class ColorNode<P, Q> implements Comparable<ColorNode<P, Q>>
    {
        private P key;
        private Q value;
        private COLOR color;
        private ColorNode<P, Q> parent;
        private ColorNode<P, Q> left;
        private ColorNode<P, Q> right;
        private final Comparator<P> p_comparator;

        private ColorNode(COLOR color)
        {
            this.color = color;
            p_comparator = null;
        }

        private ColorNode(P key, Q val, Comparator<P> p_comparator)
        {
            Objects.requireNonNull(key);
            if (key instanceof Comparable)
            {
                color = COLOR.RED;
                this.key = key;
                this.value = val;
                this.p_comparator = p_comparator;
            }
            else
            {
                color = COLOR.RED;
                this.key = key;
                this.value = val;
                Objects.requireNonNull(p_comparator);
                this.p_comparator = p_comparator;
            }
        }

        public boolean isRed()
        {
            return color == COLOR.RED;
        }

        public boolean isBlack()
        {
            return color == COLOR.BLACK;
        }

        public COLOR getColor()
        {
            return color;
        }

        private void setColor(COLOR color)
        {
            this.color = color;
        }

        private void setRed()
        {
            color = COLOR.RED;
        }

        private void setBlack()
        {
            color = COLOR.BLACK;
        }

        public Q getValue()
        {
            return value;
        }

        public P getKey()
        {
            return key;
        }

        public ColorNode<P, Q> getParent()
        {
            return parent;
        }

        private void setParent(ColorNode<P, Q> parent)
        {
            this.parent = parent;
        }

        public ColorNode<P, Q> getLeft()
        {
            return left;
        }

        private void setLeft(ColorNode<P, Q> left)
        {
            this.left = left;
        }

        public ColorNode<P, Q> getRight()
        {
            return right;
        }

        private void setRight(ColorNode<P, Q> right)
        {
            this.right = right;
        }

        @Override
        @SuppressWarnings("unchecked")
        public int compareTo(ColorNode other)
        {
            if (p_comparator != null)
            {
                var t = p_comparator.compare(key, (P) other.key);
                return (t == 0) ? (value.equals(other.value) ? 0 : value.hashCode() - other.value.hashCode()) : t;
            }
            else if (key instanceof Comparable)
            {
                if (!this.key.getClass().equals(other.key.getClass()))
                {
                    throw new IllegalArgumentException("type match error");
                }
                var t = ((Comparable<P>) key).compareTo((P) other.key);
                return (t == 0) ? (value.equals(other.value) ? 0 : value.hashCode() - other.value.hashCode()) : t;
            }
            else
            {
                throw new AssertionError();
            }
        }

        // It must be same
        @SuppressWarnings("unchecked")
        public int compareKey(P key)
        {
            if (p_comparator != null)
            {
                return p_comparator.compare(this.getKey(), key);
            }
            else if (getKey() instanceof Comparable)
            {
                return ((Comparable<P>) getKey()).compareTo(key);
            }
            else
            {
                throw new AssertionError();
            }
        }
    }

    enum COLOR
    {RED, BLACK}

    private ColorNode<K, V> root = null;
    private final Comparator<K> k_comparator;
    private final ColorNode<K, V> sentinel = new ColorNode<>(COLOR.BLACK);// sentinel: denote leaf and parent of root


    public RedBlackTree()
    {
        k_comparator = null;
    }

    public RedBlackTree(Comparator<K> k_comparator)
    {
        Objects.requireNonNull(k_comparator);
        this.k_comparator = k_comparator;
    }

    public Optional<V> tryGetValueOfMinKey()
    {
        try
        {
            return Optional.of(forceGetValueOfMinKey());
        } catch (NoSuchElementException e)
        {
            return Optional.empty();
        }
    }

    public V forceGetValueOfMinKey()
    {
        if (getRoot() != null && getSentinel() != getRoot())
        {
            return getMinimum(getRoot()).getValue();
        }
        else
        {
            throw new NoSuchElementException("null tree");
        }
    }

    public Optional<K> tryGetMinKey()
    {
        try
        {
            return Optional.of(forceGetMinKey());
        } catch (NoSuchElementException e)
        {
            return Optional.empty();
        }
    }

    public K forceGetMinKey()
    {
        if (getRoot() != null && getSentinel() != getRoot())
        {
            return getMinimum(getRoot()).getKey();
        }
        else
        {
            throw new NoSuchElementException("null tree");
        }
    }

    public Optional<V> tryGetValueOfMaxKey()
    {
        try
        {
            return Optional.of(forceGetValueOfMaxKey());
        } catch (NoSuchElementException e)
        {
            return Optional.empty();
        }
    }

    public V forceGetValueOfMaxKey()
    {
        if (getRoot() != null && getRoot() != getSentinel())
        {
            return getMaximum(getRoot()).getValue();
        }
        else
        {
            throw new NoSuchElementException("null tree");
        }
    }

    public Optional<K> tryGetMaxKey()
    {
        try
        {
            return Optional.of(forceGetMaxKey());
        } catch (NoSuchElementException e)
        {
            return Optional.empty();
        }
    }

    public K forceGetMaxKey()
    {
        if (getRoot() != null && getRoot() != getSentinel())
        {
            return getMaximum(getRoot()).getKey();
        }
        else
        {
            throw new NoSuchElementException("null tree");
        }
    }

    public ColorNode<K, V> getSentinel()
    {
        return sentinel;
    }

    private void setRoot(ColorNode<K, V> r)
    {
        Objects.requireNonNull(r);
        root = r;
        root.setParent(getSentinel());
    }

    public ColorNode<K, V> getRoot()
    {
        return root;
    }

    public void inOrderForEach(BiConsumer<K, V> bc)
    { // inorder print
        if (getRoot() == null || getSentinel() == getRoot())
        {
            return;
        }
        Objects.requireNonNull(bc);
        inorderTreeWalk(getRoot(), bc);
    }

    private void inorderTreeWalk(ColorNode<K, V> n, BiConsumer<K, V> bc)
    {
        if (n != getSentinel() & n != null)
        {
            inorderTreeWalk(n.getLeft(), bc);
            bc.accept(n.getKey(), n.getValue());
            inorderTreeWalk(n.getRight(), bc);
        }
    }

    public int getCount()
    {
        if (getRoot() == getSentinel())
        {
            return 0;
        }
        return getCount(getRoot());
    }

    private int getCount(ColorNode<K, V> n)
    { //overload trick
        if (n.getRight() != getSentinel() && n.getLeft() == getSentinel())
        {
            return getCount(n.getRight()) + 1;
        }
        else if (n.getRight() == getSentinel() && n.getLeft() != getSentinel())
        {
            return getCount(n.getLeft()) + 1;
        }
        else if (n.getRight() != getSentinel() && n.getLeft() != getSentinel())
        {
            return getCount(n.getLeft()) + getCount(n.getRight()) + 1;
        }
        else
        {
            return 1;
        }
    }

    public int getHeight()
    {
        if (getRoot() == null || getRoot() == getSentinel())
        {
            return 0;
        }
        int height = 1;
        int left_max = getHeight(getRoot().getLeft(), height);
        int right_max = getHeight(getRoot().getRight(), height);
        return Math.max(left_max, right_max) - 1;
    }

    private int getHeight(ColorNode<K, V> n, int height)
    {
        if (n != getSentinel())
        {
            int left_max = getHeight(n.getLeft(), height + 1);
            int right_max = getHeight(n.getRight(), height + 1);
            return Math.max(left_max, right_max);
        }
        return height;
    }

    public void insert(K key, V val)
    {
        Objects.requireNonNull(key);
        insert(new ColorNode<>(key, val, k_comparator));
    }

    private void insert(ColorNode<K, V> n)
    {
        Objects.requireNonNull(n);
        if (n == sentinel)
        {
            return;
        }
        if (getRoot() == null || getRoot() == getSentinel())
        {
            setRoot(n);
            getRoot().setRight(getSentinel());
            getRoot().setLeft(getSentinel());
        }
        else
        {
            var store = getSentinel();
            var ptr = getRoot();
            while (ptr != getSentinel())
            {
                store = ptr;
                if (n.compareTo(ptr) < 0)
                {
                    ptr = ptr.getLeft();
                }
                else
                {
                    ptr = ptr.getRight();
                }
            }
            n.setParent(store);
            if (n.compareTo(store) < 0)
            {
                store.setLeft(n);
            }
            else
            {
                store.setRight(n);
            }
            n.setLeft(getSentinel());
            n.setRight(getSentinel());
        }
        insertFixUp(n);
    }

    private void insertFixUp(ColorNode<K, V> ptr)
    {
        while (ptr.getParent().isRed())
        {
            if (ptr.getParent() == ptr.getParent().getParent().getLeft())
            {
                var right = ptr.getParent().getParent().getRight();
                if (right.isRed())
                { // case1: sibling is red
                    ptr.getParent().setBlack();
                    right.setBlack();
                    ptr.getParent().getParent().setRed();
                    ptr = ptr.getParent().getParent();
                    continue;
                }
                else if (ptr == ptr.getParent().getRight())
                { //case 2 convert to case 3
                    ptr = ptr.getParent();
                    leftRotate(ptr);
                }
                ptr.getParent().setBlack(); // case3
                ptr.getParent().getParent().setRed();
                rightRotate(ptr.getParent().getParent()); // ptr.getParent will be black and then break
                ptr = ptr.getParent();
            }
            else
            {
                var left = ptr.getParent().getParent().getLeft();
                if (left.isRed())
                {
                    ptr.getParent().setBlack();
                    left.setBlack();
                    ptr.getParent().getParent().setRed();
                    ptr = ptr.getParent().getParent();
                    continue;
                }
                else if (ptr == ptr.getParent().getLeft())
                {
                    ptr = ptr.getParent();
                    rightRotate(ptr);
                }
                ptr.getParent().setBlack();
                ptr.getParent().getParent().setRed();
                leftRotate(ptr.getParent().getParent());
                ptr = ptr.getParent();
            }
        }
        getRoot().setBlack();
    }

    public void delete(K key)
    {
        Objects.requireNonNull(key);
        delete(search(getRoot(), key));
    }

    private void delete(ColorNode<K, V> target)
    {
        if (target == null || target == getSentinel())
        {
            throw new NoSuchElementException("null tree");
        }
        var ptr = target;
        var ptr_color = ptr.getColor();
        ColorNode<K, V> fix_up;
        if (ptr.getLeft() == getSentinel())
        {
            fix_up = target.getRight();
            transplant(target, fix_up);
        }
        else if (ptr.getRight() == getSentinel())
        {
            fix_up = target.getLeft();
            transplant(target, fix_up);
        }
        else
        {
            ptr = getSuccessor(target);
            ptr_color = ptr.getColor();
            fix_up = ptr.getRight();
            if (ptr.getParent() == target)
            {
                fix_up.setParent(ptr); // in case of sentinel refer to target
            }
            else
            {
                transplant(ptr, ptr.getRight());
                ptr.setRight(target.getRight());
                target.getRight().setParent(ptr);
            }
            transplant(target, ptr);
            ptr.setLeft(target.getLeft());
            target.getLeft().setParent(ptr);
            ptr.setColor(target.getColor());
        }
        if (ptr_color == COLOR.BLACK)
        { // delete black node may violate property of red-black tree
            deleteFixUp(fix_up);
        }
    }

    private void deleteFixUp(ColorNode<K, V> fix_up)
    {
        while (fix_up != getRoot() && fix_up.isBlack())
        {
            ColorNode<K, V> sibling;
            if (fix_up == fix_up.getParent().getLeft())
            {
                sibling = fix_up.getParent().getRight();
                if (sibling.isRed())
                { // case1:sibling is black, convert to case 2, 3 or 4
                    sibling.setBlack(); // , which denote that sibling is black
                    fix_up.getParent().setRed();
                    leftRotate(fix_up.getParent());
                    sibling = fix_up.getParent().getRight();
                }
                if (sibling.getLeft().isBlack() && sibling.getRight().isBlack())
                { // case2: sibling children is black
                    sibling.setRed();
                    fix_up = fix_up.getParent();
                    continue;
                }
                else if (sibling.getRight().isBlack())
                { // case3: sibling left red, right black. convert case4
                    sibling.getLeft().setBlack();
                    sibling.setRed();
                    rightRotate(sibling);
                    sibling = fix_up.getParent().getRight();
                }
                sibling.setColor(fix_up.getParent().getColor()); // case4: sibling right red
                fix_up.getParent().setBlack();
                sibling.getRight().setBlack();
                leftRotate(fix_up.getParent());
            }
            else
            {
                sibling = fix_up.getParent().getLeft();
                if (sibling.isRed())
                {
                    sibling.setBlack();
                    fix_up.getParent().setRed();
                    rightRotate(fix_up.getParent());
                    sibling = fix_up.getParent().getLeft();
                }
                if (sibling.getLeft().isBlack() && sibling.getRight().isBlack())
                {
                    sibling.setRed();
                    fix_up = fix_up.getParent();
                    continue;
                }
                else if (sibling.getLeft().isBlack())
                {
                    sibling.getRight().setBlack();
                    sibling.setRed();
                    leftRotate(sibling);
                    sibling = fix_up.getParent().getLeft();
                }
                sibling.setColor(fix_up.getParent().getColor());
                fix_up.getParent().setBlack();
                sibling.getLeft().setBlack();
                rightRotate(fix_up.getParent());
            }
            fix_up = getRoot();
        }
        fix_up.setBlack();
    }

    private void transplant(ColorNode<K, V> a, ColorNode<K, V> b)
    {
        if (a.getParent() == getSentinel())
        {
            setRoot(b);
        }
        else if (a.getParent().getRight() == a)
        {
            a.getParent().setRight(b);
            b.setParent(a.getParent()); // permissible if b is sentinel
        }
        else
        {
            a.getParent().setLeft(b);
            b.setParent(a.getParent());
        }
    }

    public Optional<V> trySearch(K key)
    {
        try
        {
            return Optional.of(forceSearch(key));
        } catch (NoSuchElementException e)
        {
            return Optional.empty();
        }
    }

    public V forceSearch(K key)
    {
        if (getRoot() == null || getRoot() == getSentinel())
        {
            throw new NoSuchElementException();
        }
        Objects.requireNonNull(key);
        return search(getRoot(), key).getValue();
    }

    private ColorNode<K, V> search(ColorNode<K, V> n, K key)
    {
        if (n.compareKey(key) == 0)
        {
            return n;
        }
        else if (n.compareKey(key) > 0 && n.getLeft() != getSentinel())
        {
            return search(n.getLeft(), key);
        }
        else if (n.compareKey(key) < 0 && n.getRight() != getSentinel())
        {
            return search(n.getRight(), key);
        }
        throw new NoSuchElementException();
    }

    private void leftRotate(ColorNode<K, V> left_node)
    {
        var right_node = left_node.getRight();
        //exchange
        left_node.setRight(right_node.getLeft());
        if (right_node.getLeft() != getSentinel())
        { // remember to double link
            right_node.getLeft().setParent(left_node);
        }
        //exchange
        right_node.setParent(left_node.getParent()); // double link right_node to left_node parent
        if (left_node.getParent() == getSentinel())
        {
            setRoot(right_node);
        }
        else if (left_node.getParent().getLeft() == left_node)
        {
            left_node.getParent().setLeft(right_node);
        }
        else
        {
            left_node.getParent().setRight(right_node);
        }
        //exchange
        right_node.setLeft(left_node);
        left_node.setParent(right_node);
    }

    private void rightRotate(ColorNode<K, V> right_node)
    { // mirror of leftRotate
        var left_node = right_node.getLeft();
        //exchange
        right_node.setLeft(left_node.getRight());
        if (left_node.getRight() != getSentinel())
        { // remember to double link
            left_node.getRight().setParent(right_node);
        }
        //exchange
        left_node.setParent(right_node.getParent()); // double link right_node to left_node parent
        if (right_node.getParent() == getSentinel())
        {
            setRoot(left_node);
        }
        else if (right_node.getParent().getRight() == right_node)
        {
            right_node.getParent().setRight(left_node);
        }
        else
        {
            right_node.getParent().setLeft(left_node);
        }
        //exchange
        left_node.setRight(right_node);
        right_node.setParent(left_node);
    }

    private ColorNode<K, V> getMinimum(ColorNode<K, V> current)
    {
        ColorNode<K, V> target = null;
        var ptr = current;
        while (ptr != getSentinel())
        {
            target = ptr;
            ptr = ptr.getLeft();
        }
        if (target == null)
        {
            throw new NoSuchElementException();
        }
        return target;
    }

    private ColorNode<K, V> getMaximum(ColorNode<K, V> current)
    {
        ColorNode<K, V> target = null;
        var ptr = current;
        while (ptr != getSentinel())
        {
            target = ptr;
            ptr = ptr.getRight();
        }
        if (target == null)
        {
            throw new NoSuchElementException();
        }
        return target;
    }

    private ColorNode<K, V> getSuccessor(ColorNode<K, V> current)
    {
        if (current.getRight() != getSentinel())
        {
            return getMinimum(current.getRight());
        }
        else
        {
            var target = current.getParent();
            var target_right = current;
            while ((target != getSentinel()) && target.getRight() == target_right)
            {
                target_right = target;
                target = target.getParent();
            }
            return target;
        }
    }

    private ColorNode<K, V> getPredecessor(ColorNode<K, V> current)
    {
        if (current.getLeft() != getSentinel())
        {
            return getMaximum(current.getLeft());
        }
        else
        {
            var target = current.getParent();
            var target_left = current;
            while ((target != getSentinel()) && (target.getLeft() == target_left))
            {
                target_left = target;
                target = target.getParent();

            }
            return target;
        }
    }
}