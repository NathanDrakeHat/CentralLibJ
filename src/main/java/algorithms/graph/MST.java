package algorithms.graph;

import structures.DisjointSet;
import tools.Graph;
import java.util.*;

// minimum spanning tree
public class MST {
    public static class KruskalVertex<V extends Comparable<V>> implements Comparable<KruskalVertex<V>>, DisjointSet<KruskalVertex<V>> {
        private final V name;
        private int rank = 0;
        private KruskalVertex<V> parent = this;

        public KruskalVertex(V n) { name = n; }

        public V getName() { return name; }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_vertex){
            if(this.getClass().equals(other_vertex.getClass())){
                return name.equals(((KruskalVertex<V>) other_vertex).name);
            }else return false;
        }

        @Override
        public int hashCode(){ return name.hashCode(); }

        @Override
        public int compareTo(KruskalVertex<V> other){
            return name.compareTo(other.name);
        }
        
        @Override
        public int getRank() { return rank; }
        @Override
        public void setRank(int rank) { this.rank = rank; }
        
        @Override
        public KruskalVertex<V> getParent() { return parent; }
        @Override
        public void setParent(KruskalVertex<V> r) { this.parent = r; }

        @Override
        public String toString(){ return String.format("KruskalVertex: %s", name.toString()); }
        
    }
    public static class PrimVertex<V extends Comparable<V>> implements Comparable<PrimVertex<V>>{
        public final V name;
        public PrimVertex<V> parent;
        public double key = 0;

        public PrimVertex(V name) { this.name = name; }

        public PrimVertex(PrimVertex<V> other){
            this.name = other.name;
            this.parent = other.parent;
            this.key = other.key;
        }

        @Override
        public int hashCode() { return name.hashCode(); }

        @Override
        public int compareTo(PrimVertex<V> other){
            int key_check =  Double.compare(key, other.key);
            if(key_check == 0) return name.compareTo(other.name);
            else return key_check;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other){
            if(this.getClass().equals(other.getClass())){
                return name.equals(((PrimVertex<V>) other).name);
            }else return false;
        }

        @Override
        public String toString() { return String.format("PrimVertex %s, Key: %.2f", name.toString(),key); }
    }

    public static <T extends Comparable<T>> Set<Graph<KruskalVertex<T>>.Edge> algorithmOfKruskal(Graph<KruskalVertex<T>> graph){
        Set<Graph<KruskalVertex<T>>.Edge> res = new HashSet<>();
        var edges_set = graph.getAllEdges();
        var edges_list = new ArrayList<>(edges_set);
        Collections.sort(edges_list);
        for(var edge : edges_list){
            var v1 = edge.getSmallerVertex();
            var v2 = edge.getBiggerVertex();
            if(DisjointSet.findSet(v1) != DisjointSet.findSet(v2)){
                res.add(edge);
                DisjointSet.union(v1, v2);
            }
        }
        return res;
    }

    public static <T extends Comparable<T>> Graph<PrimVertex<T>> algorithmOfPrim(Graph<PrimVertex<T>> graph, PrimVertex<T> r){
        Queue<PrimVertex<T>> Q = new PriorityQueue<>();
        var vertices = graph.getAllVertices();
        for(var vertex : vertices){
            if(!vertex.equals(r)) vertex.key = Double.POSITIVE_INFINITY;
            else {
                vertex.key = 0.0;
                Q.add(vertex); // init
            }
            vertex.parent = null;
        }
        while(!vertices.isEmpty()){
            PrimVertex<T> u;
            do { // ignore encountered vertex
                u = Q.remove();
            }while(!vertices.contains(u));
            vertices.remove(u);

            for(var edge : graph.getEdgesAt(u)){
                var v = edge.getAnotherVertex(u);
                if(vertices.contains(v) & edge.getWeight() < v.key){
                    v.parent = u;
                    v.key = edge.getWeight();
                    Q.add(new PrimVertex<>(v)); // prevent update
                }
            }
        }
        return graph;
    }
}