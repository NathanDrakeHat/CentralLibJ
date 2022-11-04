package dev.qhc99.centralibj.algsJ.graph;




import java.util.*;
import java.util.function.BiConsumer;

/**
 * all pair shortest path
 */
public class APSPath{

  /**
   * O(V^4)
   *
   * @param W weights
   * @return all shortest path
   */
  public static double[][] slowAllPairsShortestPaths(double[][] W){
    var n = W.length;
    var L = W;
    for(int m = 2; m <= n - 1; m++){
      L = extendedShortestPath(L, W);
    }
    // L^(n-1)
    return L;
  }

  private static double[][] extendedShortestPath(double[][] L_origin, double[][] W){
    var n = W.length;
    var L_next = new double[n][n];
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        L_next[i][j] = Double.POSITIVE_INFINITY;
        for(int k = 0; k < n; k++){
          L_next[i][j] = Math.min(L_next[i][j], L_origin[i][k] + W[k][j]);
        }
      }
    }
    return L_next;
  }

  /**
   * O(V^3*lgV)
   *
   * @param W weights
   * @return all shortest path
   */
  public static double[][] fasterAllPairsShortestPaths(double[][] W){
    var n = W.length;
    var L = W;
    int m = 1;
    for(; m < n - 1; m *= 2){
      L = extendedShortestPath(L, L);
    }
    return L;
  }

  /**
   * no negative-weight cycles
   *
   * @param W weights
   * @return all shortest path
   */
  public static double[][] FloydWarshall(double[][] W){
    var n = W.length;
    var D_origin = W;
    for(int k = 0; k < n; k++){
      var D_current = new double[n][n];
      for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
          D_current[i][j] = Math.min(D_origin[i][j], D_origin[i][k] + D_origin[k][j]);
        }
      }
      D_origin = D_current;
    }
    return D_origin;
  }


  public static boolean[][] transitiveClosure(double[][] W){
    var n = W.length;
    var T = new boolean[n][n];
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        T[i][j] = (i == j) || (W[i][j] != Double.POSITIVE_INFINITY);
      }
    }
    for(int k = 0; k < n; k++){
      var T_k = new boolean[n][n];
      for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
          T_k[i][j] = T[i][j] || (T[i][k] && T[k][j]);
        }
      }
      T = T_k;
    }
    return T;
  }


  /**
   * sparse
   * Fibonacci heap: O(V^2*lgV + V*E)
   * min heap: O(V*E*lgV)
   *
   * @param graph        graph
   * @param algoDijkstra function
   * @param <T>          id
   * @return all shortest path
   */
  public static <T>
  Optional<double[][]> Johnson( LinkedGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> graph,
                                BiConsumer<LinkedGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>>, BFS.Vert<T>> algoDijkstra){
    Map<BFS.Vert<T>, Double> h = new HashMap<>();
    var n = graph.verticesCount();
    var vertices_new = new ArrayList<>(graph.allVertices());
    var s = new BFS.Vert<T>();
    vertices_new.add(s);
    var new_graph = buildGraph(graph, vertices_new, s);
    if(!SSSPath.BellmanFord(new_graph, s)){
      return Optional.empty();
    }
    else{
      var edges_new = new_graph.getAllEdges();
      for(var vertex : vertices_new){
        h.put(vertex, vertex.distance);
      }
      for(var edge : edges_new){
        edge.weight = edge.weight + edge.from().distance - edge.to().distance;
      }
      var D = new double[n][n];
      int idx_u = 0;
      for(var u : vertices_new){
        if(u != s){
          int idx_v = 0;
          algoDijkstra.accept(graph, u);
          for(var v : vertices_new){
            if(v != s){
              D[idx_u][idx_v] = v.distance + h.get(v) - h.get(u);
              idx_v++;
            }
          }
          idx_u++;
        }
      }
      return Optional.of(D);
    }
  }

  private static <T> LinkedGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> buildGraph(
           LinkedGraph<BFS.Vert<T>, WeightEdge<BFS.Vert<T>>> graph,
           List<BFS.Vert<T>> vertices,
           BFS.Vert<T> s){
    var new_graph = new LinkedGraph<>(graph);
    new_graph.addVertex(s);
    for(var vertex : vertices){
      if(vertex != s){
        new_graph.addEdge(new WeightEdge<>(s, vertex, 0));
      }
    }
    return new_graph;
  }
}
