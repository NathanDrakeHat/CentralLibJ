package graph;


import java.util.*;


public final class LinkedGraph<V> implements Graph<V> {
    private final Direction graph_direction;
    private final Map<V, Set<Edge<V>>> edge_map = new HashMap<>();
    private int size;

    public LinkedGraph(Collection<V> vertices, Direction is_directed){
        Objects.requireNonNull(is_directed);
        Objects.requireNonNull(vertices);
        size = 0;
        for(var v : vertices) {
            Objects.requireNonNull(v);
            this.edge_map.put(v, new HashSet<>());
            size++;
        }
        this.graph_direction = is_directed;
    }

    @Override
    public int getVerticesCount() { return size; }

    @Override
    public void setNeighbor(V vertex, V neighbor){ setNeighbor(vertex, neighbor, 1); }
    @Override
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

    @Override
    public Set<Edge<V>> GetAllEdges(){
        Set<Edge<V>> res = new HashSet<>();
        for(var edges : edge_map.values()){
            res.addAll(edges); }
        return res;
    }
    @Override
    public Set<V> GetAllVertices(){ return new HashSet<>(edge_map.keySet()); }
    @Override
    public Set<Edge<V>> getEdgesAt(V vertex){
        return new HashSet<>(edge_map.get(vertex));
    }

}
