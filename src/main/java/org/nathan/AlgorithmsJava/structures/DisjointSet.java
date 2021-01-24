package org.nathan.AlgorithmsJava.structures;

// generic type O: origin type
public abstract class DisjointSet<O>
{
    private int rank = 0;
    private DisjointSet<O> parent = this;

    @SuppressWarnings("unchecked")
    public static <T extends DisjointSet<T>> T findSet(T x)
    {
        if (x != x.getParent())
        {

            x.setParent(findSet((T)x.getParent()));
        }
        return (T)x.getParent();
    }

    public static <T extends DisjointSet<T>> void union(T a, T b)
    {
        link(findSet(a), findSet(b));
    }

    private static <T extends DisjointSet<T>> void link(T x, T y)
    {
        if (x.getRank() > y.getRank())
        {
            y.setParent(x);
        }
        else
        {
            x.setParent(y);
            if (x.getRank() == y.getRank())
            {
                y.setRank(y.getRank() + 1);
            }
        }
    }

    DisjointSet<O> getParent(){
        return parent;
    }

    void setParent(DisjointSet<O> root){
        parent = root;
    }

    int getRank(){
        return rank;
    }

    void setRank(int rank){
        this.rank = rank;
    }
}