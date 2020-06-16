package algorithms.graph;

import structures.DisjointSet;
import tools.Graph;
import java.util.*;

// minimum spanning tree
public final class MST {
    public static
    class KruskalVertex<V extends Comparable<V>> implements Comparable<KruskalVertex<V>>, DisjointSet<KruskalVertex<V>> {
        private final V content;
        private int rank = 0;
        private KruskalVertex<V> parent = this;

        public KruskalVertex(V n) { content = n; }

        public V getContent() { return content; }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_vertex){
            if(other_vertex == null) return false;
            else if(this.getClass().equals(other_vertex.getClass())){
                return content.equals(((KruskalVertex<V>) other_vertex).content);
            }else return false;
        }

        @Override
        public int hashCode(){ return content.hashCode(); }

        @Override
        public int compareTo(KruskalVertex<V> other){
            return content.compareTo(other.content);
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
        public String toString(){ return String.format("(KruskalVertex: %s)", content.toString()); }
        
    }
    public static
    class PrimVertex<V extends Comparable<V>> implements Comparable<PrimVertex<V>>{
        public final V content;
        public PrimVertex<V> parent;
        public double key = 0;

        public PrimVertex(V name) { this.content = name; }

        public PrimVertex(PrimVertex<V> other){
            this.content = other.content;
            this.parent = other.parent;
            this.key = other.key;
        }

        public V getContent() { return content; }

        @Override
        public int hashCode() { return content.hashCode(); }

        @Override
        public int compareTo(PrimVertex<V> other){
            int key_check =  Double.compare(key, other.key);
            if(key_check == 0) return content.compareTo(other.content);
            else return key_check;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other){
            if(other == null) return false;
            else if(this.getClass().equals(other.getClass())){
                return content.equals(((PrimVertex<V>) other).content);
            }else return false;
        }

        @Override
        public String toString() { return String.format("(PrimVertex %s, Key: %.2f)", content.toString(),key); }
    }

    public static <T extends Comparable<T>>
    Set<Graph<KruskalVertex<T>>.Edge> algorithmOfKruskal(Graph<KruskalVertex<T>> graph){
        Set<Graph<KruskalVertex<T>>.Edge> res = new HashSet<>();
        var edges_set = graph.getAllEdges();
        var edges_list = new ArrayList<>(edges_set);
        edges_list.sort(Comparator.comparingDouble(graph::computeWeight));
        for(var edge : edges_list){
            var v1 = edge.getFormerVertex();
            var v2 = edge.getLaterVertex();
            if(DisjointSet.findSet(v1) != DisjointSet.findSet(v2)){
                res.add(edge);
                DisjointSet.union(v1, v2);
            }
        }
        return res;
    }

    public static <T extends Comparable<T>>
    Graph<PrimVertex<T>> algorithmOfPrim(Graph<PrimVertex<T>> graph, PrimVertex<T> r){
        Queue<PrimVertex<T>> Q = new PriorityQueue<>();
        var queue_set = graph.getAllVertices();
        for(var u : queue_set){
            if(!u.equals(r)) u.key = Double.POSITIVE_INFINITY;
            else {
                u.key = 0.0;
                Q.add(u); // init
            }
            u.parent = null;
        }
        while(!queue_set.isEmpty()){
            PrimVertex<T> u;
            do { // ignore encountered vertex
                u = Q.remove();
            }while(!queue_set.contains(u));
            queue_set.remove(u);

            for(var v : graph.getNeighborsAt(u)){
                if(queue_set.contains(v) && graph.computeWeight(u,v) < v.key){
                    v.parent = u;
                    v.key = graph.computeWeight(u,v);
                    Q.add(new PrimVertex<>(v)); // add decreased key and prevent update origin
                }
            }
        }
        return graph;
    }
}