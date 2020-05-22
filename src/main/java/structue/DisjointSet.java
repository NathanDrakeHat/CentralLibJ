package structue;

public class DisjointSet <V>{
    private DisjointSet <V> parent;
    private V content = null;
    private int rank = 0;

    public DisjointSet(V v){
        this.parent = this;
        this.rank = 0;
        content = v;
    }

    public void union(DisjointSet<V> other){
        link(findSetRoot(this), findSetRoot(other));
    }

    public void link(DisjointSet<V> x, DisjointSet<V> y){
        if(x.rank > y.rank)
            y.parent = x;
        else {
            x.parent = y;
            if(x.rank == y.rank) y.rank++;
        }
    }

    private DisjointSet<V> findSetRoot(DisjointSet<V> x){
        if(x != x.parent)
            x.parent = findSetRoot(x.parent);
        return x.parent;
    }
    public DisjointSet<V> findSetRoot(){
        if(this != this.parent)
            this.parent = findSetRoot(this.parent);
        return this.parent;
    }
}
