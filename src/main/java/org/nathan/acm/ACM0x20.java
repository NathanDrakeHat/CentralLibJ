package org.nathan.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.algsJ.graph.BaseEdge;
import org.nathan.algsJ.graph.BaseVert;
import org.nathan.algsJ.graph.LinkedGraph;
import org.nathan.centralUtils.utils.NumericUtils;

import java.util.*;

public class ACM0x20{
  public static <ID, Vert extends BaseVert<ID>, Edge extends BaseEdge<Vert>>
  @NotNull List<Vert> topologicalSort(@NotNull LinkedGraph<Vert, Edge> graph){
    if(!graph.isDirected()){
      throw new IllegalArgumentException();
    }
    var edges = graph.getAllEdges();
    Map<Vert, Integer> inCount = new HashMap<>(graph.verticesCount());
    for(var edge : edges){
      inCount.put(edge.to(), inCount.getOrDefault(edge.to(), 0) + 1);
    }
    Queue<Vert> queue = new ArrayDeque<>(graph.verticesCount());
    var vs = graph.allVertices();
    for(var v : vs){
      if(!inCount.containsKey(v)){
        queue.add(v);
      }
    }

    List<Vert> ans = new ArrayList<>(graph.verticesCount());
    while(!queue.isEmpty()) {
      var head = queue.poll();
      ans.add(head);
      var adjEdgesOfHead = graph.adjacentEdgesOf(head);
      for(var edge : adjEdgesOfHead){
        var count = inCount.get(edge.to());
        count--;
        inCount.put(edge.to(), count);
        if(count == 0){
          queue.add(edge.to());
        }
      }
    }

    return ans;
  }

  public static <ID, V extends BaseVert<ID>, E extends BaseEdge<V>>
  @NotNull Map<V, Integer> reachabilityCount(@NotNull LinkedGraph<V, E> graph){
    if(!graph.isDirected()){
      throw new IllegalArgumentException();
    }
    var sort_vs = topologicalSort(graph);
    if(sort_vs.size() != graph.verticesCount()){
      throw new RuntimeException("cyclic graph error.");
    }
    Map<V, BitSet> reach = new HashMap<>(sort_vs.size());
    var vs = graph.allVertices();
    for(int i = 0; i < vs.size(); i++){
      var v = vs.get(i);
      var b = new BitSet(vs.size());
      b.set(i, true);
      reach.put(v, b);
    }
    for(int i = sort_vs.size() - 1; i >= 0; i--){
      var v = sort_vs.get(i);
      var b = reach.get(v);
      for(var e : graph.adjacentEdgesOf(v)){
        var t_b = reach.get(e.to());
        b.or(t_b);
      }
    }

    Map<V, Integer> ans = new HashMap<>(sort_vs.size());
    for(var kv : reach.entrySet()){
      ans.put(kv.getKey(), kv.getValue().cardinality());
    }

    return ans;
  }

  /**
   * <br/>POJ2248
   *
   * @param n limit
   * @return array
   */
  public static List<Integer> additionChains(int n){
    if(n < 2){
      throw new IllegalArgumentException();
    }
    else if(n == 2){
      return List.of(1, 2);
    }
    else if(n >= 100){
      throw new IllegalArgumentException();
    }

    List<Integer> list = List.of(1, 2);
    List<List<Integer>> layer = new ArrayList<>(1);
    layer.add(list);

    while(layer.size() > 0) {
      var size = layer.get(0).size();
      List<List<Integer>> nextLayer = new ArrayList<>((size + (size - 1) * size / 2) * layer.size());
      for(var l : layer){
        Set<Integer> next_item = new HashSet<>();
        for(int i = 0; i < l.size() - 1; i++){
          var i1 = l.get(i);
          var i2 = l.get(i + 1);
          int sum1 = i1 * 2, sum2 = i1 + i2;
          if(!next_item.contains(sum1)){
            var nl = new ArrayList<>(l);
            nl.add(sum1);
            if(sum1 == n){
              return nl;
            }
            else if(sum1 > n){
              break;
            }
            nextLayer.add(nl);
            next_item.add(sum1);
          }
          if(!next_item.contains(sum2)){
            var nl = new ArrayList<>(l);
            nl.add(sum2);
            if(sum2 == n){
              return nl;
            }
            else if(sum2 > n){
              break;
            }
            nextLayer.add(nl);
            next_item.add(sum2);
          }
        }
        {
          int i = l.size() - 1;
          var i1 = l.get(i);
          int sum1 = i1 * 2;
          if(!next_item.contains(sum1)){
            var nl = new ArrayList<>(l);
            nl.add(sum1);
            if(sum1 == n){
              return nl;
            }
            else if(sum1 < n){
              nextLayer.add(nl);
              next_item.add(sum1);
            }
          }
        }
      }
      layer = nextLayer;
    }

    throw new RuntimeException();
  }

  /**
   * bi-direction BFS
   * <br/> (TYVJ1340)
   *
   * @param gift weights of gift
   * @return max number of carried gift
   */
  public static int sendGift(int[] gift){
    int[] weights = new int[gift.length];
    System.arraycopy(gift, 0, weights, 0, weights.length);
    weights = Arrays.stream(weights).boxed().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).toArray();
    int len = weights.length;
    Set<Integer> half = new HashSet<>(1);
    half.add(0);
    for(int i = 0; i < len / 2; i++){
      Set<Integer> next = new HashSet<>(half.size() * 2);
      next.addAll(half);
      for(var t : half){
        if(NumericUtils.addNotOverflow(t, weights[i])){
          next.add(t + weights[i]);
        }
      }
      half = next;
    }

    TreeSet<Integer> tree = new TreeSet<>(half);

    int max = 0;
    half = new HashSet<>(1);
    half.add(0);
    for(int i = len / 2; i < len; i++){
      Set<Integer> next = new HashSet<>(half.size() * 2);
      next.addAll(half);
      for(var t : half){
        if(NumericUtils.addNotOverflow(t, weights[i])){
          next.add(t + weights[i]);
        }
      }
      half = next;
    }
    for(var i : half){
      var f = tree.floor(Integer.MAX_VALUE - i);
      max = Math.max(max, i + f);
    }

    return max;
  }


}
