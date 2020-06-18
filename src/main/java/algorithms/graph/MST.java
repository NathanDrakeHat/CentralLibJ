package algorithms.graph;

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
            content = n;
            string = String.format("KruskalVertex: %s", content.toString());
            hash_code = string.hashCode();
        }

        public V getContent() { return content; }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_vertex){
            if(other_vertex instanceof KruskalVertex){
                return content.equals(((KruskalVertex<V>) other_vertex).content);
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
    public static <T> Set<Graph<KruskalVertex<T>>.Edge> algorithmOfKruskal(Graph<KruskalVertex<T>> graph){
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

    public static class PrimVertex<V>{
        private final V content;
        PrimVertex<V> parent;
        private double key = 0;
        private final String string;
        private final int hash_code;

        public PrimVertex(V name) {
            this.content = name;
            string = String.format("PrimVertex: (%s)", content.toString());
            hash_code = string.hashCode();
        }

        public V getContent() { return content; }

        public double getKey() { return key; }

        @Override
        public int hashCode() { return hash_code; }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other){
            if(other instanceof PrimVertex){
                return content.equals(((PrimVertex<V>) other).content);
            }else return false;
        }

        @Override
        public String toString() { return string; }
    }
    public enum PrimQueue{
        MIN_HEAP, FIBONACCI_HEAP
    }
    public static <T> void algorithmOfPrimWithFibonacciHeap(Graph<PrimVertex<T>> graph, PrimVertex<T> r){
        FibonacciHeap<PrimVertex<T>> Q = new FibonacciHeap<>();
        for (var u : graph.getAllVertices()) {
            if (!u.equals(r)) u.key = Double.POSITIVE_INFINITY;
            else {
                u.key = 0.0;
            }
            Q.insert(u.key, u);
            u.parent = null;
        }
        while (Q.length() > 0) {
            var u = Q.extractMin();
            for (var v : graph.getNeighborsAt(u)) {
                if (Q.contains(v) && graph.computeWeight(u, v) < v.key) {
                    v.parent = u;
                    v.key = graph.computeWeight(u, v);
                    Q.decreaseKey(v, v.key);
                }
            }
        }
    }

    public static <T> void algorithmOfPrimWithMinHeap(Graph<PrimVertex<T>> graph, PrimVertex<T> r){
        var vertices = graph.getAllVertices();
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
            for (var v : graph.getNeighborsAt(u)) {
                if (Q.contains(v) && graph.computeWeight(u, v) < v.key) {
                    v.parent = u;
                    v.key = graph.computeWeight(u, v);
                    Q.decreaseKey(v, v.key);
                }
            }
        }
    }
}