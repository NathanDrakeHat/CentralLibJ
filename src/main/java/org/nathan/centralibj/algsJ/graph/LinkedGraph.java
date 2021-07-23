package org.nathan.centralibj.algsJ.graph;


import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * static object (non-value) graph
 *
 * @param <V>
 */
public class LinkedGraph<V, E extends BaseEdge<V>>{
  final boolean directed;
  final List<V> vertices;
  final Map<V, List<E>> edges_map;

  /**
   * common constructor
   *
   * @param size        vertices size
   * @param is_directed bool
   */
  LinkedGraph(int size, boolean is_directed){
    directed = is_directed;
    vertices = new ArrayList<>(size);
    edges_map = new HashMap<>(size);
  }

  /**
   * build
   *
   * @param is_directed bool
   * @param vertices    all vertices
   */
  public LinkedGraph(boolean is_directed, @NotNull List<? extends V> vertices){
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
  public LinkedGraph(@NotNull LinkedGraph<V, E> other_graph){
    this(other_graph.verticesCount(), other_graph.directed);
    this.edges_map.putAll(other_graph.edges_map);
    this.vertices.addAll(other_graph.vertices);
  }

  public void addEdge(@NotNull E edge){
    if(!edges_map.containsKey(edge.vert_from) || !edges_map.containsKey(edge.vert_to)){
      throw new IllegalArgumentException();
    }
    edges_map.get(edge.vert_from).add(edge);
    if(!directed){
      edges_map.get(edge.vert_to).add(edge);
    }
  }

  public void addEdges(@NotNull List<E> edges){
    for(var edge : edges){
      addEdge(edge);
    }
  }

  public void addVertex(@NotNull V vertex){
    if(edges_map.containsKey(vertex)){
      throw new IllegalArgumentException("repeated vertex");
    }
    vertices.add(vertex);
    edges_map.put(vertex, new ArrayList<>());
  }

  public int verticesCount(){
    return vertices.size();
  }

  public @NotNull List<E> getAllEdges(){
    List<E> res = new ArrayList<>();
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
  public @NotNull List<E> adjacentEdgesOf(V vertex){
    return Collections.unmodifiableList(edges_map.get(vertex));
  }

  public boolean isDirected(){
    return directed;
  }
}
