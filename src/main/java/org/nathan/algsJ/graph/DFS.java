package org.nathan.algsJ.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * depth first search
 */
public final class DFS {
  public static <T> void depthFirstSearch(@NotNull LinkedGraph<Vert<T>, BaseEdge<Vert<T>>> G) {
    var vertices = G.allVertices();
    vertices.parallelStream().forEach(v -> {
      v.color = COLOR.WHITE;
      v.parent = null;
    });
    int time = 0;
    for (var v : vertices) {
      if (v.color == COLOR.WHITE) {
        time = DFSVisit(G, v, time);
      }
    }
  }

  private static <T> int DFSVisit(LinkedGraph<Vert<T>, BaseEdge<Vert<T>>> G, Vert<T> u, int time) {
    time++;
    u.discover = time;
    u.color = COLOR.GRAY;
    var u_edges = G.adjacentEdgesOf(u);
    for (var edge : u_edges) {
      var v = edge.another(u);
      if (v.color == COLOR.WHITE) {
        v.parent = u;
        time = DFSVisit(G, v, time);
      }
    }
    u.color = COLOR.BLACK;
    time++;
    u.finish = time;
    return time;
  }

  public static <T> List<Vert<T>> topologicalSort(@NotNull LinkedGraph<Vert<T>, BaseEdge<Vert<T>>> G) {
    depthFirstSearch(G);
    List<Vert<T>> l = new ArrayList<>(G.allVertices());
    l.sort((o1, o2) -> o2.finish - o1.finish); // descend order
    return l;
  }

  public static <T> void stronglyConnectedComponents(@NotNull LinkedGraph<Vert<T>, BaseEdge<Vert<T>>> G) {
    var l = topologicalSort(G);
    var G_T = transposeGraph(G);
    depthFirstSearchOrderly(G_T, l);
  }

  private static <T> void depthFirstSearchOrderly(LinkedGraph<Vert<T>, BaseEdge<Vert<T>>> G, List<Vert<T>> order) {
    var vertices = G.allVertices();
    for (var v : vertices) {
      v.color = COLOR.WHITE;
      v.parent = null;
    }
    int time = 0;
    for (var v : order) {
      if (v.color == COLOR.WHITE) {
        time = DFSVisit(G, v, time);
      }
    }
  }

  private static <T> LinkedGraph<Vert<T>, BaseEdge<Vert<T>>> transposeGraph(LinkedGraph<Vert<T>, BaseEdge<Vert<T>>> graph) {
    LinkedGraph<Vert<T>, BaseEdge<Vert<T>>> new_graph = new LinkedGraph<>(true, graph.allVertices());
    var vertices = graph.allVertices();
    for (var v : vertices) {
      var edges = graph.adjacentEdgesOf(v);
      for (var edge : edges) {
        var n = edge.another(v);
        new_graph.addEdge(new BaseEdge<>(n, v));
      }
    }
    return new_graph;
  }

  enum COLOR {WHITE, GRAY, BLACK}

  public static class Vert<V> extends BaseVert<V> {
    @Nullable
    DFS.Vert<V> parent;
    int discover; //d
    int finish; // f
    COLOR color;

    Vert(@NotNull V name) {
      super(name);
    }

    public @Nullable DFS.Vert<V> getParent() {
      return parent;
    }

    @Override
    public String toString() {
      return String.format("DFS.Vertex: (%s)", identity);
    }
  }
}