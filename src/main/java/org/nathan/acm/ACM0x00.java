package org.nathan.acm;


import org.nathan.centralUtils.tuples.Tuple;
import org.nathan.centralUtils.utils.ArrayUtils;
import org.nathan.centralUtils.utils.NumericUtils;

import java.util.*;


/**
 * 《算法竞赛进阶指南》, 李煜东
 */
public class ACM0x00{

  /**
   * a ^ b % m <br/>
   * b = 101... = c_k*2^(k-1) + ... <br/>
   * a^b = a^(2^(k-1)) * 1 * a^(2^(k-3)) * ...<br/>
   *
   * @param a a
   * @param b b >= 0
   * @param m m
   * @return int
   */
  public static int fastPowerMod(int a, int b, int m){
    int ans = 1 % m;
    while(b != 0) {
      if((b & 1) == 1){
        ans = (int) ((long) a * ans % m);
      }
      a = (int) ((long) a * a % m);
      b = b >>> 1;
    }
    return ans;
  }

  /**
   * a * b % m <br/>
   * b = 101... = c_k*2^(k-1) + ... <br/>
   * a * b = 2^(k-1)*a + 0*a + 2^(k-3)*a+ ...
   *
   * @param a a
   * @param b b >= 0
   * @param m m
   * @return int
   */
  public static long longFastMultiplyMod1(long a, long b, long m){
    long ans = 0;
    while(b != 0) {
      if((b & 1) == 1){
        ans = (ans + a) % m;
      }
      a *= 2 % m;
      b = b >>> 1;
    }
    return ans;
  }

  /**
   * a*b%m = a*b - floor(a*b/p)*p
   *
   * @param a a
   * @param b b
   * @param m m
   * @return long
   */
  public static long longFastMultiplyMod2(long a, long b, long m){
    a %= m;
    b %= m;
    long c = (long) ((double) a * b / m);
    long ans = a * b - c * m;
    if(ans < 0){
      ans += m;
    }
    else if(ans >= m){
      ans -= m;
    }
    return ans;
  }

  /**
   * @param num num
   * @param k   start from 0
   * @return byte
   */
  public static int kthBinDigit(int num, int k){
    return num >>> k & 1;
  }

  public static int lastKBinDigits(int num, int k){
    return num & ((1 << k) - 1);
  }

  public static int notKthBinDigits(int num, int k){
    return num ^ (1 << k);
  }

  public static int setKthBinDigitOne(int num, int k){
    return num | (1 << k);
  }

  public static int setKthBinDigitZero(int num, int k){
    return num & (~(1 << k));
  }

  /**
   * shortest undirected path
   *
   * @param n       number of vertices
   * @param weights undirected graph weights
   * @return shortest path length
   */
  public static double solve_hamilton(int n, double[][] weights){
    if(!ArrayUtils.isMatrix(weights)){
      throw new IllegalArgumentException();
    }
    int len = weights.length;
    double[][] path_vert_dist = new double[1 << len][len];
    for(var r : path_vert_dist){
      for(int i = 0; i < len; i++){
        r[i] = Double.POSITIVE_INFINITY;
      }
    }
    path_vert_dist[1][0] = 0;
    for(int path = 1; path < (1 << n); path++){
      for(int v_a = 0; v_a < n; v_a++){
        if(kthBinDigit(path, v_a) == 1){
          for(int v_b = 0; v_b < n; v_b++){
            if(kthBinDigit(path, v_b) == 1){
              double dist = path_vert_dist[notKthBinDigits(path, v_a)][v_b] + weights[v_a][v_b];
              path_vert_dist[path][v_a] = Math.min(path_vert_dist[path][v_a], dist);
            }
          }
        }
      }
    }

    return path_vert_dist[(1 << n) - 1][4];
  }

