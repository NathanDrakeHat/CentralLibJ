package graph;

import java.util.Objects;
import java.util.Set;

public interface Graph<V> {
    int getVerticesCount();

    void setNeighbor(V vertex, V neighbor);

    void setNeighbor(V vertex, V neighbor, double w);

    Set<Edge<V>> GetAllEdges();

    Set<V> GetAllVertices();

    Set<Edge<V>> getEdgesAt(V vertex);

    enum Direction{
        DIRECTED,NON_DIRECTED
    }

    final class Edge<T> {
        private final T former_vertex;
        private final T later_vertex;
        private final Direction edge_direction;
        private final double weight;
        private final String string;
        private final int hash_code;

        Edge(T former, T later, double weight, Direction is_directed){
            Objects.requireNonNull(former);
            Objects.requireNonNull(later);
            Objects.requireNonNull(is_directed);
            this.weight = weight;
            former_vertex = former;
            later_vertex = later;
            this.edge_direction = is_directed;
            if(edge_direction == Graph.Direction.DIRECTED)
                string =  String.format("[Edge(%s >>> %s)], weight:%f", former_vertex, later_vertex,weight);
            else
                string = String.format("[Edge(%s <-> %s)], weight:%f", former_vertex, later_vertex,weight);

            if(edge_direction == Graph.Direction.DIRECTED)
                hash_code = Objects.hash(former_vertex, later_vertex,weight, true);
            else{
                int t1 = Objects.hashCode(former_vertex);
                int t2 = Objects.hashCode(later_vertex);
                if(t1 <= t2)
                    hash_code = Objects.hash(former_vertex,later_vertex,weight,false);
                else
                    hash_code = Objects.hash(later_vertex,former_vertex,weight,false);
            }
        }

        @Override
        public boolean equals(Object other_edge) {
            if (other_edge == this) return true;
            if (!(other_edge instanceof Edge)) return false;
            if (edge_direction != ((Edge<?>) other_edge).edge_direction) return false;
            if(weight != ((Edge<?>) other_edge).weight) return false;

            if (edge_direction == Graph.Direction.DIRECTED) {
                return former_vertex.equals(((Edge<?>) other_edge).former_vertex) &&
                        later_vertex.equals(((Edge<?>) other_edge).later_vertex);
            } else {
                return ( former_vertex.equals(((Edge<?>) other_edge).former_vertex) &&
                        later_vertex.equals(((Edge<?>) other_edge).later_vertex) ) ||

                        ( later_vertex.equals(((Edge<?>) other_edge).former_vertex) &&
                                former_vertex.equals(((Edge<?>) other_edge).later_vertex) );
            }
        }

        public T getFormerVertex() { return former_vertex; }

        public T getLaterVertex() { return later_vertex; }

        public T getAnotherSide(T vertex){
            if(vertex.equals(former_vertex)) return later_vertex;
            else if(vertex.equals(later_vertex)) return former_vertex;
            else throw new IllegalArgumentException();
        }

        public double getWeight() { return weight; }

        @Override
        public String toString(){ return string; }

        @Override
        public int hashCode(){ return hash_code; }
    }
}
