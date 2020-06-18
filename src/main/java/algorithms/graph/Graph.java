package algorithms.graph;


import java.util.*;


public final class Graph<V>  {
    public enum Direction{
        DIRECTED,NON_DIRECTED
    }
    private final Direction graph_direction;
    private final Map<V, Set<V>> neighbors_map = new HashMap<>();
    private final Map<Edge<V>, Double> weight_map = new HashMap<>();
    private int size;
    public static final class Edge<T> {
        private final T former_vertex;
        private final T later_vertex;
        private final Direction edge_direction;
        private final String string;
        private final int hash_code;

        Edge(T former, T later, Direction is_directed){
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
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_edge) {
            if (other_edge == this) return true;
             else if (!(other_edge instanceof Edge)) return false;
             else {
                if (edge_direction != ((Edge<T>) other_edge).edge_direction) return false;
                else if (edge_direction == Direction.DIRECTED) {
                    return former_vertex.equals(((Edge<T>) other_edge).former_vertex) &&
                            later_vertex.equals(((Edge<T>) other_edge).later_vertex);
                } else {
                    return (former_vertex.equals(((Edge<T>) other_edge).former_vertex) &&
                            later_vertex.equals(((Edge<T>) other_edge).later_vertex)) ||

                            (later_vertex.equals(((Edge<T>) other_edge).former_vertex) &&
                                    former_vertex.equals(((Edge<T>) other_edge).later_vertex));
                }
            }
        }

        public T getFormerVertex() { return former_vertex; }

        public T getLaterVertex() { return later_vertex; }

        @Override
        public String toString(){ return string; }

        @Override
        public int hashCode(){ return hash_code; }
    }

    public Graph(Collection<V> vertices, Direction is_directed){
        size = 0;
        for(var v : vertices) {
            this.neighbors_map.put(v, new HashSet<>());
            size++;
        }
        this.graph_direction = is_directed;
    }

    public int getVerticesCount() { return size; }

    public void setNeighbor(V vertex, V neighbor){ setNeighbor(vertex, neighbor, 1); }
    public void setNeighbor(V vertex, V neighbor, double w){
        var edge_t = new Edge<>(vertex, neighbor, graph_direction);
        if(graph_direction == Direction.DIRECTED) {
            var neighbors_set = neighbors_map.computeIfAbsent(vertex, (k)->new HashSet<>());
            neighbors_set.add(neighbor);
            weight_map.put(edge_t, w);
        }else{
            var neighbors_set = neighbors_map.computeIfAbsent(vertex, (k)->new HashSet<>());
            neighbors_set.add(neighbor);
            neighbors_set = neighbors_map.computeIfAbsent(neighbor, (k)->new HashSet<>());
            neighbors_set.add(vertex);
            weight_map.put(edge_t, w);
            edge_t = new Edge<>(neighbor,vertex,graph_direction);
            weight_map.put(edge_t, w);
        }
    }

    public Set<Edge<V>> getAllEdges(){
        Set<Edge<V>> res = new HashSet<>();
        for(var vertex : getAllVertices()){
            res.addAll(getEdgesAt(vertex));
        }
        return res;
    }
    public Set<V> getAllVertices(){
        return new HashSet<>(neighbors_map.keySet());
    }
    public Set<Edge<V>> getEdgesAt(V vertex) {
        var neighbors = getNeighborsAt(vertex);
        Set<Edge<V>> res = new HashSet<>();
        for(var n : neighbors) res.add(new Edge<>(vertex, n,graph_direction));
        return res;
    }
    public Set<V> getNeighborsAt(V vertex){
        return new HashSet<>(neighbors_map.computeIfAbsent(vertex,(k)->new HashSet<>()));
    }

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
