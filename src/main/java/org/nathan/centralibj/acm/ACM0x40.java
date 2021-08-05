package org.nathan.centralibj.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralibj.algsJ.dataStruc.DisjointSet;
import org.nathan.centralibj.utils.LambdaUtils;
import org.nathan.centralibj.utils.tuples.Triad;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

class ACM0x40 {
  /**
   * disjoint set with weight edge
   * <br/>NOI2022/CODEVS1540
   */
  public static class LegendOfGalaxyHero {
    final int[] repOfPlane;
    final int[] distanceToHead;
    final int[] sizeOfSet; // only value of head is meaningful

    LegendOfGalaxyHero(int N) {
      repOfPlane = IntStream.range(0, N).toArray();
      distanceToHead = new int[N];
      sizeOfSet = new int[N];
      Arrays.fill(sizeOfSet, 1);
    }

    private int headOf(int thisIdx) {
      var rep = repOfPlane[thisIdx];
      if (rep == thisIdx) {
        return thisIdx;
      }
      var head = headOf(rep);
      distanceToHead[thisIdx] += distanceToHead[rep];
      repOfPlane[thisIdx] = head;
      return head;
    }

    public void concatenate(int i, int j) {
      int ri = headOf(i), rj = headOf(j);
      repOfPlane[ri] = rj;
      distanceToHead[ri] = sizeOfSet[rj];
      sizeOfSet[rj] += sizeOfSet[ri];
    }

    /**
     * @param i index
     * @param j index
     * @return distance if in the same line or -1
     */
    public int distance(int i, int j) {
      int headI = headOf(i), headJ = headOf(j);
      if (headI == headJ) {
        return Math.abs(distanceToHead[i] - distanceToHead[j]);
      }
      else {
        return -1;
      }
    }
  }

  /**
   * disjoint set with extended field
   * <br/>NOI2001/POJ1182
   *
   * @param N         number of animal
   * @param statement same
   * @return falsehood
   */
  public static int foodChain(int N, @NotNull List<Triad<Integer, Integer, Integer>> statement) {
    // self, eat, enemy
    List<Triad<DisjointSet, DisjointSet, DisjointSet>> aniFields = new ArrayList<>(N);
    for (int i = 0; i < N; i++) {
      aniFields.add(new Triad<>(new DisjointSet(), new DisjointSet(), new DisjointSet()));
    }
    int ans = 0;
    var funcObj = new Object() {
      DisjointSet getSelf(Triad<DisjointSet, DisjointSet, DisjointSet> t) {
        return t.first();
      }

      DisjointSet getEat(Triad<DisjointSet, DisjointSet, DisjointSet> t) {
        return t.second();
      }

      DisjointSet getEnemy(Triad<DisjointSet, DisjointSet, DisjointSet> t) {
        return t.third();
      }
    };
    for (var t : statement) {
      var n = t.first();
      var x = t.second();
      var y = t.third();
      if (n == 1) {
        if (!(DisjointSet.inSameSet(funcObj.getEat(aniFields.get(x)), funcObj.getSelf(aniFields.get(y))) ||
                DisjointSet.inSameSet(funcObj.getSelf(aniFields.get(x)), funcObj.getEat(aniFields.get(y))))) {
          DisjointSet.union(funcObj.getSelf(aniFields.get(x)), funcObj.getSelf(aniFields.get(y)));
          DisjointSet.union(funcObj.getEat(aniFields.get(x)), funcObj.getEat(aniFields.get(y)));
          DisjointSet.union(funcObj.getEnemy(aniFields.get(x)), funcObj.getEnemy(aniFields.get(y)));
        }
        else {
          ans++;
        }
      }
      else if (n == 2) {
        if (!(DisjointSet.inSameSet(funcObj.getSelf(aniFields.get(x)), funcObj.getSelf(aniFields.get(y))) ||
                DisjointSet.inSameSet(funcObj.getSelf(aniFields.get(x)), funcObj.getEat(aniFields.get(y))))) {
          DisjointSet.union(funcObj.getEat(aniFields.get(x)), funcObj.getSelf(aniFields.get(y)));
          DisjointSet.union(funcObj.getSelf(aniFields.get(x)), funcObj.getEnemy(aniFields.get(y)));
          DisjointSet.union(funcObj.getEnemy(aniFields.get(x)), funcObj.getEat(aniFields.get(y)));
        }
        else {
          ans++;
        }
      }
      else {
        throw new IllegalArgumentException("operation is not \"1\" or \"2\"");
      }
    }
    return ans;
  }

