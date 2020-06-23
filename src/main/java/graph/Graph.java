package graph;


import java.util.*;


public final class Graph<V>{
    private final Direction graph_direction;
    private final List<V> vertices = new ArrayList<>();
    private final Map<V, List<Edge<V>>> edge_map = new HashMap<>();
    private int size;
    enum Direction{
        DIRECTED,NON_DIRECTED
    }

    static final class Edge<T> {
        private final T former_vertex;
        private final T later_vertex;
        private final Direction edge_direction;
        double weight;


        Edge(T former, T later, double weight, Direction is_directed){
            Objects.requireNonNull(former);
            Objects.requireNonNull(later);
            Objects.requireNonNull(is_directed);
            this.weight = weight;
            former_vertex = former;
            later_vertex = later;
            this.edge_direction = is_directed;
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

        public Direction getEdgeDirection() { return edge_direction; }

        @Override
        public String toString(){
            if(edge_direction == Graph.Direction.DIRECTED)
                return String.format("[Edge(%s >>> %s)], weight:%f", former_vertex, later_vertex,weight);
            else
                return String.format("[Edge(%s <-> %s)], weight:%f", former_vertex, later_vertex,weight);
        }

        @Override
        public int hashCode(){
            if(edge_direction == Graph.Direction.DIRECTED)
                return Objects.hash(former_vertex, later_vertex,weight, true);
            else{
                int t1 = Objects.hashCode(former_vertex);
                int t2 = Objects.hashCode(later_vertex);
                if(t1 <= t2)
                    return Objects.hash(former_vertex,later_vertex,weight,false);
                else
                    return Objects.hash(later_vertex,former_vertex,weight,false);
            }
        }
    }

    public Graph(List<V> vertices, Direction is_directed){
        Objects.requireNonNull(is_directed);
        Objects.requireNonNull(vertices);
        size = 0;
        for(var vertex : vertices) {
            Objects.requireNonNull(vertex);
            this.edge_map.put(vertex, new ArrayList<>());
            size++;
            this.vertices.add(vertex);
        }
        this.graph_direction = is_directed;
    }

    public int getVerticesCount() { return size; }

    public void setNeighbor(V vertex, V neighbor){ setNeighbor(vertex, neighbor, 1); }
    public void setNeighbor(V vertex, V neighbor, double w){
        var edge_t = new Edge<>(vertex, neighbor, w, graph_direction);
        if(graph_direction == Direction.DIRECTED) {
            var edges_set = edge_map.get(vertex);
            edges_set.add(edge_t);
        }else{
            var edges_set = edge_map.get(vertex);
            edges_set.add(edge_t);

            edges_set = edge_map.get(neighbor);
            edges_set.add(edge_t);
        }
    }

    public List<Edge<V>> getAllEdges(){
        List<Edge<V>> res = new ArrayList<>();
        for(var edges : edge_map.values()){
            res.addAll(edges); }
        return res;
    }
    public List<V> getAllVertices(){ return new ArrayList<>(vertices); }
    public List<Edge<V>> getEdgesAt(V vertex){
        return new ArrayList<>(edge_map.get(vertex));
    }

}