  public static int strangeSwitch(String[][] switches){
    if(!ArrayUtils.isMatrix(switches)){
      throw new IllegalArgumentException();
    }

    var funcFlipNeighbor = new Object(){
      private void apply(int r, int c){
        var funcFlipSingle = new Object(){
          private void apply(int r, int c){
            if(switches[r][c].equals("x")){
              switches[r][c] = "o";
            }
            else if(switches[r][c].equals("o")){
              switches[r][c] = "x";
            }
            else{ throw new RuntimeException("input format error."); }
          }
        };
        if((r - 1) >= 0 && (r - 1) < switches.length){
          funcFlipSingle.apply(r - 1, c);
        }
        if((c - 1) >= 0 && (c - 1) < switches.length){
          funcFlipSingle.apply(r, c - 1);
        }

        if((r + 1) >= 0 && (r + 1) < switches.length){
          funcFlipSingle.apply(r + 1, c);
        }
        if((c + 1) >= 0 && (c + 1) < switches.length){
          funcFlipSingle.apply(r, c + 1);
        }
        funcFlipSingle.apply(r, c);
      }
    };

    Deque<Tuple<Integer, Integer>> pushes = new ArrayDeque<>(16);
    var funcRecurSolve = new Object(){
      private int apply(String[][] switches, int r, int c, int push_count){
        var funcNextPos = new Object(){
          Tuple<Integer, Integer> apply(int rIdx, int cIdx){
            int nc = cIdx + 1;
            int nr = rIdx;
            if(nc >= switches.length){
              nc = 0;
              nr += 1;
            }

            return new Tuple<>(nr, nc);
          }
        };
        if(r == 0){
          var pos = funcNextPos.apply(r, c);
          int nc = pos.second();
          int nr = pos.first();

          funcFlipNeighbor.apply(r, c);
          int left_min = apply(switches, nr, nc, push_count + 1);
          funcFlipNeighbor.apply(r, c);

          int right_min = apply(switches, nr, nc, push_count);

          if(left_min < 0 && right_min < 0){
            return -1;
          }
          else if(left_min < 0){
            return right_min;
          }
          else if(right_min < 0){
            return left_min;
          }
          else{ return Math.min(left_min, right_min); }
        }
        else{
          while(!(r >= switches.length)) {
            if(switches[r - 1][c].equals("o")){
              funcFlipNeighbor.apply(r, c);
              pushes.offerLast(new Tuple<>(r, c));
              push_count += 1;
            }
            var tPos = funcNextPos.apply(r, c);
            r = tPos.first();
            c = tPos.second();
          }

          int res = push_count;
          for(var item : switches[switches.length - 1]){
            if(!item.equals("x")){
              res = -1;
              break;
            }
          }
          while(!pushes.isEmpty()) {
            var tPos = pushes.removeLast();
            funcFlipNeighbor.apply(tPos.first(), tPos.second());
          }
          return res;
        }
      }
    };

    return funcRecurSolve.apply(switches, 0, 0, 0);
  }

  public static int laserBomb(int[][] targets, int radius){
    if(!ArrayUtils.isMatrix(targets)){
      throw new IllegalArgumentException();
    }

    int[][] sums = new int[targets.length][targets.length];

    for(int r = 0; r < targets.length; r++){
      int rSum = 0;
      for(int c = 0; c < targets.length; c++){
        rSum += targets[r][c];
        if(r >= 1){
          sums[r][c] = rSum + sums[r - 1][c];
        }
        else{
          sums[r][c] = rSum;
        }
      }
    }

    if(radius > targets.length){
      return sums[sums.length - 1][sums.length - 1];
    }

    int max = 0;

    for(int r = radius - 1; r < targets.length; r++){
      for(int c = radius - 1; c < targets.length; c++){
        int cost;
        if(r - radius >= 0 && c - radius >= 0){
          cost = sums[r][c] - sums[r - radius][c] - sums[r][c - radius] + sums[r - radius][c - radius];
        }
        else if(r - radius >= 0){
          cost = sums[r][c] - sums[r - radius][c];
        }
        else if(c - radius >= 0){
          cost = sums[r][c] - sums[r][c - radius];
        }
        else{
          cost = sums[r][c];
        }
        max = Math.max(cost, max);
      }
    }

    return max;
  }

