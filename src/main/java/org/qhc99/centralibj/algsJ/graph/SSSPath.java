package org.qhc99.centralibj.algsJ.graph;


import org.jetbrains.annotations.NotNull;
import org.qhc99.centralibj.algsJ.dataStruc.DynamicPriQueue;
import org.qhc99.centralibj.algsJ.dataStruc.FibonacciMinHeap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.qhc99.centralibj.algsJ.graph.DFS.topologicalSort;

/**
 * single source shortest path
 */
public final class SSSPath{
  /**
   * general case algorithm: negative weight, cyclic
   *
   * @param graph graph
   * @param s     start
   * @param <ID>   id
   * @return has shortest path
   */
  public static <ID, V extends BFS.Vert<ID>, E extends WeightEdge<V>>
  boolean BellmanFord(@NotNull LinkedGraph<V, E> graph, @NotNull V s){
    if(!graph.directed){
      throw new IllegalArgumentException();
    }
    initializeSingleSource(graph, s);
    int vertices_count = graph.verticesCount();
    var edges = graph.getAllEdges();
    for(int i = 1; i < vertices_count; i++){
      for(var edge : edges){
        relax(edge);
      }
    }
    for(var edge : edges){
      if(edge.to().distance > edge.from().distance + edge.weight()){
        return false;
      }
    }
    return true;
  }

  private static <ID, V extends BFS.Vert<ID>, E extends WeightEdge<V>>
  void initializeSingleSource(LinkedGraph<V, E> G, V s){
    var vertices = G.allVertices();
    for(var v : vertices){
      v.distance = Double.POSITIVE_INFINITY;
      v.parent = null;
      if(s == v){
        s.distance = 0;
      }
    }
  }

  private static <ID, V extends BFS.Vert<ID>, E extends WeightEdge<V>>
  void relax(E edge){
    var weight = edge.weight();
    var u = edge.from();
    var v = edge.to();
    var sum = u.distance + weight;
    if(v.distance > sum){
      v.distance = sum;
      v.parent = u;
    }
  }

  /**
   * shortest paths of directed acyclic graph
   * @param BFS_Linked_graph bfs graph
   * @param s source
   * @param <ID> Id
   * @param <V> vertex
   * @param <E> edge
   */
  public static <ID, V extends BFS.Vert<ID>, E extends WeightEdge<V>>
  void ssDAG(@NotNull LinkedGraph<V, E> BFS_Linked_graph, @NotNull V s){
    if(!BFS_Linked_graph.directed){
      throw new IllegalArgumentException();
    }
    var DFS_Linked_graph = transform(BFS_Linked_graph);
    var DFS_list = topologicalSort(DFS_Linked_graph);
    initializeSingleSource(BFS_Linked_graph, s);
    DFS_list.sort((d1, d2) -> d2.finish - d1.finish);
    var BFS_list = DFS_list.stream().map(DFS.Vert::identity).toList();
    for(var u : BFS_list){
      var u_edges = BFS_Linked_graph.adjacentEdgesOf(u);
      for(var edge : u_edges){
        relax(edge);
      }
    }
  }

  private static <ID, V extends BFS.Vert<ID>, E extends WeightEdge<V>>
  LinkedGraph<DFS.Vert<V>, BaseEdge<DFS.Vert<V>>> transform(LinkedGraph<V,E> other_graph){
    LinkedGraph<DFS.Vert<V>, BaseEdge<DFS.Vert<V>>> res =
            new LinkedGraph<>(other_graph.verticesCount(), other_graph.directed);
    Map<V, DFS.Vert<V>> mapRecord = new HashMap<>(res.verticesCount());
    other_graph.allVertices().forEach(otherV -> {
      var mapped = new DFS.Vert<>(otherV);
      res.vertices.add(mapped);
      mapRecord.put(otherV, mapped);
    });
    other_graph.edges_map.forEach(((otherV, edges) ->
            res.edges_map.put(
                    mapRecord.get(otherV),
                    edges.stream().map(edge ->
                            new BaseEdge<>(
                                    mapRecord.get(edge.from()),
                                    mapRecord.get(edge.to())))
                            .collect(Collectors.toList()))));

    return res;
  }


  /**
   * fibonacci heap, time complexity: O(V^2*lgV + V*E)
   * @param G graph
   * @param s source
   * @param <ID> id
   * @param <V> vertex
   * @param <E> edge
   */
  public static <ID, V extends BFS.Vert<ID>, E extends WeightEdge<V>>
  void DijkstraFibonacciHeap(@NotNull LinkedGraph<V, E> G, @NotNull V s){
    if(!G.directed){
      throw new IllegalArgumentException();
    }
    initializeSingleSource(G, s);
    var vertices = G.allVertices();
    FibonacciMinHeap<Double, V> Q = FibonacciMinHeap.ofDouble();
    for(var vertex : vertices){
      Q.insert(vertex.distance, vertex);
    }
    while(Q.count() > 0) {
      var u = Q.extractMin();
      var u_edges = G.adjacentEdgesOf(u);
      for(var edge : u_edges){
        var v = edge.another(u);
        var original = v.distance;
        relax(edge);
        if(v.distance < original){
          Q.decreaseKey(v, v.distance);
        }
      }
    }
  }

  /**
   * min heap, time complexity: O(V*E*lgV)
   * @param G graph
   * @param s source
   * @param <ID> id
   * @param <V> vertex
   * @param <E> edge
   */
  public static<ID, V extends BFS.Vert<ID>, E extends WeightEdge<V>>
  void DijkstraMinHeap(@NotNull LinkedGraph<V, E> G, @NotNull V s){
    if(!G.directed){
      throw new IllegalArgumentException();
    }
    initializeSingleSource(G, s);
    var vertices = G.allVertices();
    DynamicPriQueue<Double, V> Q = new DynamicPriQueue<>(vertices, BFS.Vert::getDistance, Double::compare);
    while(Q.length() > 0) {
      var u = Q.extractExtremum().second();
      var u_edges = G.adjacentEdgesOf(u);
      for(var edge : u_edges){
        var v = edge.another(u);
        var original = v.distance;
        relax(edge);
        if(v.distance < original){
          Q.updateKey(v, v.distance);
        }
      }
    }
  }

}
