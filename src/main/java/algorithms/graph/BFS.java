package algorithms.graph;

import tools.Graph;
import java.util.*;

public class BFS {
    // breath first search
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Vertex<V extends Comparable<V>> implements Comparable<Vertex<V>>{
        private Vertex<V> parent;
        private COLOR color;
        private double d; //distance
        private final V name; // generic

        Vertex(V name){ this.name = name; }

        public boolean equals(Vertex<V> other){ return name.equals(other.name); }

        @Override public int hashCode(){
            return name.hashCode();
        }

        @Override public int compareTo(Vertex<V> other) {
            return this.name.compareTo(other.name);
        }
    }

    public static <T extends Comparable<T>> void breathFirstSearch(Graph<Vertex<T>> G, Vertex<T> s) {
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

    public static <T extends Comparable<T>> List<Character> getPath( Vertex<T> s, Vertex<T> v){
        List<T> t = new ArrayList<>();
        getPath(s, v, t);
        int idx = 0;
        List<Character> res = new ArrayList<>(t.size());
        for(var i : t)
            res.add(idx++, i);
        return res;
    }
    private static <T extends Comparable<T>> void getPath(Vertex<T> s, Vertex<T> v, List<T> res){
        if(v == s){
            res.add(s.name);
        } else if(v.parent != null){
            getPath(s, v.parent, res);
            res.add(v.name);
        }
    }
}
