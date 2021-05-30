package org.nathan.algorithmsJava.graph;


import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public final class LinkedGraph<V>{
    private final boolean directed;
    private final List<V> vertices = new ArrayList<>();
    private final Map<V, List<GraphEdge<V>>> edges_map = new HashMap<>();
    private int size;

    public LinkedGraph(@NotNull List<V> vertices, boolean is_directed){
        size = 0;
        for(var vertex : vertices){
            Objects.requireNonNull(vertex);
            this.edges_map.put(vertex, new ArrayList<>());
            this.vertices.add(vertex);
            size++;
        }
        this.directed = is_directed;
    }

    public LinkedGraph(@NotNull LinkedGraph<V> other_graph){
        size = other_graph.vertices.size();
        this.directed = other_graph.directed;
        this.edges_map.putAll(other_graph.edges_map);
        this.vertices.addAll(other_graph.vertices);
    }

    /**
     * change wrapper of vertex
     * @param other_graph other graph
     * @param mapper map function
     * @param <OtherV> other vertex wrapper
     */
    public <OtherV> LinkedGraph(@NotNull LinkedGraph<OtherV> other_graph, Function<OtherV, V> mapper){
        size = other_graph.vertices.size();
        directed = other_graph.directed;
        Map<OtherV, V> mapRecord = new HashMap<>(size);
        other_graph.vertices.forEach(otherV -> {
            var mapped = mapper.apply(otherV);
            vertices.add(mapped);
            mapRecord.put(otherV, mapped);
        });
        other_graph.edges_map.forEach(((otherV, edges) ->
                edges_map.put(
                        mapRecord.get(otherV),
                        edges.parallelStream().map(edge ->
                                new GraphEdge<>(
                                        mapRecord.get(edge.formerVertex()),
                                        mapRecord.get(edge.laterVertex()),
                                        edge.weight(),
                                        edge.directed()))
                                .collect(Collectors.toList()))));
    }

    public void setNeighbor(@NotNull V vertex, @NotNull V neighbor){
        setNeighbor(vertex, neighbor, 1);
    }

    public void setNeighbor(@NotNull V vertex, @NotNull V neighbor, double w){
        var edge_t = new GraphEdge<>(vertex, neighbor, w, directed);
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
        size++;
        vertices.add(vertex);
        edges_map.put(vertex, new ArrayList<>());
    }

    public int getVerticesCount(){
        return size;
    }

    public @NotNull List<GraphEdge<V>> getAllEdges(){
        List<GraphEdge<V>> res = new ArrayList<>();
        for(var vertex : vertices){
            res.addAll(edges_map.get(vertex));
        }
        return res;
    }

    /**
     * @return unmodifiable list
     */
    public @NotNull List<V> getAllVertices(){
        return Collections.unmodifiableList(vertices);
    }

    /**
     * @param vertex vertex
     * @return unmodifiable list
     */
    public @NotNull List<GraphEdge<V>> getEdgesAt(V vertex){
        return Collections.unmodifiableList(edges_map.get(vertex));
    }

}
