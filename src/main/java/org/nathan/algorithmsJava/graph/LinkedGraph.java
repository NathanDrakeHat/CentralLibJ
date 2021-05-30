package org.nathan.algorithmsJava.graph;


import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * static graph
 * @param <V>
 */
public class LinkedGraph<V extends Vertex<?>>{
    private final boolean directed;
    private final List<V> vertices;
    private final Map<V, List<ComplexEdge<V>>> edges_map;

    LinkedGraph(int size, boolean is_directed){
        directed = is_directed;
        vertices  = new ArrayList<>(size);
        edges_map = new HashMap<>(size);
    }

    public LinkedGraph(@NotNull List<V> vertices, boolean is_directed){
        this(vertices.size(), is_directed);
        for(var vertex : vertices){
            Objects.requireNonNull(vertex);
            this.edges_map.put(vertex, new ArrayList<>());
            this.vertices.add(vertex);
        }
    }

    public LinkedGraph(@NotNull LinkedGraph<V> other_graph){
        this(other_graph.verticesCount(), other_graph.directed);
        this.edges_map.putAll(other_graph.edges_map);
        this.vertices.addAll(other_graph.vertices);
    }

    /**
     * change wrapper of vertex
     * @param other_graph other graph
     * @param mapper map function
     * @param <OV> other vertex wrapper
     */
    public <OV extends Vertex<?>> LinkedGraph(@NotNull LinkedGraph<OV> other_graph, Function<OV, V> mapper){
        this(other_graph.verticesCount(), other_graph.directed);
        Map<OV, V> mapRecord = new HashMap<>(verticesCount());
        other_graph.vertices.forEach(otherV -> {
            var mapped = mapper.apply(otherV);
            vertices.add(mapped);
            mapRecord.put(otherV, mapped);
        });
        other_graph.edges_map.forEach(((otherV, edges) ->
                edges_map.put(
                        mapRecord.get(otherV),
                        edges.parallelStream().map(edge ->
                                new ComplexEdge<>(
                                        mapRecord.get(edge.former()),
                                        mapRecord.get(edge.later()),
                                        edge.weight(),
                                        edge.directed()))
                                .collect(Collectors.toList()))));
    }

    public void setNeighbor(@NotNull V vertex, @NotNull V neighbor){
        setNeighbor(vertex, neighbor, 1);
    }

    public void setNeighbor(@NotNull V vertex, @NotNull V neighbor, double w){
        var edge_t = new ComplexEdge<>(vertex, neighbor, w, directed);
        if(directed){
            var edges_list = edges_map.get(vertex);
            edges_list.add(edge_t);
        }
        else{
            var edges_list = edges_map.get(vertex);
            edges_list.add(edge_t);

            edges_list = edges_map.get(neighbor);
            edges_list.add(edge_t);
        }
    }

    public void addNewVertex(@NotNull V vertex){
        if(vertices.contains(vertex) || edges_map.containsKey(vertex)){
            throw new IllegalArgumentException("repeated vertex");
        }
        vertices.add(vertex);
        edges_map.put(vertex, new ArrayList<>());
    }

    public int verticesCount(){
        return vertices.size();
    }

    public @NotNull List<ComplexEdge<V>> getAllEdges(){
        List<ComplexEdge<V>> res = new ArrayList<>();
        for(var vertex : vertices){
            res.addAll(edges_map.get(vertex));
        }
        return res;
    }

    /**
     * @return unmodifiable list
     */
    public @NotNull List<V> allVertices(){
        return Collections.unmodifiableList(vertices);
    }

    /**
     * @param vertex vertex
     * @return unmodifiable list
     */
    public @NotNull List<ComplexEdge<V>> edgesAt(V vertex){
        return Collections.unmodifiableList(edges_map.get(vertex));
    }
}
