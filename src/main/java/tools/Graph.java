package tools;

import java.util.*;
import java.util.stream.Collectors;


public final class Graph<V extends Comparable<V>>  {
    private final boolean graph_directed;
    public class Edge implements Comparable<Edge> {
        private final V former_vertex;
        private final V later_vertex;
        private final boolean directed = graph_directed;

        public Edge(V small, V bigger){
            if(small.compareTo(bigger) <= 0){
                former_vertex = small;
                later_vertex = bigger;
            }else{
                former_vertex = bigger;
                later_vertex = small;
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_edge){
            if(other_edge == null){
                return false;
            }else if(Edge.class.equals(other_edge.getClass())){
                if(directed != ((Edge) other_edge).directed) return false;
                else if(directed) {
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

        public V getAnotherVertex(V vertex){
            if(former_vertex.equals(vertex)) return later_vertex;
            else if(later_vertex.equals(vertex)) return former_vertex;
            else throw new IllegalArgumentException("Not match.");
        }

        private V getSmallerVertex(){
            if(former_vertex.compareTo(later_vertex) <= 0) return former_vertex;
            else return later_vertex;
        }
        private V getBiggerVertex(){
            if(former_vertex.compareTo(later_vertex) > 0) return former_vertex;
            else return later_vertex;
        }

        @Override
        public int compareTo(Edge other){ 
            if(directed != other.directed) throw new IllegalArgumentException("Not same type edge");
            else{
                if(directed){
                    var l = former_vertex.compareTo(other.former_vertex);
                    if(l == 0) return later_vertex.compareTo(other.later_vertex);
                    else return l;
                }else{
                    var l1 = this.getSmallerVertex();
                    var l2 = other.getSmallerVertex();
                    var m1 = this.getBiggerVertex();
                    var m2 = other.getBiggerVertex();
                    var t = l1.compareTo(l2);
                    if(t == 0) return m1.compareTo(m2);
                    else return t;
                }
            }
        }

        @Override
        public String toString(){
            if(directed) return String.format("Edge(%s --> %s)", former_vertex, later_vertex);
            else return String.format("Edge(%s <--> %s)", former_vertex, later_vertex);
        }

        @Override
        public int hashCode(){
            if(directed) return Objects.hash(former_vertex, later_vertex, true);
            else return Objects.hash(getSmallerVertex(), getBiggerVertex(), false);
        }
    }
    private final Map<V, Set<V>> neighbors_map = new HashMap<>();
    private final Map<Edge, Double> weight_map = new HashMap<>();

    public Graph(boolean is_directed){  this.graph_directed = is_directed; }
    public Graph(V[] vertices,boolean is_directed){
        for(var v : vertices) {
            this.neighbors_map.put(v, null);}
        this.graph_directed = is_directed;
    }
    public Graph(List<V> vertices,boolean is_directed){
        for(var v : vertices) {
            this.neighbors_map.put(v, null);}
        this.graph_directed = is_directed;
    }

    public void setNeighbor(V vertex, V neighbor){ setNeighbor(vertex, neighbor, 1); }
    public void setNeighbor(V vertex, V neighbor, double w){
        var edge_t = new Edge(vertex, neighbor);
        if(graph_directed) {
            var neighbors_set = neighbors_map.computeIfAbsent(vertex, (k)->new HashSet<>());
            neighbors_set.add(neighbor);
            weight_map.put(edge_t, w);
        }else{
            var neighbors_set = neighbors_map.computeIfAbsent(vertex, (k)->new HashSet<>());
            neighbors_set.add(neighbor);
            neighbors_set = neighbors_map.computeIfAbsent(neighbor, (k)->new HashSet<>());
            neighbors_set.add(vertex);
            weight_map.put(edge_t, w);
            edge_t = new Edge(neighbor,vertex);
            weight_map.put(edge_t, w);
        }
    }

    public Set<Edge> getEdgesAt(V vertex) {
        return neighbors_map.get(vertex).stream().map((n)->new Edge(vertex,n)).collect(Collectors.toSet());
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
    public void putVertex(V vertex) { neighbors_map.put(vertex, new TreeSet<>()); }
    public boolean hasVertex(V vertex) { return neighbors_map.containsKey(vertex); }

    public Set<V> getNeighborsAt(V vertex){
        return new HashSet<>(neighbors_map.computeIfAbsent(vertex,(k)->new HashSet<>()));
    }
    public boolean hasOneNeighbor(V vertex, V neighbor) {
        return neighbors_map.get(vertex).contains(neighbor);
    }

    public double computeWeight(Edge e){
        var t = weight_map.get(e);
        if(t == null) throw new NoSuchElementException();
        else return t;
    }
    public double computeWeight(V former, V later){
        var e = new Edge(former, later);
        var t = weight_map.get(e);
        if(t == null) throw new NoSuchElementException();
        else return t;
    }
}
