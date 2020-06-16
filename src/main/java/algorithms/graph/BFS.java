package algorithms.graph;

import tools.Graph;
import java.util.*;

public final class BFS {
    // breath first search
    enum COLOR{ WHITE, GRAY, BLACK}
    public static
    class Vertex<V extends Comparable<V>> implements Comparable<Vertex<V>>{
        Vertex<V> parent;
        COLOR color;
        double d; //distance
        final V content; // generic

        Vertex(V name){ this.content = name; }

        public V getContent() { return content; }

        public boolean equals(Vertex<V> other){ return content.equals(other.content); }

        @Override public int hashCode(){
            return content.hashCode();
        }

        @Override public int compareTo(Vertex<V> other) {
            return this.content.compareTo(other.content);
        }

        @Override
        public String toString() {
            return String.format("BFS.Vertex:%s, parent:%s",content.toString(),parent.toString());
        }
    }

    public static <T extends Comparable<T>>
    void breathFirstSearch(Graph<Vertex<T>> G, Vertex<T> s) {
        var vs = G.getAllVertices();
        for (var v : vs) {
            if (!v.equals(s)) {
                v.color = COLOR.WHITE;
                v.d = Double.POSITIVE_INFINITY;
                v.parent = null;
            }
        }
        s.color = COLOR.GRAY;
        s.d = 0;
        s.parent = null;
        Queue<Vertex<T>> Q = new LinkedList<>();
        Q.add(s);
        while(!Q.isEmpty()){
            var u = Q.remove();
            for(var v : G.getNeighborsAt(u)){
                if(v.color == COLOR.WHITE){
                    v.color = COLOR.GRAY;
                    v.d = u.d + 1;
                    v.parent = u;
                    Q.add(v);
                }
            }
            u.color = COLOR.BLACK;
        }
    }

    public static <T extends Comparable<T>>
    List<T> getPath( Vertex<T> s, Vertex<T> v){
        List<T> t = new ArrayList<>();
        traverse(s, v, t);
        int idx = 0;
        List<T> res = new ArrayList<>(t.size());
        for(var i : t)
            res.add(idx++, i);
        return res;
    }
    private static <T extends Comparable<T>>
    void traverse(Vertex<T> s, Vertex<T> v, List<T> res){
        if(v == s){
            res.add(s.content);
        } else if(v.parent != null){
            traverse(s, v.parent, res);
            res.add(v.content);
        }
    }
}
