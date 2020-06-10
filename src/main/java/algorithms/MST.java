package algorithms;

import tools.WeightedGraph;

// minimum spanning tree
public class MST {
    public static class Vertex implements Comparable<Vertex>{
        private final String name;

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
    }

    public void algorithmKruskal(WeightedGraph<Vertex> graph){

    }
}
