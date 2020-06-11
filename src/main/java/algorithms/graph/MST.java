package algorithms.graph;

import structures.DisjointSet;
import tools.WeightedGraph;
import java.util.*;

// minimum spanning tree
public class MST {
    public static class KruskalVertex implements Comparable<KruskalVertex>, DisjointSet<KruskalVertex> {
        private final String name;
        private int rank = 0;
        private KruskalVertex parent = this;
        private double key;

        public KruskalVertex(String n) { name = n; }

        public String getName() { return name; }

        @Override
        public boolean equals(Object other_vertex){
            if(other_vertex instanceof KruskalVertex){
                return name.equals(((KruskalVertex) other_vertex).name);
            }else return false;
        }

        @Override
        public int hashCode(){ return name.hashCode(); }

        @Override
        public int compareTo(KruskalVertex other){
            return name.compareTo(other.name);
        }
        
        @Override
        public int getRank() { return rank; }
        @Override
        public void setRank(int rank) { this.rank = rank; }
        
        @Override
        public KruskalVertex getParent() { return parent; }
        @Override
        public void setParent(KruskalVertex r) { this.parent = r; }

        @Override
        public String toString(){ return String.format("KruskalVertex: %s", name); }
        
    }
    public static class PrimVertex implements Comparable<PrimVertex>{
        public final String name;
        public PrimVertex parent;
        public double key = 0;

        public PrimVertex(String name) { this.name = name; }

        public PrimVertex(PrimVertex other){
            this.name = other.name;
            this.parent = other.parent;
            this.key = other.key;
        }

        @Override
        public int hashCode() { return name.hashCode(); }

        @Override
        public int compareTo(PrimVertex other){
            int key_check =  Double.compare(key, other.key);
            if(key_check == 0) return name.compareTo(other.name);
            else return key_check;
        }

        @Override
        public boolean equals(Object other){
            if(other instanceof PrimVertex){
                return name.equals(((PrimVertex) other).name);
            }else return false;
        }

        @Override
        public String toString() { return String.format("PrimVertex %s, Key: %.2f", name,key); }
    }

    public static Set<WeightedGraph<KruskalVertex>.Edge> algorithmKruskal(WeightedGraph<KruskalVertex> graph){
        Set<WeightedGraph<KruskalVertex>.Edge> res = new HashSet<>();
        var edges_set = graph.getEdges();
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

    public static WeightedGraph<PrimVertex> algorithmPrim(WeightedGraph<PrimVertex> graph, PrimVertex r){
        Queue<PrimVertex> Q = new PriorityQueue<>();
        var vertexes = graph.getVertexes();
        for(var vertex : vertexes){
            if(!vertex.equals(r)) vertex.key = Double.POSITIVE_INFINITY;
            else {
                vertex.key = 0.0;
                Q.add(vertex); // init
            }
            vertex.parent = null;
        }
        while(!vertexes.isEmpty()){
            PrimVertex u;
            do { // ignore encountered vertex
                u = Q.remove();
            }while(!vertexes.contains(u));
            vertexes.remove(u);

            for(var edge : graph.getEdgesAt(u)){
                var v = edge.getAnotherVertex(u);
                if(vertexes.contains(v) & edge.getWeight() < v.key){
                    v.parent = u;
                    v.key = edge.getWeight();
                    Q.add(new PrimVertex(v)); // prevent update
                }
            }
        }
        return graph;
    }
}