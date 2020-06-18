package algorithms.graph;

import java.util.*;

public final class BFS {
    // breath first search
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Vertex<V>{
        Vertex<V> parent;
        private COLOR color;
        double distance; // d
        private final V content;
        private final String string;
        private final int hash_code;

        Vertex(V name){
            this.content = name;
            string = String.format("BFS.Vertex: (%s)",content.toString());
            hash_code = string.hashCode();
        }

        public V getContent() { return content; }

        public Vertex<V> getParent() {return parent;}

        public double getDistance() { return distance; }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_vertex){
            if(other_vertex instanceof  Vertex){
                return content.equals(((Vertex<V>) other_vertex).content);
            }else return false;
        }

        @Override public int hashCode(){ return hash_code; }

        @Override
        public String toString() { return string; }
    }

    public static <T> void breathFirstSearch(Graph<Vertex<T>> G, Vertex<T> s) {
        var vs = G.getAllVertices();
        for (var v : vs) {
            if (!v.equals(s)) {
                v.color = COLOR.WHITE;
                v.distance = Double.POSITIVE_INFINITY;
                v.parent = null;
            }
        }
        s.color = COLOR.GRAY;
        s.distance = 0;
        s.parent = null;
        Queue<Vertex<T>> Q = new LinkedList<>();
        Q.add(s);
        while(!Q.isEmpty()){
            var u = Q.remove();
            for(var v : G.getNeighborsAt(u)){
                if(v.color == COLOR.WHITE){
                    v.color = COLOR.GRAY;
                    v.distance = u.distance + 1;
                    v.parent = u;
                    Q.add(v);
                }
            }
            u.color = COLOR.BLACK;
        }
    }

    public static <T> List<T> getPath(Vertex<T> s, Vertex<T> v){
        List<T> t = new ArrayList<>();
        traverse(s, v, t);
        int idx = 0;
        List<T> res = new ArrayList<>(t.size());
        for(var i : t)
            res.add(idx++, i);
        return res;
    }
    private static <T> void traverse(Vertex<T> s, Vertex<T> v, List<T> res){
        if(v == s){
            res.add(s.content);
        } else if(v.parent != null){
            traverse(s, v.parent, res);
            res.add(v.content);
        }
    }
}
