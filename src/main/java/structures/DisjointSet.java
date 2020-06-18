package structures;

public interface DisjointSet<V> {
    static <T extends DisjointSet<T>> T findSet(T x) {
        if(x != x.getParent())
            x.setParent(findSet(x.getParent()));
        return x.getParent();
    }

    static <T extends DisjointSet<T>> void union(T a, T b){ link(findSet(a), findSet(b)); }

    static <T extends DisjointSet<T>> void link(T x, T y){
        if(x.getRank() > y.getRank())
            y.setParent(x);
        else {
            x.setParent(y);
            if(x.getRank() == y.getRank()){
                y.setRank(y.getRank()+1);
            };
        }
    }

    V getParent();

    void setParent(V root);

    int getRank();

    void setRank(int rank);
}