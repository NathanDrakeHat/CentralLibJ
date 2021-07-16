package org.nathan.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.algsJ.graph.*;
import org.nathan.algsJ.misc.Shuffle;
import org.nathan.centralUtils.tuples.Triad;
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
   * iterative deepen DFS
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

    List<Integer> list = new ArrayList<>(2);
    list.add(1);
    list.add(2);

    var funcSolve = new Object(){
      boolean apply(int depth, int limit){
        if(depth > limit){
          return false;
        }

        Set<Integer> sum = new HashSet<>((int) Math.pow(2, list.size()));
        for(int i = list.size() - 1; i >= 0; i--){
          for(int j = i; j >= 0; j--){
            var t = list.get(i) + list.get(j);
            if(t == n){
              list.add(t);
              return true;
            }
            else{
              if(!sum.contains(t)){
                sum.add(t);
                list.add(t);
                var res = apply(depth + 1, limit);
                if(res){
                  return true;
                }
                else{
                  list.remove(list.size() - 1);
                }
              }
            }
          }
        }
        return false;
      }
    };

    for(int i = 3; i < 20; i++){
      if(funcSolve.apply(3, i)){
        return list;
      }
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

  /**
   * POJ2449
   * <br/> A* algorithm
   *
   * @param graph       graph
   * @param source      source
   * @param destination destination
   * @param <ID>        id
   * @param <V>         vertex
   * @param <E>         edge
   * @return k th minimum path
   */
  public static <ID, V extends BFS.Vert<ID>, E extends WeightEdge<V>>
  double kthMinPath(@NotNull LinkedGraph<V, E> graph, LinkedGraph<V, E> reversed, V source, V destination, int K){
    if(!SSSPath.BellmanFord(reversed, destination)){
      throw new IllegalArgumentException("negative cyclic graph.");
    }
    var vs = reversed.allVertices();
    Map<V, Double> fx = new HashMap<>(vs.size());
    Map<V, Integer> retrieve = new HashMap<>(vs.size());
    for(var v : vs){
      fx.put(v, v.getDistance());
      retrieve.put(v, 0);
    }
    PriorityQueue<Triad<V, Double, Double>> priorityQueue =
            new PriorityQueue<>(Comparator.comparing(t -> t.second() + t.third()));
    priorityQueue.add(new Triad<>(source, 0., fx.get(source)));
    while(true) {
      var triad = priorityQueue.poll();
      var x = triad.first();
      var dist = triad.second();
      var retrieveX = retrieve.get(x) + 1;
      retrieve.put(x, retrieveX);
      if(x == destination && retrieveX == K){
        return dist;
      }
      var edges = graph.adjacentEdgesOf(x);
      for(var e : edges){
        var y = e.to();
        if(retrieve.get(y) < K){
          priorityQueue.add(new Triad<>(y, dist + e.weight(), fx.get(y)));
        }
      }
    }
  }

  static class NPuzzle{
    private final String[] data;
    private int sr = 2, sc = 2;

    public NPuzzle(){
      data = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "_"};
    }

    public NPuzzle(@NotNull String[][] n){
      data = new String[9];
      if(n.length != 3){
        throw new IllegalArgumentException();
      }
      for(int i = 0; i < 3; i++){
        if(n[i].length != 3){
          throw new IllegalArgumentException();
        }
        System.arraycopy(n[i], 0, this.data, i * 3, 3);
      }
    }

    public String get(int r, int c){
      if(!(r >= 0 && r < 3) || !(c >= 0 && c < 3)){
        throw new ArrayIndexOutOfBoundsException();
      }
      return data[r * 3 + c];
    }

    private void set(int r, int c, String s){
      if(!(r >= 0 && r < 3) || !(c >= 0 && c < 3)){
        throw new ArrayIndexOutOfBoundsException();
      }
      data[r * 3 + c] = s;
    }

    public void exchange(int r1, int c1, int r2, int c2){
      if(!(r1 >= 0 && r1 < 3) || !(c1 >= 0 && c1 < 3) || !(r2 >= 0 && r2 < 3) || !(c2 >= 0 && c2 < 3)){
        throw new ArrayIndexOutOfBoundsException();
      }
      if(r1 == r2){
        if(!(Math.abs(c1 - c2) == 1)){
          throw new IllegalArgumentException("not adjacent");
        }
      }
      else if(c1 == c2){
        if(!(Math.abs(r1 - r2) == 1)){
          throw new IllegalArgumentException("not adjacent");
        }
      }
      else{
        throw new IllegalArgumentException("not adjacent");
      }
      if(!get(r1, c1).equals("_") && !get(r2, c2).equals("_")){
        throw new IllegalArgumentException("not space to exchange");
      }
      var t = get(r1, c1);
      set(r1, c1, get(r2, c2));
      set(r2, c2, t);
    }

    public void shuffle(int times){
      for(int i = 0; i < times; i++){
        var ns = neighbors(sr, sc);
        Shuffle.KnuthShuffle(ns);
        var first = ns[0];
        exchange(sr, sc, first[0], first[1]);
        sr = first[0];
        sc = first[1];
      }
    }

    private static int[][] neighbors(int r, int c){
      switch(r){
        case 0 -> {
          switch(c){
            case 0 -> {
              return new int[][]{new int[]{0, 1}, new int[]{1, 0}};
            }
            case 1 -> {
              return new int[][]{new int[]{0, 0}, new int[]{0, 2}, new int[]{1, 1}};
            }
            case 2 -> {
              return new int[][]{new int[]{0, 1}, new int[]{1, 2}};
            }
          }
        }
        case 1 -> {
          switch(c){
            case 0 -> {
              return new int[][]{new int[]{0, 0}, new int[]{1, 1}, new int[]{2, 0}};
            }
            case 1 -> {
              return new int[][]{new int[]{0, 1}, new int[]{1, 0}, new int[]{1, 2}, new int[]{2, 1}};
            }
            case 2 -> {
              return new int[][]{new int[]{0, 2}, new int[]{1, 1}, new int[]{2, 2}};
            }
          }
        }
        case 2 -> {
          switch(c){
            case 0 -> {
              return new int[][]{new int[]{1, 0}, new int[]{2, 1}};
            }
            case 1 -> {
              return new int[][]{new int[]{2, 0}, new int[]{1, 1}, new int[]{2, 2}};
            }
            case 2 -> {
              return new int[][]{new int[]{2, 1}, new int[]{1, 2}};
            }
          }
        }
      }
      throw new ArrayIndexOutOfBoundsException();
    }

    public void reset(){
      for(int i = 0; i < 8; i++){
        data[i] = String.valueOf(i);
      }
      data[8] = "_";
    }

    public boolean solved(){
      for(int i = 0; i < 8; i++){
        if(!(data[i].equals(String.valueOf(i)))){
          return false;
        }
      }
      return data[8].equals("_");
    }

    @Override
    public String toString(){
      var b = new StringBuilder();
      for(int i = 0; i < 9; i++){
        b.append(data[i]);
        if(i + 1 % 3 == 0){
          b.append("\n");
        }
      }
      b.delete(b.length() - 1, b.length());
      return b.toString();
    }
  }

  /**
   * POJ1077
   * <br/> n-puzzle game
   *
   * @param nPuzzle eight
   * @return min answer
   */
  public static @NotNull Deque<NPuzzle> eight(@NotNull NPuzzle nPuzzle){
    return null;
  }
}
