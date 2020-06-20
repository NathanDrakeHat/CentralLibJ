package algorithms.graph;


import java.util.*;


public final class Graph<V>  {
    public static final class Edge<T> {
        private final T former_vertex;
        private final T later_vertex;
        private final Direction edge_direction;
        private final String string;
        private final int hash_code;

        Edge(T former, T later, Direction is_directed){
            Objects.requireNonNull(former);
            Objects.requireNonNull(later);
            Objects.requireNonNull(is_directed);
            former_vertex = former;
            later_vertex = later;
            this.edge_direction = is_directed;
            if(edge_direction == Direction.DIRECTED)
                string =  String.format("[Edge(%s >>> %s)]", former_vertex, later_vertex);
            else
                string = String.format("[Edge(%s <-> %s)], %d", former_vertex, later_vertex, hashCode());
            if(edge_direction == Direction.DIRECTED)
                hash_code = Objects.hash(former_vertex, later_vertex, true);
            else{
                int t1 = Objects.hashCode(former_vertex);
                int t2 = Objects.hashCode(later_vertex);
                if(t1 <= t2)
                    hash_code = Objects.hash(former_vertex,later_vertex,false);
                else
                    hash_code = Objects.hash(later_vertex,former_vertex,false);
            }
        }

        @Override
        public boolean equals(Object other_edge) {
            if (other_edge == this) return true;
            else if (!(other_edge instanceof Edge)) return false;
            else {
                if (edge_direction != ((Edge<?>) other_edge).edge_direction) return false;
                else if (edge_direction == Direction.DIRECTED) {
                    return former_vertex.equals(((Edge<?>) other_edge).former_vertex) &&
                            later_vertex.equals(((Edge<?>) other_edge).later_vertex);
                } else {
                    return (former_vertex.equals(((Edge<?>) other_edge).former_vertex) &&
                            later_vertex.equals(((Edge<?>) other_edge).later_vertex)) ||

                            (later_vertex.equals(((Edge<?>) other_edge).former_vertex) &&
                                    former_vertex.equals(((Edge<?>) other_edge).later_vertex));
                }
            }
        }

        public T getFormerVertex() { return former_vertex; }

        public T getLaterVertex() { return later_vertex; }

        public T getAnotherSide(T vertex){
            if(vertex.equals(former_vertex)) return later_vertex;
            else if(vertex.equals(later_vertex)) return former_vertex;
            else throw new IllegalArgumentException();
        }

        @Override
        public String toString(){ return string; }

        @Override
        public int hashCode(){ return hash_code; }
    }
    public enum Direction{
        DIRECTED,NON_DIRECTED
    }
    private final Direction graph_direction;
    private final Map<V, Set<Edge<V>>> edge_map = new HashMap<>();
    private final Map<V, Set<V>> neighbors_map = new HashMap<>();
    private final Map<Edge<V>, Double> weight_map = new HashMap<>();
    private int size;

    public Graph(Collection<V> vertices, Direction is_directed){
        Objects.requireNonNull(is_directed);
        size = 0;
        for(var v : vertices) {
            Objects.requireNonNull(v);
            this.edge_map.put(v, new HashSet<>());
            neighbors_map.put(v, new HashSet<>());
            size++;
        }
        this.graph_direction = is_directed;
    }

    public int getVerticesCount() { return size; }

    public void setNeighbor(V vertex, V neighbor){ setNeighbor(vertex, neighbor, 1); }
    public void setNeighbor(V vertex, V neighbor, double w){
        var edge_t = new Edge<>(vertex, neighbor, graph_direction);
        if(graph_direction == Direction.DIRECTED) {
            var edges_set = edge_map.get(vertex);
            edges_set.add(edge_t);
            var neighbors_set = neighbors_map.get(vertex);
            neighbors_set.add(neighbor);

            weight_map.put(edge_t, w);
        }else{
            var edges_set = edge_map.get(vertex);
            edges_set.add(edge_t);
            var neighbors_set = neighbors_map.get(vertex);
            neighbors_set.add(neighbor);

            edges_set = edge_map.get(neighbor);
            edges_set.add(edge_t);
            neighbors_set = neighbors_map.get(neighbor);
            neighbors_set.add(vertex);

            weight_map.put(edge_t, w);
        }
    }

    public Set<Edge<V>> getAllEdges(){
        Set<Edge<V>> res = new HashSet<>();
        for(var edges : edge_map.values()){
            res.addAll(edges); }
        return res;
    }
    public Set<V> getAllVertices(){ return new HashSet<>(edge_map.keySet()); }
    public Set<V> getNeighborsAt(V vertex){ return new HashSet<>(neighbors_map.get(vertex)); }

    public double computeWeight(Edge<V> e){
        var t = weight_map.get(e);
        if(t == null) throw new NoSuchElementException();
        else return t;
    }
    public double computeWeight(V former, V later){
        var e = new Edge<>(former, later, graph_direction);
        var t = weight_map.get(e);
        if(t == null) throw new NoSuchElementException();
        return t;
    }
}
