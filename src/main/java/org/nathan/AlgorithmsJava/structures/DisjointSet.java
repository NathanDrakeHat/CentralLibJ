package org.nathan.AlgorithmsJava.structures;

// generic type O: origin type
public abstract class DisjointSet<O> {
    private int rank = 0;
    private DisjointSet<O> parent = this;

    public static <T> DisjointSet<T> findSet(DisjointSet<T> x) {
        if (x != x.getParent()) {
            x.setParent(findSet(x.getParent()));
        }
        return x.getParent();
    }

    public static <T> void union(DisjointSet<T> a, DisjointSet<T> b) {
        link(findSet(a), findSet(b));
    }

    private static <T> void link(DisjointSet<T> x, DisjointSet<T> y) {
        if (x.getRank() > y.getRank()) {
            y.setParent(x);
        }
        else {
            x.setParent(y);
            if (x.getRank() == y.getRank()) {
                y.setRank(y.getRank() + 1);
            }
        }
    }

    DisjointSet<O> getParent() {
        return parent;
    }

    void setParent(DisjointSet<O> root) {
        parent = root;
    }

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }
}