  /**
   * sum of (a^b)'s divisors mod 9901
   *
   * @param a a
   * @param b b
   * @return int
   */
  public static int sumDiv(int a, int b){
    var primesList = NumericUtils.getAllPrimeFactors(a);
    int res = 1;
    Map<Integer, Integer> primes = new HashMap<>();
    for(var prime : primesList){
      primes.put(prime, primes.getOrDefault(prime, 0) + 1);
    }
    for(var p_i : primes.entrySet()){
      res *= geometricSequenceSum(p_i.getKey(), p_i.getValue() * b);
    }
    return res % 9901;
  }

  /**
   * (1 + p + p^2 + ... + p^n)%m
   *
   * @param p p
   * @param n n
   * @return int
   */
  public static int geometricSequenceSum(int p, int n){
    if(n == 0){
      return 1;
    }

    if(n % 2 == 1){
      return (int) ((1 + Math.pow(p, (double) (n + 1) / 2)) * geometricSequenceSum(p, (n - 1) / 2));
    }
    else{
      return (int) (((1 + Math.pow(p, (double) n / 2))) * geometricSequenceSum(p, n / 2 - 1) + Math.pow(p, n));
    }
  }

  /**
   * 严格单调性极值
   *
   * @param nums  num list
   * @param <Num> comparable
   * @return index
   */
  public static <Num extends Comparable<Num>> int maxExtremum(List<Num> nums){
    int r = nums.size();
    int l = 0;
    while(r - l >= 3) {
      int mid = (r + l) / 2;
      var l_m = nums.get(mid - 1);
      var r_m = nums.get(mid + 1);
      if(l_m.compareTo(r_m) < 0){
        l = mid;
      }
      else if(l_m.compareTo(r_m) > 0){
        r = mid + 1;
      }
      else{
        l = mid;
        r = mid + 1;
      }
    }

    Num max = nums.get(l);
    int max_idx = l;
    for(int i = l + 1; i < r; i++){
      var t = nums.get(i);
      if(max.compareTo(t) < 0){
        max = t;
        max_idx = i;
      }
    }

    return max_idx;
  }

  /**
   * binary search on (small) answer range
   * discrete max average[-10, 10]
   *
   * @param nums  int array
   * @param limit low limit
   * @return max average
   */
  public static int bestCowFences(int[] nums, int limit){
    int min = -10, max = 11;

    int[] norm = new int[nums.length];
    while(max - min > 1) {
      System.arraycopy(nums, 0, norm, 0, nums.length);
      int mid = (min + max) / 2;
      norm[0] -= mid;
      int t_min = norm[0], t_max_sum = -11;
      for(int i = 1; i < norm.length; i++){
        norm[i] -= mid;
        norm[i] += norm[i - 1];
        if(i >= limit){
          if(norm[i - limit] < t_min){
            t_min = norm[i - limit];
          }
          int sum = norm[i] - t_min;
          if(sum > t_max_sum){
            t_max_sum = sum;
          }
        }
      }
      if(t_max_sum >= 0){
        min = mid;
      }
      else{
        max = mid;
      }
    }
    return min;
  }

  public static List<Integer> innovativeBusiness(boolean[][] greater){
    List<Integer> res = new ArrayList<>(greater.length);

    for(int i = 0; i < greater.length; i++){
      int l = 0, r = res.size();
      while(r - l > 0) {
        int mid = (r + l) / 2;
        var gt = greater[i][res.get(mid)];
        if(gt){
          l = mid + 1;
        }
        else{
          r = mid;
        }
      }
      res.add(l, i);

    }
    return res;
  }

  public static <E> List<E> discrete(List<E> l, Comparator<E> comp){
    l.sort(comp);
    return l.stream().distinct().toList();
  }

  public static <E> int query(List<E> dis, E elem, Comparator<E> comp){
    int l = 0, r = dis.size();
    while(r - l > 1) {
      int mid = (r + l) / 2;
      var mid_e  =dis.get(mid);
      int c= comp.compare(elem, mid_e);
      if(c > 0){
        l = mid + 1;
      }
      else if(c < 0){
        r = mid;
      }
      else {
        return mid;
      }
    }
    return l;
  }
}