  /**
   * suffix sum query
   */
  public static class TreelikeArray {
    final int[] arr;
    public final int ArrayLength;

    public TreelikeArray(int[] array) {
      ArrayLength = array.length;
      arr = new int[ArrayLength + 1];

      for (int i = 1; i <= ArrayLength; i++) {
        prefixSumAdd(i, array[i - 1]);
      }
    }

    /**
     * zero initialization
     *
     * @param N number
     */
    public TreelikeArray(int N) {
      ArrayLength = N;
      arr = new int[N + 1];
    }

    /**
     * sum of i items
     *
     * @param count count
     * @return suffix sum
     */
    public int prefixSumOf(int count) {
      if (count < 0 || count >= arr.length) {
        throw new IllegalArgumentException();
      }
      int ans = 0;
      while (count != 0) {
        ans += arr[count];
        count -= lowBit(count);
      }
      return ans;
    }

    /**
     * @param l inclusive
     * @param h inclusive
     * @return sum of range
     */
    public int prefixSumOfRange(int l, int h) {
      if (l < 0 || l >= arr.length || h < 0 || h >= arr.length || h < l) {
        throw new IllegalArgumentException();
      }
      return prefixSumOf(h) - prefixSumOf(l - 1);

    }

    public void prefixSumAdd(int idx, int diff) {
      if (idx <= 0 || idx >= arr.length) {
        throw new IllegalArgumentException();
      }
      int len = arr.length;
      while (idx < len) {
        arr[idx] += diff;
        idx += lowBit(idx);
      }
    }

    private static int lowBit(int x) {
      return x & -x;
    }
  }

  /**
   * POJ3468
   */
  public static class RangeAddRangeSumQuery {

    private final TreelikeArray c0;
    private final TreelikeArray c1;
    private final int[] sum;

    public RangeAddRangeSumQuery(int[] array) {
      var len = array.length;
      c0 = new TreelikeArray(len);
      c1 = new TreelikeArray(len);

      sum = new int[array.length + 1];
      for (int i = 1; i < len; i++) {
        sum[i] += sum[i - 1];
      }
    }

    public void addRange(int l, int r, int diff) {
      c0.prefixSumAdd(l, diff);
      if (r + 1 <= c0.ArrayLength) {
        c0.prefixSumAdd(r + 1, -diff);
      }
      c1.prefixSumAdd(l, l * diff);
      if (r + 1 <= c1.ArrayLength) {
        c1.prefixSumAdd(r + 1, -(r + 1) * diff);
      }
    }

    public int sumOfRange(int l, int r) {
      return (sum[r] + (r + 1) * c0.prefixSumOf(r) - c1.prefixSumOf(r)) -
              (sum[l - 1] + l * c1.prefixSumOf(l - 1) - c1.prefixSumOf(l - 1));
    }
  }

  /**
   * range addable query, maintain node info to augment tree, delay update for better performance
   *
   * @param <Data>
   */
  public static class SegmentTreeTemplate<Data, Node> {

    private final List<Node> array;
    private final BiFunction<Data, Data, Data> op;
    final Function<Node, Integer> getLeft;
    final BiConsumer<Node, Integer> setLeft;
    final Function<Node, Integer> getRight;
    final BiConsumer<Node, Integer> setRight;
    final BiConsumer<List<Node>, Integer> update;
    final Function<Node, Data> getData;
    final BiConsumer<Node, Data> setData;

    public static SegmentTreeTemplate<Integer, MaxNode<Integer>> maxSegmentTree(@NotNull List<Integer> a) {
      return new SegmentTreeTemplate<>(
              a,
              (i,j)->{
                if(i == null){
                  return j;
                }
                else if(j == null){
                  return i;
                }
                else {
                  return Math.max(i,j);
                }
              },
              MaxNode::new,
              n -> n.l,
              (n, i) -> n.l = i,
              n -> n.r,
              (n, i) -> n.r = i,
              n -> n.data,
              (n, i) -> n.data = i,
              (l, p) -> l.get(p).data = Math.max(l.get(2 * p).data, l.get(2 * p + 1).data));
    }

