package tools;

import java.util.*;

public class WeightedGraph<V extends Comparable<V>> extends LinkedGraph<V> {
    public class Edge {
        private final V one_vertex;
        private final V another_vertex;
        private final double weight;

        public Edge(V a, V b, double weight){
            one_vertex = a;
            another_vertex = b;
            this.weight = weight;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_edge){
            if(other_edge == null){
                return false;
            }else if(Edge.class.equals(other_edge.getClass())){
                if(one_vertex.equals(((Edge) other_edge).one_vertex) &
                        another_vertex.equals(((Edge)other_edge).another_vertex) &
                        this.weight == ((Edge) other_edge).weight){
                    return true;
                }else return (one_vertex.equals(((Edge) other_edge).another_vertex)) &
                        (another_vertex.equals(((Edge) other_edge).one_vertex) &
                                (this.weight == ((Edge) other_edge).weight));
            }else{
                return false;
            }
        }

        public V getOneVertex() { return one_vertex; }

        public V getAnotherVertex() { return another_vertex; }

        public double getWeight() { return weight; }
    }
    Map<V, List<Edge>> edge_map = new HashMap<>();

    public WeightedGraph(){ super(); }
    public WeightedGraph(V[] vertexes){
        super(vertexes);
        for(var v : vertexes){
            edge_map.put(v, new ArrayList<>());
        }
    }

    public void setNeighbors(V v, V[] vertexes, double[] weights){
        int len = vertexes.length;
        if(weights.length != len) throw new IllegalArgumentException();
        super.setNeighbors(v, vertexes);

        List<Edge> edges_list = edge_map.get(v);
        if(edges_list != null) { edges_list.clear(); }
        else{ edges_list = new ArrayList<>(); }
        for(int i = 0; i < len; i++){
            edges_list.add(new Edge(v, vertexes[i], weights[i]));
        }

        for(int i = 0; i < len; i++){
            edges_list = edge_map.get(vertexes[i]);
            if(edges_list != null) { edges_list.clear(); }
            else{ edges_list = new ArrayList<>(); }
            edges_list.add(new Edge(v, vertexes[i], weights[i]));
        }
    }
    @Override
    public void clearNeighbors(V v){
        super.clearNeighbors(v);
        edge_map.get(v).clear();
    }

    public void putNeighbor(V v, V n, double w){
        addNeighbor(v, n, w);
        addNeighbor(n ,v, w);
    }

    private void addNeighbor(V v, V n, double w){
        super.addNeighbor(v, n);
        var edges_list = edge_map.get(v);
        if(edges_list != null){
            var edge_t = new Edge(v, n, w);
            if(!edges_list.contains(edge_t)) edges_list.add(edge_t);
        }else{
            edges_list = new ArrayList<>();
            edge_map.put(v, edges_list);
            edges_list.add(new Edge(v, n ,w));
        }
    }
    @Override
    public void removeNeighbor(V v, V n){
        super.removeNeighbor(v, n);
        edge_map.get(v).clear();
    }

    public List<Edge> getEdges(V v) { return edge_map.get(v); }
}