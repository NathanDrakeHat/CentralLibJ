package org.nathan.algorithmsJ.graph;


import org.jetbrains.annotations.NotNull;

import java.util.*;


// TODO refactor edges

/**
 * static graph
 *
 * @param <V>
 */
public class LinkGraph<V extends Vertex<?>>{
  final boolean directed;
  final List<V> vertices;
  final Map<V, List<UnionEdge<V>>> edges_map;

  /**
   * common constructor
   *
   * @param size        vertices size
   * @param is_directed bool
   */
  LinkGraph(int size, boolean is_directed){
    directed = is_directed;
    vertices = new ArrayList<>(size);
    edges_map = new HashMap<>(size);
  }

  /**
   * build
   *
   * @param vertices    all vertices
   * @param is_directed bool
   */
  public LinkGraph(@NotNull List<? extends V> vertices, boolean is_directed){
    this(vertices.size(), is_directed);
    for(var vertex : vertices){
      Objects.requireNonNull(vertex);
      this.edges_map.put(vertex, new ArrayList<>());
      this.vertices.add(vertex);
    }
  }


  /**
   * copy
   *
   * @param other_graph other
   */
  public LinkGraph(@NotNull LinkGraph<V> other_graph){
    this(other_graph.verticesCount(), other_graph.directed);
    this.edges_map.putAll(other_graph.edges_map);
    this.vertices.addAll(other_graph.vertices);
  }

  public void setNeighbor(@NotNull V vertex, @NotNull V neighbor){
    setNeighbor(vertex, neighbor, 1);
  }

  public void setNeighbor(@NotNull V vertex, @NotNull V neighbor, double w){
    var edge_t = new UnionEdge<>(vertex, neighbor, w);
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
    if(edges_map.containsKey(vertex)){
      throw new IllegalArgumentException("repeated vertex");
    }
    vertices.add(vertex);
    edges_map.put(vertex, new ArrayList<>());
  }

  public int verticesCount(){
    return vertices.size();
  }

  public @NotNull List<UnionEdge<V>> getAllEdges(){
    List<UnionEdge<V>> res = new ArrayList<>();
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
  public @NotNull List<UnionEdge<V>> edgesAt(V vertex){
    return Collections.unmodifiableList(edges_map.get(vertex));
  }
}
