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
    public static class ResidualFlowEdge<T> {
        private final T former_vertex;
        private final T later_vertex;
        double flow;

        ResidualFlowEdge(T former, T later, double flow){
            Objects.requireNonNull(former);
            Objects.requireNonNull(later);
            former_vertex = former;
            later_vertex = later;
            this.flow = flow;
        }

        @Override
        public boolean equals(Object other_edge) {
            if (other_edge == this) return true;
            if (!(other_edge instanceof ResidualFlowEdge)) return false;
            return former_vertex.equals(((ResidualFlowEdge<?>) other_edge).former_vertex) &&
                    later_vertex.equals((((ResidualFlowEdge<?>) other_edge).later_vertex));
        }

        public T getFormerVertex() { return former_vertex; }

        public T getLaterVertex() { return later_vertex; }

        public double getFlow() { return flow; }

        @Override
        public String toString(){
            return String.format("[ResidualFlowEdge(%s >>> %s)], flow:%f", former_vertex, later_vertex,flow);
        }

        @Override
        public int hashCode(){ return Objects.hash(former_vertex, later_vertex); }
    }
    private final Map<V, List<FlowEdge<V>>> edges_map = new HashMap<>();

    public FlowGraph(List<V> vertices){
        Objects.requireNonNull(vertices);
        for(var vertex : vertices) {
            Objects.requireNonNull(vertex);
            this.edges_map.put(vertex, new ArrayList<>());
        }
    }

    public void setNeighbor(V vertex, V neighbor, double capacity){
        Objects.requireNonNull(vertex);
        Objects.requireNonNull(neighbor);
        var edges_list = edges_map.get(vertex);
        edges_list.add(new FlowEdge<>(vertex,neighbor,capacity));
    }

    public List<FlowEdge<V>> getNeighborEdges(V vertex){ return new ArrayList<>(edges_map.get(vertex)); }

    public double getResidualCapacity(V former, V later){
        var o1 = tryGetFlowEdge(former,later);
        if(o1.isPresent()){
            var e = o1.get();
            return e.capacity - e.flow;
        }else{
            var o2 = tryGetFlowEdge(later,former);
            return o2.map(edge -> edge.flow).orElse(0.0);
        }
    }

    public Optional<FlowEdge<V>> tryGetFlowEdge(V vertex, V another){
        var edges_list = edges_map.get(vertex);
        for(var e : edges_list){
            if(e.later_vertex.equals(another)){
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    public double getCapacity(V u, V v){
        var o = tryGetFlowEdge(u,v);
        if(o.isPresent()){
            return o.get().capacity;
        };
        throw new NoSuchElementException();
    }
}
