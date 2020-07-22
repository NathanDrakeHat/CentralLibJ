package structures;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public final class VEBTree {
    //keys are not duplicate and confined in a range
    private int u; //universe size
    private VEBTree summary = null;
    private VEBTree[] cluster = null;
    private final int NONE = -1;
    private int min = NONE; // hidden in cluster
    private int max = NONE;

    private VEBTree(double u) {
        this.u = (int) u;
        if (u != 2) {
            var c = Math.ceil(Math.pow(u, 1 / 2.0));
            summary = new VEBTree(c);
            cluster = new VEBTree[(int) c];
            var f = Math.floor(Math.pow(u, 1 / 2.0));
            for (int i = 0; i < cluster.length; i++) {
                cluster[i] = new VEBTree(f);
            }
        }
    }

    public VEBTree(int k) {
        if (k < 1) {
            throw new IllegalArgumentException("input should >= 1.");
        }
        u = (int) Math.pow(2, k);
        if (u != 2) {
            var c = Math.ceil(Math.pow(u, 1 / 2.0)); // return double
            summary = new VEBTree(c);
            cluster = new VEBTree[(int) c];
            var f = Math.floor(Math.pow(u, 1 / 2.0));
            for (int i = 0; i < cluster.length; i++) {
                cluster[i] = new VEBTree(f);
            }
        }
    }

    private void emptyInsert(VEBTree V, int x) {
        V.min = x;
        V.max = x;
    }

    private void insert(VEBTree V, int x) {
        // recursive strip x into cluster index and array index
        if (V.min == NONE) {
            emptyInsert(V, x);
        }
        else {
            if (x < V.min) {
                var t = x;
                x = V.min;
                V.min = t;
            }
            if (V.u > 2) {
                if (minimum(V.cluster[V.high(x)]) == NONE) {
                    //                System.out.println(String.format("enter summary, cluster index %d", V.high(x)));
                    insert(V.summary, V.high(x));
                    emptyInsert(V.cluster[V.high(x)], V.low(x));
                }
                else {
                    //                System.out.println(String.format("enter cluster %d, index %d", V.high(x), V.low(x)));
                    insert(V.cluster[V.high(x)], V.low(x));
                }
            }
            if (x > V.max) {
                V.max = x;
            }
        }
    }

    public VEBTree safeInsert(int x) {
        Objects.checkIndex(x, u);
        if (!hasMember(x)) {
            insert(this, x);
        }
        return this;
    }

    public VEBTree uncheckInsert(int x) {
        // duplicate insert will invoke bug
        Objects.checkIndex(x, u);
        insert(this, x);
        return this;
    }

    public VEBTree safeDelete(int x) {
        Objects.checkIndex(x, u);
        if (hasMember(x)) // can't delete multi-time, can't delete none
        {
            delete(this, x);
        }
        return this;
    }

    public VEBTree uncheckDelete(int x) {
        //duplicate delete or delete items not in tree will invoke bug
        Objects.checkIndex(x, u);
        delete(this, x);
        return this;
    }

    private void delete(VEBTree V, int x) {
        // worst case lg(lg u)
        // base case
        if (V.min == V.max) {
            V.min = NONE;
            V.max = NONE;
        }
        else if (V.u == 2) {
            if (x == 0) {
                V.min = 1;
            }
            else {
                V.min = 0;
            }
            V.max = V.min;
        }
        else {
            if (x == V.min) {
                var first_cluster = minimum(V.summary);
                x = V.index(first_cluster, minimum(V.cluster[first_cluster]));
                V.min = x; // second min to V.min
            }
            delete(V.cluster[V.high(x)], V.low(x));
            if (minimum(V.cluster[V.high(x)]) == NONE) {
                delete(V.summary, V.high(x));
                if (x == V.max) {
                    var summary_max = maximum(V.summary);
                    if (summary_max == NONE) {
                        V.max = V.min;
                    }
                    else {
                        V.max = V.index(summary_max, maximum(V.cluster[summary_max]));
                    }
                }
            }
            else if (x == V.max) {
                V.max = V.index(V.high(x), maximum(V.cluster[V.high(x)]));
            }
        }
    }

    public Optional<Integer> tryGetMaximum() {
        try {
            return Optional.of(forceGetMaximum());
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public Integer forceGetMaximum() {
        var res = maximum(this);
        if (res != NONE) {
            return res;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    private int maximum(VEBTree V) {
        return V.max;
    }

    public Optional<Integer> tryGetMinimum() {
        try {
            return Optional.of(forceGetMinimum());
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public Integer forceGetMinimum() {
        var res = minimum(this);
        if (res != NONE) {
            return res;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    private int minimum(VEBTree V) {
        return V.min;
    }

    public boolean hasMember(int x) {
        return hasMember(this, x);
    }

    private boolean hasMember(VEBTree V, int x) {
        if (x == V.min || x == V.max) {
            return true;
        }
        else if (V.u == 2) {
            return false;
        }
        else {
            return hasMember(V.cluster[V.high(x)], V.low((x)));
        }
    }

    public Optional<Integer> tryGetSuccessor(int x) {
        try {
            return Optional.of(forceGetSuccessor(x));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public Integer forceGetSuccessor(int x) {
        var res = successor(this, x);
        if (res != NONE) {
            return res;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    private int successor(VEBTree V, int x) {
        // base
        if (V.u == 2) {
            if (x == 0 && V.max == 1) {
                return 1; // have x and successor
            }
            else {
                return NONE;
            }
        }
        else if (V.min != NONE && x < V.min) {
            return V.min; // dose not have x but have successor
        }
        else {// recursive
            var max_low = maximum(V.cluster[V.high(x)]);
            if (max_low != NONE && V.low(x) < max_low) {
                var offset = successor(V.cluster[V.high(x)], V.low(x));
                return V.index(V.high(x), offset);
            }
            else {
                var succ_cluster = successor(V.summary, V.high(x));
                if (succ_cluster == NONE) {
                    return NONE;
                }
                var offset = minimum(V.cluster[succ_cluster]);
                return V.index(succ_cluster, offset);
            }
        }
    }

    public Optional<Integer> tryGetPredecessor(int x) {
        try {
            return Optional.of(forceGetPredecessor(x));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public Integer forceGetPredecessor(int x) {
        var res = predecessor(this, x);
        if (res != NONE) {
            return res;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    private int predecessor(VEBTree V, int x) {
        if (V.u == 2) {
            if (x == 1 && V.min == 0) {
                return 0;
            }
            else {
                return NONE;
            }
        }
        else if (V.max != NONE && x > V.max) {
            return V.max;
        }
        else {
            var min_low = minimum(V.cluster[V.high(x)]);
            if (min_low != NONE && V.low(x) > min_low) {//and
                var offset = predecessor(V.cluster[V.high(x)], V.low(x));
                return V.index(V.high(x), offset);
            }
            else {
                var pred_cluster = predecessor(V.summary, V.high(x));
                if (pred_cluster == NONE) {
                    if (V.min != NONE && x > V.min) {
                        return V.min;
                    }
                    else {
                        return NONE;
                    }
                }
                else {
                    var offset = maximum(V.cluster[pred_cluster]);
                    return V.index(pred_cluster, offset);
                }
            }
        }
    }

    private int high(int x) {
        if (x < 0) {
            throw new IllegalArgumentException();
        }
        return (int) (x / Math.floor(Math.pow(u, 1 / 2.0)));
    }

    private int low(int x) {
        if (x < 0) {
            throw new IllegalArgumentException();
        }
        return (int) (x % Math.floor(Math.pow(u, 1 / 2.0)));
    }

    private int index(int h, int l) {
        if (h < 0 || l < 0) {
            throw new IllegalArgumentException();
        }
        return (int) (h * Math.floor(Math.pow(u, 1 / 2.0)) + l);
    }
}