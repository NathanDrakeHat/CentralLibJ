package org.nathan.algorithms_java.structures;

/**
 * to use union() and findSet(),
 * extends this class or initial it in a public field
 */
public class DisjointSet {
    private int rank = 0;
    private DisjointSet parent = this;

    /**
     *  find identifier of the set of an element
     * @param x element
     * @return identifier
     */
    public static DisjointSet findSet(DisjointSet x) {
        if (x != x.getParent()) {
            x.setParent(findSet(x.getParent()));
        }
        return x.getParent();
    }

    public static  void union(DisjointSet a, DisjointSet b) {
        link(findSet(a), findSet(b));
    }

    private static  void link(DisjointSet x, DisjointSet y) {
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

    private DisjointSet getParent() {
        return parent;
    }

    private void setParent(DisjointSet root) {
        parent = root;
    }

    private int getRank() {
        return rank;
    }

    private void setRank(int rank) {
        this.rank = rank;
    }
}