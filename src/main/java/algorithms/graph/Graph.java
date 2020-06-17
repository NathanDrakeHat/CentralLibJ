package algorithms.graph;


import java.util.*;


public final class Graph<V>  {
    public enum Direction{
        DIRECTED,NON_DIRECTED
    }
    private final Direction graph_direction;
    private final Map<V, Set<V>> neighbors_map = new HashMap<>();
    private final Map<Edge, Double> weight_map = new HashMap<>();
    final class Edge {
        private final V former_vertex;
        private final V later_vertex;
        private final Direction edge_direction;

        Edge(V former, V later, Direction is_directed){
            former_vertex = former;
            later_vertex = later;
            this.edge_direction = is_directed;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_edge){
            if(other_edge == null){
                return false;
            }else if(Edge.class.equals(other_edge.getClass())){
                if(edge_direction != ((Edge) other_edge).edge_direction) return false;
                else if(edge_direction == Direction.DIRECTED) {
                    return former_vertex.equals(((Edge) other_edge).former_vertex) &&
                            later_vertex.equals(((Edge) other_edge).later_vertex);
                }else {
                    return (former_vertex.equals(((Edge) other_edge).former_vertex) &&
                            later_vertex.equals(((Edge) other_edge).later_vertex)) ||

                            (later_vertex.equals(((Edge) other_edge).former_vertex) &&
                                    former_vertex.equals(((Edge) other_edge).later_vertex));
                }
            }else{
                return false;
            }
        }

        public V getFormerVertex() { return former_vertex; }

        public V getLaterVertex() { return later_vertex; }

        public V getAnotherSide(V vertex){
            if(former_vertex.equals(vertex)) return later_vertex;
            else if(later_vertex.equals(vertex)) return former_vertex;
            else throw new IllegalArgumentException("Not match.");
        }

        @Override
        public String toString(){
            if(edge_direction == Direction.DIRECTED)
                return String.format("[Edge(%s >>> %s)]", former_vertex, later_vertex);
            else
                return String.format("[Edge(%s <-> %s)], %d", former_vertex, later_vertex, hashCode());
        }

        @Override
        public int hashCode(){
            if(edge_direction == Direction.DIRECTED) return Objects.hash(former_vertex, later_vertex, true);
            else{
                int t1 = Objects.hashCode(former_vertex);
                int t2 = Objects.hashCode(later_vertex);
                if(t1 <= t2) return Objects.hash(former_vertex,later_vertex,false);
                else return Objects.hash(later_vertex,former_vertex,false);
            }
        }
    }

    public Graph(List<V> vertices, Direction is_directed){
        for(var v : vertices) {
            this.neighbors_map.put(v, new HashSet<>());}
        this.graph_direction = is_directed;
    }
    public Graph(Set<V> vertices, Direction is_directed){
        for(var v : vertices) {
            this.neighbors_map.put(v, new HashSet<>());}
        this.graph_direction = is_directed;
    }

    public void setNeighbor(V vertex, V neighbor){ setNeighbor(vertex, neighbor, 1); }
    public void setNeighbor(V vertex, V neighbor, double w){
        var edge_t = new Edge(vertex, neighbor, graph_direction);
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
            edge_t = new Edge(neighbor,vertex,graph_direction);
            weight_map.put(edge_t, w);
        }
    }

    public Set<Edge> getEdgesAt(V vertex) {
        var neighbors = getNeighborsAt(vertex);
        Set<Edge> res = new HashSet<>();
        for(var n : neighbors) res.add(new Edge(vertex, n,graph_direction));
        return res;
    }
    public Set<Edge> getAllEdges(){
        Set<Edge> res = new HashSet<>();
        for(var vertex : getAllVertices()){
            res.addAll(getEdgesAt(vertex));
        }
        return res;
    }
    public Set<V> getAllVertices(){
        return new HashSet<>(neighbors_map.keySet());
    }


    public Set<V> getNeighborsAt(V vertex){
        return new HashSet<>(neighbors_map.computeIfAbsent(vertex,(k)->new HashSet<>()));
    }

    public double computeWeight(Edge e){
        var t = weight_map.get(e);
        if(t == null) throw new NoSuchElementException();
        else return t;
    }
    public double computeWeight(V former, V later){
        var e = new Edge(former, later, graph_direction);
        var t = weight_map.get(e);
        if(t == null) throw new NoSuchElementException();
        return t;
    }

}
