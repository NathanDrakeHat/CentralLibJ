package org.nathan.algorithmsJava.structures;

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
        if (x != x.parent) {
            x.parent = findSet(x.parent);
        }
        return x.parent;
    }

    public static  void union(DisjointSet a, DisjointSet b) {
        link(findSet(a), findSet(b));
    }

    private static  void link(DisjointSet x, DisjointSet y) {
        if (x.rank > y.rank) {
            y.parent = x;
        }
        else {
            x.parent = y;
            if (x.rank == y.rank) {
                y.rank = y.rank + 1;
            }
        }
    }
}