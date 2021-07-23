package org.nathan.centralibj.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralibj.algsJ.dataStruc.SharedList;
import org.nathan.centralibj.algsJ.graph.*;
import org.nathan.centralibj.algsJ.numeric.NumberTheory;
import org.nathan.centralibj.utils.tuples.Quaternion;
import org.nathan.centralibj.utils.tuples.Triad;
import org.nathan.centralibj.utils.tuples.Tuple;

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
        if(NumberTheory.addNotOverflow(t, weights[i])){
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
        if(NumberTheory.addNotOverflow(t, weights[i])){
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
   * A* algorithm
   * <br/> POJ2449
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

    public NPuzzle(int shuffle){
      data = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "_"};
      shuffle(shuffle);
    }

    public NPuzzle(NPuzzle nPuzzle){
      data = new String[9];
      System.arraycopy(nPuzzle.data, 0, this.data, 0, this.data.length);
      sr = nPuzzle.sr;
      sc = nPuzzle.sc;
    }

    public Tuple<Integer, Integer> getSpaceIndex(){
      return new Tuple<>(sr, sc);
    }

    public String get(int r, int c){
      if(!(r >= 0 && r < 3) || !(c >= 0 && c < 3)){
        throw new ArrayIndexOutOfBoundsException();
      }
      return data[r * 3 + c];
    }

    private void set(int r, int c, String s){
      data[r * 3 + c] = s;
    }

    public void exchangeWith(int r, int c){
      if(!(r >= 0 && r < 3) || !(c >= 0 && c < 3)){
        throw new ArrayIndexOutOfBoundsException();
      }
      if(sr == r){
        if(!(Math.abs(sc - c) == 1)){
          throw new IllegalArgumentException("not adjacent");
        }
      }
      else if(sc == c){
        if(!(Math.abs(sr - r) == 1)){
          throw new IllegalArgumentException("not adjacent");
        }
      }
      else{
        throw new IllegalArgumentException("not adjacent");
      }
      var t = get(r, c);
      set(r, c, get(sr, sc));
      set(sr, sc, t);
      sr = r;
      sc = c;
    }

    private void shuffle(int times){
      for(int i = 0; i < times; i++){
        var neighbors = neighbors();
        Collections.shuffle(neighbors);
        var first = neighbors.get(0);
        exchangeWith(first.first(), first.second());
        sr = first.first();
        sc = first.second();
      }
    }

    public List<Tuple<Integer, Integer>> neighbors(){
      switch(sr){
        case 0 -> {
          switch(sc){
            case 0 -> {
              return new ArrayList<>(List.of(new Tuple<>(0, 1), new Tuple<>(1, 0)));
            }
            case 1 -> {
              return new ArrayList<>(List.of(new Tuple<>(0, 0), new Tuple<>(0, 2), new Tuple<>(1, 1)));
            }
            case 2 -> {
              return new ArrayList<>(List.of(new Tuple<>(0, 1), new Tuple<>(1, 2)));
            }
          }
        }
        case 1 -> {
          switch(sc){
            case 0 -> {
              return new ArrayList<>(List.of(new Tuple<>(0, 0), new Tuple<>(1, 1), new Tuple<>(2, 0)));
            }
            case 1 -> {
              return new ArrayList<>(List.of(new Tuple<>(0, 1), new Tuple<>(1, 0), new Tuple<>(1, 2), new Tuple<>(2,
                      1)));
            }
            case 2 -> {
              return new ArrayList<>(List.of(new Tuple<>(0, 2), new Tuple<>(1, 1), new Tuple<>(2, 2)));
            }
          }
        }
        case 2 -> {
          switch(sc){
            case 0 -> {
              return new ArrayList<>(List.of(new Tuple<>(1, 0), new Tuple<>(2, 1)));
            }
            case 1 -> {
              return new ArrayList<>(List.of(new Tuple<>(2, 0), new Tuple<>(1, 1), new Tuple<>(2, 2)));
            }
            case 2 -> {
              return new ArrayList<>(List.of(new Tuple<>(2, 1), new Tuple<>(1, 2)));
            }
          }
        }
      }
      throw new ArrayIndexOutOfBoundsException();
    }

    public boolean solved(){
      for(int i = 0; i < 8; i++){
        if(!(data[i].equals(String.valueOf(i + 1)))){
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
        b.append(" ");
        if((i + 1) % 3 == 0){
          b.append("\n");
        }
      }
      b.delete(b.length() - 1, b.length());
      return b.toString();
    }
  }

  /**
   * A* algorithm
   * <br/> n-puzzle game
   * <br/> POJ1077
   *
   * @param nPuzzle eight
   * @return min answer
   */
  public static @NotNull NPuzzle[] eight(@NotNull NPuzzle nPuzzle){
    // step, estimate, n-puzzle, last space
    PriorityQueue<Quaternion<Integer, Integer, NPuzzle, SharedList<Tuple<Integer, Integer>>>> queue =
            new PriorityQueue<>(Comparator.comparing(t -> t.first() + t.second()));
    queue.add(new Quaternion<>(0, estimate(nPuzzle), nPuzzle, new SharedList<>(nPuzzle.getSpaceIndex())));
    while(queue.size() > 0) {
      var quaternion = queue.poll();
      var np = quaternion.third();
      if(np.solved()){
        var history = quaternion.fourth().toDeque();
        history.removeFirst();
        var ans = new NPuzzle[history.size() + 1];
        ans[ans.length - 1] = new NPuzzle(np);
        int i = ans.length - 2;
        while(history.size() > 0) {
          var s = history.removeLast();
          np.exchangeWith(s.first(), s.second());
          ans[i--] = new NPuzzle(np);
        }
        return ans;
      }

      var neighbors = np.neighbors();
      var step = quaternion.first();

      for(int i = 0; i < neighbors.size() - 1; i++){
        var nb = neighbors.get(i);
        if(!nb.equals(quaternion.fourth().Data)){
          var nnp = new NPuzzle(np);
          var currentSpace = nnp.getSpaceIndex();
          var sl = new SharedList<>(currentSpace);
          sl.setParent(quaternion.fourth());
          nnp.exchangeWith(nb.first(), nb.second());
          queue.add(new Quaternion<>(step + 1, estimate(nnp), nnp, sl));
        }
      }
      {
        var nb = neighbors.get(neighbors.size() - 1);
        if(!nb.equals(quaternion.fourth().Data)){
          var currentSpace = np.getSpaceIndex();
          var sl = new SharedList<>(currentSpace);
          sl.setParent(quaternion.fourth());
          np.exchangeWith(nb.first(), nb.second());
          queue.add(new Quaternion<>(step + 1, estimate(np), np, sl));
        }
      }
    }
    throw new RuntimeException("impossible error.");
  }

  private static int estimate(NPuzzle nPuzzle){
    int ans = 0;
    for(int i = 0; i < 3; i++){
      for(int j = 0; j < 3; j++){
        var s = nPuzzle.get(i, j);
        if(!s.equals("_")){
          var num = Integer.parseInt(s);
          var r = num / 3;
          var c = num % 3;
          ans += Math.abs(i - r) + Math.abs(j - c);
        }
        else{
          ans += Math.abs(i - 2) + Math.abs(j - 2);
        }
      }
    }
    return ans;
  }

  /**
   * IDA* algorithm
   * <br/>POJ3460
   *
   * @param books books
   * @return list of (old start, len ,new start)
   */
  public static List<Triad<Integer, Integer, Integer>> bookSort(int[] books){
    List<Triad<Integer, Integer, Integer>> ans = new ArrayList<>();
    var funcSolve = new Object(){
      boolean apply(int time, int limit, Deque<Triad<Integer, Integer, Integer>> exchanges){
        var estimate = estimateMoveOfBooks(books);
        if(estimate == 0){
          ans.addAll(exchanges);
          return true;
        }
        else if(time + estimate > limit){
          return false;
        }

        for(int len = 1; len < books.length; len++){
          for(int s1 = 0; s1 + len < books.length; s1++){
            for(int s2 = s1 + 1; s2 + len <= books.length; s2++){
              exchanges.addLast(new Triad<>(s1, len, s2));
              backwardsExchangeBooks(books, s1, len, s2);
              if(apply(time + 1, limit, exchanges)){
                return true;
              }
              backwardsExchangeBooks(books, s1, s2 - s1, len + s1);
              exchanges.removeLast();
            }
          }
        }
        return false;
      }
    };
    Deque<Triad<Integer, Integer, Integer>> deque = new ArrayDeque<>();
    for(int i = 1; i <= 4; i++){
      if(funcSolve.apply(0, i, deque)){
        break;
      }
    }
    return ans;
  }

  private static int estimateMoveOfBooks(int[] books){
    int ans = 0;
    for(int i = 0; i < books.length - 1; i++){
      var a = books[i];
      var b = books[i + 1];
      if(b - a != 1){
        ans++;
      }
    }
    return (int) Math.ceil(ans / 3.);
  }

  static void backwardsExchangeBooks(int[] books, int s1, int len, int s2){
    int[] temp = new int[len];
    System.arraycopy(books, s1, temp, 0, len);
    int es = s1 + len;
    int e2 = s2 + len;
    System.arraycopy(books, es, books, s1, e2 - es);
    System.arraycopy(temp, 0, books, s2, len);
  }
}