    public static SegmentTreeTemplate<Integer, SumNode<Integer>> maxContinuousSumSegmentTree(@NotNull List<Integer> a) {
      return new SegmentTreeTemplate<>(
              a,
              (i,j)->{
                if(i == null){
                  return j;
                }
                else if(j == null){
                  return i;
                }
                else {
                  return Math.max(i,j);
                }
              },
              SumNode::new,
              n -> n.l,
              (n, i) -> n.l = i,
              n -> n.r,
              (n, i) -> n.r = i,
              n -> n.data,
              (n, i) -> n.data = i,
              (l, p) -> {
                var ap = l.get(p);
                var left = l.get(2 * p);
                var right = l.get(2 * p + 1);
                ap.sum = left.sum + right.sum;
                ap.lMax = Arrays.stream(new int[]{left.lMax, right.lMax, left.sum}).max().getAsInt();
                ap.rMax = Arrays.stream(new int[]{left.rMax, right.rMax, right.sum}).max().getAsInt();
                ap.data = Arrays.stream(new int[]{left.data, right.data, left.rMax, right.lMax}).max().getAsInt();
              }
      );
    }

    /**
     * @param a  array
     * @param op null compatible operation
     */
    public SegmentTreeTemplate(
            @NotNull List<Data> a,
            @NotNull BiFunction<Data, Data, Data> op,
            @NotNull LambdaUtils.Gettable<Node> nodeInit,
            @NotNull Function<Node, Integer> getLeft,
            @NotNull BiConsumer<Node, Integer> setLeft,
            @NotNull Function<Node, Integer> getRight,
            @NotNull BiConsumer<Node, Integer> setRight,
            @NotNull Function<Node, Data> getData,
            @NotNull BiConsumer<Node, Data> setData,
            @NotNull BiConsumer<List<Node>, Integer> update) {
      this.getLeft = getLeft;
      this.getRight = getRight;
      this.setLeft = setLeft;
      this.setRight = setRight;
      this.update = update;
      this.getData = getData;
      this.setData = setData;

      int size = a.size();
      int len = size * 4;
      array = new ArrayList<>(len);
      for (int i = 0; i < len; i++) {
        array.add(nodeInit.get());
      }
      this.op = op;

      var funcBuild = new Object() {
        void apply(int p, int l, int r) {
          var al = array.get(p);
          setLeft.accept(al, l);
          setRight.accept(al, r);
          if (l == r) {
            setData.accept(al, Objects.requireNonNull(a.get(l-1)));
            return;
          }
          int mid = (l + r) / 2;
          apply(2 * p, l, mid);
          apply(2 * p + 1, mid + 1, r);
          update.accept(array, p);
        }
      };
      funcBuild.apply(1, 1, size);
    }


    public void update(int x, Data v) {
      update(1, x, v);
    }

    private void update(int p, int x, Data v) {
      var ap = array.get(p);
      int l = getLeft.apply(ap), r = getRight.apply(ap);
      int mid = (l + r) / 2;
      if (l == r) {
        setData.accept(ap, v);
        return;
      }
      if (x <= mid) {
        update(2 * p, x, v);
      }
      else {
        update(2 * p + 1, x, v);
      }
      update.accept(array, p);
    }

    public @NotNull Optional<Data> query(int l, int r) {
      var ans = query(1, l, r);
      if (ans == null) {
        return Optional.empty();
      }
      else {
        return Optional.of(ans);
      }
    }

    private Data query(int p, int l, int r) {
      var ap = array.get(p);
      int al = getLeft.apply(ap), ar = getRight.apply(ap);
      int mid = (ar + al) / 2;
      if (l <= al && r >= ar) {
        return getData.apply(ap);
      }

      Data ans = null;
      if (l <= mid) {
        ans = op.apply(ans, query(2 * p, l, r));
      }
      if (r > mid) {
        ans = op.apply(ans, query(2 * p + 1, l, r));
      }
      return ans;
    }

    public static class MaxNode<T1> {
      int l, r;
      T1 data;
    }

    public static class SumNode<T1> {
      int l, r;
      T1 data, sum, rMax, lMax;
    }
  }

  // TODO 点分治
}
