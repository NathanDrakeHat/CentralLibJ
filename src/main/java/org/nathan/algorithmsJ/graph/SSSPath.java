package org.nathan.algorithmsJ.graph;


import org.jetbrains.annotations.NotNull;
import org.nathan.algorithmsJ.structures.FibonacciHeap;
import org.nathan.algorithmsJ.structures.ExtremumHeap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.nathan.algorithmsJ.graph.DFS.topologicalSort;

/**
 * single source shortest path
 */
public final class SSSPath{
  /**
   * general case algorithm: negative weight, cyclic
   *
   * @param graph graph
   * @param s     start
   * @param <T>   id
   * @return has shortest path
   */
  public static <T> boolean BellmanFord(@NotNull LinkGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> graph, @NotNull BFS.Vert<T> s){
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

  private static <T> void initializeSingleSource(LinkGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> G, BFS.Vert<T> s){
    var vertices = G.allVertices();
    for(var v : vertices){
      v.distance = Double.POSITIVE_INFINITY;
      v.parent = null;
      if(s == v){
        s.distance = 0;
      }
    }
  }

  private static <T> void relax(WeightEdge<BFS.Vert<T>> edge){
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
   *
   * @param <T>              id
   * @param BFS_Linked_graph linked graph with bfs vertex wrapper
   * @param s                start
   */
  public static <T> void ssDAG(@NotNull LinkGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> BFS_Linked_graph, @NotNull BFS.Vert<T> s){
    var DFS_Linked_graph = transform(BFS_Linked_graph);
    var DFS_list = topologicalSort(DFS_Linked_graph);
    initializeSingleSource(BFS_Linked_graph, s);
    DFS_list.sort((d1, d2) -> d2.finish - d1.finish);
    var BFS_list = DFS_list.stream().map(DFS.Vert::getId).collect(Collectors.toList());
    for(var u : BFS_list){
      var u_edges = BFS_Linked_graph.edgesAt(u);
      for(var edge : u_edges){
        relax(edge);
      }
    }
  }

  private static <T> LinkGraph<DFS.Vert<BFS.Vert<T>>, BaseEdge<DFS.Vert<BFS.Vert<T>>>> transform(
          LinkGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> other_graph){
    LinkGraph<DFS.Vert<BFS.Vert<T>>, BaseEdge<DFS.Vert<BFS.Vert<T>>>> res = new LinkGraph<>(other_graph.verticesCount(), other_graph.directed);
    Map<BFS.Vert<T>, DFS.Vert<BFS.Vert<T>>> mapRecord = new HashMap<>(res.verticesCount());
    other_graph.allVertices().forEach(otherV -> {
      var mapped = new DFS.Vert<>(otherV);
      res.vertices.add(mapped);
      mapRecord.put(otherV, mapped);
    });
    other_graph.edges_map.forEach(((otherV, edges) ->
            res.edges_map.put(
                    mapRecord.get(otherV),
                    edges.parallelStream().map(edge ->
                            new BaseEdge<>(
                                    mapRecord.get(edge.from()),
                                    mapRecord.get(edge.to())))
                            .collect(Collectors.toList()))));

    return res;
  }


  /**
   * fibonacci heap, time complexity: O(V^2*lgV + V*E)
   *
   * @param G   graph
   * @param s   start
   * @param <T> id
   */
  public static <T> void DijkstraFibonacciHeap(@NotNull LinkGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> G, @NotNull BFS.Vert<T> s){
    initializeSingleSource(G, s);
    var vertices = G.allVertices();
    FibonacciHeap<Double, BFS.Vert<T>> Q = new FibonacciHeap<>(Comparator.comparingDouble(a -> a));
    for(var vertex : vertices){
      Q.insert(vertex.distance, vertex);
    }
    while(Q.count() > 0) {
      var u = Q.extractMin();
      var u_edges = G.edgesAt(u);
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
   *
   * @param G   graph
   * @param s   start
   * @param <T> id
   */
  public static <T> void DijkstraMinHeap(@NotNull LinkGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> G, @NotNull BFS.Vert<T> s){
    initializeSingleSource(G, s);
    var vertices = G.allVertices();
    ExtremumHeap<Double, BFS.Vert<T>> Q = new ExtremumHeap<>(true,vertices, BFS.Vert::getDistance, Double::compare);
    while(Q.length() > 0) {
      var u = Q.extractExtremum();
      var u_edges = G.edgesAt(u);
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
