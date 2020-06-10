package algorithms;

import structures.DisjointSet;
import tools.WeightedGraph;

import java.util.*;

// minimum spanning tree
public class MST {
    public static class Vertex implements Comparable<Vertex>, DisjointSet<Vertex> {
        private final String name;
        private int rank = 0;
        private Vertex parent = this;

        public Vertex(String n) { name = n; }

        public String getName() { return name; }

        @Override
        public boolean equals(Object other_vertex){
            if(other_vertex instanceof Vertex){
                return name.equals(((Vertex) other_vertex).name);
            }else return false;
        }

        @Override
        public int hashCode(){ return name.hashCode(); }

        @Override
        public int compareTo(Vertex other){
            return name.compareTo(other.name);
        }
        
        @Override
        public int getRank() { return rank; }
        @Override
        public void setRank(int rank) { this.rank = rank; }
        
        @Override
        public Vertex getParent() { return parent; }
        @Override
        public void setParent(Vertex r) { this.parent = r; }

        @Override
        public String toString(){
            return name;
        }
        
    }

    public static List<WeightedGraph<Vertex>.Edge> algorithmKruskal(WeightedGraph<Vertex> graph){
        List<WeightedGraph<Vertex>.Edge> res = new ArrayList<>();
        var edges_list = graph.getEdges();
        Collections.sort(edges_list);
        for(var edge : edges_list){
            var v1 = edge.getOneVertex();
            var v2 = edge.getAnotherVertex();
            if(DisjointSet.findSet(v1) != DisjointSet.findSet(v2)){
                res.add(edge);
                DisjointSet.union(v1, v2);
            }
        }
        return res;
    }
}
