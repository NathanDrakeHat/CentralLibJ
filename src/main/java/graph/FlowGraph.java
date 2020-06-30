package graph;

import java.util.*;

public class FlowGraph<V> {
    public static class FlowEdge<T> {
        private final T former_vertex;
        private final T later_vertex;
        double capacity;
        double flow;

        FlowEdge(T former, T later, double capacity){
            Objects.requireNonNull(former);
            Objects.requireNonNull(later);
            this.capacity = capacity;
            former_vertex = former;
            later_vertex = later;
        }

        @Override
        public boolean equals(Object other_edge) {
            if (other_edge == this) return true;
            if (!(other_edge instanceof FlowEdge)) return false;
            if(capacity != (((FlowEdge<?>) other_edge).capacity)) return false;
            return former_vertex.equals(((FlowEdge<?>) other_edge).former_vertex) &&
                        later_vertex.equals((((FlowEdge<?>) other_edge).later_vertex));
        }

        public T getFormerVertex() { return former_vertex; }

        public T getLaterVertex() { return later_vertex; }

        public double getCapacity() { return capacity; }

        public double getFlow() { return flow; }

        @Override
        public String toString(){
            return String.format("[FlowEdge(%s >>> %s)], capacity:%f", former_vertex, later_vertex,capacity);
        }

        @Override
        public int hashCode(){ return Objects.hash(former_vertex, later_vertex, capacity); }
    }
    private final Map<V, List<FlowEdge<V>>> edges_map = new HashMap<>();
    private int size;

    public FlowGraph(List<V> vertices){
        Objects.requireNonNull(vertices);
        size = 0;
        for(var vertex : vertices) {
            Objects.requireNonNull(vertex);
            this.edges_map.put(vertex, new ArrayList<>());
            size++;
        }
    }

    public void setNeighbor(V vertex, V neighbor, double capacity){
        Objects.requireNonNull(vertex);
        Objects.requireNonNull(neighbor);
        var edges_list = edges_map.get(vertex);
        edges_list.add(new FlowEdge<>(vertex,neighbor,capacity));
    }

    public List<FlowEdge<V>> getFlowEdges(V vertex){ return new ArrayList<>(edges_map.get(vertex)); }

    public int getVerticesCount() { return size; }
}
