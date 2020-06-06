package structure;

public class DisjointSet <V>{
    private DisjointSet <V> parent;
    private final V content;
    private int rank = 0;

    public DisjointSet(V v){
        this.parent = this;
        this.rank = 0;
        content = v;
    }

    public void union(DisjointSet<V> other){ link(getRootOfSet(this), getRootOfSet(other)); }

    public void link(DisjointSet<V> x, DisjointSet<V> y){
        if(x.rank > y.rank)
            y.parent = x;
        else {
            x.parent = y;
            if(x.rank == y.rank) y.rank++;
        }
    }

    private DisjointSet<V> getRootOfSet(DisjointSet<V> x){
        if(x != x.parent)
            x.parent = getRootOfSet(x.parent);
        return x.parent;
    }
    public DisjointSet<V> getRootOfSet(){ return getRootOfSet(this); }
    
    public V getContent() { return this.content; }
}
