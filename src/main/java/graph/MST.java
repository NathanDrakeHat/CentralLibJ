package graph;

import structures.DisjointSet;
import structures.FibonacciHeap;
import tools.MinHeap;

import java.util.*;

// minimum spanning tree
public final class MST {
    public static class KruskalVertex<V> implements DisjointSet<KruskalVertex<V>> {
        private final V content;
        private int rank = 0;
        private KruskalVertex<V> parent = this;
        private final String string;
        private final int hash_code;

        public KruskalVertex(V n) {
            Objects.requireNonNull(n);
            content = n;
            string = String.format("KruskalVertex: %s", content.toString());
            hash_code = string.hashCode();
        }

        public V getContent() { return content; }

        @Override
        public boolean equals(Object other_vertex){
            if(other_vertex instanceof KruskalVertex){
                return content.equals(((KruskalVertex<?>) other_vertex).content);
            }else return false;
        }

        @Override
        public int hashCode(){ return hash_code; }
        
        @Override
        public int getRank() { return rank; }
        @Override
        public void setRank(int rank) { this.rank = rank; }
        
        @Override
        public KruskalVertex<V> getParent() { return parent; }
        @Override
        public void setParent(KruskalVertex<V> r) { this.parent = r; }

        @Override
        public String toString(){ return string; }
        
    }
    public static <T> Set<Graph.Edge<KruskalVertex<T>>> algorithmOfKruskal(Graph<KruskalVertex<T>> graph){
        Objects.requireNonNull(graph);
        Set<LinkedGraph.Edge<KruskalVertex<T>>> res = new HashSet<>();
        var edges_set = graph.GetAllEdges();
        var edges_list = new ArrayList<>(edges_set);
        edges_list.sort(Comparator.comparingDouble(LinkedGraph.Edge::getWeight));
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

    public static class PrimVertex<V> {
        private final V content;
        PrimVertex<V> parent;
        private double key = 0;
        private final String string;
        private final int hash_code;

        public PrimVertex(V name) {
            Objects.requireNonNull(name);
            this.content = name;
            string = String.format("PrimVertex: (%s)", content.toString());
            hash_code = string.hashCode();
        }

        public V getContent() { return content; }

        public double getKey() { return key; }

        @Override
        public int hashCode() { return hash_code; }

        @Override
        public boolean equals(Object other){
            if(this == other) return true;
            else if(!(other instanceof PrimVertex)) return false;
            else return content.equals(((PrimVertex<?>) other).content);
        }

        @Override
        public String toString() { return string.concat(String.format(" %f",key)); }
    }
    public static <T> void algorithmOfPrimWithFibonacciHeap(Graph<PrimVertex<T>> graph, PrimVertex<T> r){
        Objects.requireNonNull(r);
        Objects.requireNonNull(graph);
        FibonacciHeap<PrimVertex<T>> Q = new FibonacciHeap<>();
        var vertices = graph.GetAllVertices();
        for (var u : vertices) {
            if (!u.equals(r)) u.key = Double.POSITIVE_INFINITY;
            else {
                u.key = 0.0;
            }
            Q.insert(u.key, u);
            u.parent = null;
        }
        while (Q.length() > 0) {
            var u = Q.extractMin();
            var u_edges = graph.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                if (Q.contains(v) && edge.getWeight() < v.key) {
                    v.parent = u;
                    v.key = edge.getWeight();
                    Q.decreaseKey(v, v.key);
                }
            }
        }
    }
    public static <T> void algorithmOfPrimWithMinHeap(Graph<PrimVertex<T>> graph, PrimVertex<T> r){
        Objects.requireNonNull(r);
        Objects.requireNonNull(graph);
        var vertices = graph.GetAllVertices();
        for (var u : vertices) {
            if (!u.equals(r)) u.key = Double.POSITIVE_INFINITY;
            else {
                u.key = 0.0;
            }
            u.parent = null;
        }
        MinHeap<PrimVertex<T>> Q = new MinHeap<>(vertices, PrimVertex::getKey);
        while (Q.length() > 0) {
            var u = Q.extractMin();
            var u_edges = graph.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                if (Q.contains(v) && edge.getWeight() < v.key) {
                    v.parent = u;
                    v.key = edge.getWeight();
                    Q.decreaseKey(v, v.key);
                    int t = 1;
                }
            }
        }
    }
}