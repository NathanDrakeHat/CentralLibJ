package structures;

public interface DisjointSet<V> {


    static <T extends DisjointSet<T>> T findSet(T x) {
        if(x.getParent() == null){
            x.setParent(x);
            return x;
        }
        if(x != x.getParent())
            x.setParent(findSet(x.getParent()));
        return x.getParent();
    }

    static <T extends DisjointSet<T>> void union(T a, T b){
        link(findSet(a), findSet(b));
    }

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

//public class DisjointSet{
//    private DisjointSet <V> parent;
//    private int rank = 0;
//
//    public DisjointSet(V v){
//        this.parent = this;
//        this.rank = 0;
//    }

//    public static <T extends IsDisjointSet<T>> void union(T a, T b){
//        link(getRootOfSet(a), getRootOfSet(b));
//    }
//
//    public static <T> void link(DisjointSet<V> x, DisjointSet<V> y){
//        if(x.rank > y.rank)
//            y.parent = x;
//        else {
//            x.parent = y;
//            if(x.rank == y.rank) y.rank++;
//        }
//    }

//    private DisjointSet<V> getRootOfSet(DisjointSet<V> x){
//        if(x != x.parent)
//            x.parent = getRootOfSet(x.parent);
//        return x.parent;
//    }


