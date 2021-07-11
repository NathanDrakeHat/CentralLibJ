package org.nathan.algsJ.graph;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MaxFlow{

  public static class ForFulkersonSolver<ID, V extends BaseVert<ID>, E extends FlowEdge<V>>{
    private double maxFlow;
    private final Map<V, Boolean> marked;
    private final Map<V, E> edgeOf;
    private final LinkedGraph<V, E> graph;
    private V s;
    private V t;
    private final Deque<V> queue = new ArrayDeque<>();


    public ForFulkersonSolver(@NotNull LinkedGraph<V, E> graph){
      if(graph.directed){
        throw new IllegalArgumentException();
      }
      this.graph = graph;
      var vertices = graph.allVertices();
      marked = new HashMap<>(vertices.size());
      edgeOf = new HashMap<>(vertices.size());
      for(var v : vertices){
        Objects.requireNonNull(v);
      }
    }

    public void solve(@NotNull V source, @NotNull V destination){
      s = source;
      t = destination;
      double flow = Double.POSITIVE_INFINITY;
      while(hasAugPath()) {
        // backward through augment path
        for(var v = t;
            v != s;
            v = edgeOf.get(v).another(v)){
          flow = Math.min(flow, edgeOf.get(v).residualCapacityTo(v));
        }

        for(var v = t;
            v != s;
            v = edgeOf.get(v).another(v)){
          edgeOf.get(v).addFlowTo(v, flow);
        }

        maxFlow += flow;
      }
    }

    private boolean hasAugPath(){
      marked.clear();
      edgeOf.clear();

      queue.addLast(s);
      marked.put(s, true);
      while(!queue.isEmpty()) {
        var vert = queue.removeFirst();
        var adjEdges = new ArrayList<>(graph.adjacentEdgesOf(vert));
        adjEdges.sort(Comparator.comparing(e -> e.residualCapacityTo(e.another(vert))));
        for(var edge : adjEdges){
          var w = edge.another(vert);
          if(edge.residualCapacityTo(w) > 0 && !marked.getOrDefault(w, false)){
            edgeOf.put(w, edge);
            marked.put(w, true);
            queue.addLast(w);
          }
        }
      }

      return marked.getOrDefault(t, false);
    }

    public double maxFlow(){
      return maxFlow;
    }

    /**
     * in the same cut of source
     *
     * @param vert vert
     * @return in the source cut
     */
    public boolean inMinCut(@NotNull V vert){
      if(!marked.containsKey(vert)){
        return false;
      }
      return marked.get(vert);
    }
  }
}
