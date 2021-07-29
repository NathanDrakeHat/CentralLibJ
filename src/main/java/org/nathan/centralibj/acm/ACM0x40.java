package org.nathan.centralibj.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralibj.algsJ.dataStruc.DisjointSet;
import org.nathan.centralibj.utils.tuples.Triad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class ACM0x40{
  /**
   * disjoint set with weight edge
   * <br/>NOI2022/CODEVS1540
   */
  public static class LegendOfGalaxyHero{
    final int[] repOfPlane;
    final int[] distanceToHead;
    final int[] sizeOfSet; // only value of head is meaningful

    LegendOfGalaxyHero(int N){
      repOfPlane = IntStream.range(0, N).toArray();
      distanceToHead = new int[N];
      sizeOfSet = new int[N];
      Arrays.fill(sizeOfSet, 1);
    }

    private int headOf(int thisIdx){
      var rep = repOfPlane[thisIdx];
      if(rep == thisIdx){
        return thisIdx;
      }
      var head = headOf(rep);
      distanceToHead[thisIdx] += distanceToHead[rep];
      repOfPlane[thisIdx] = head;
      return head;
    }

    public void concatenate(int i, int j){
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
    public int distance(int i, int j){
      int headI = headOf(i), headJ = headOf(j);
      if(headI == headJ){
        return Math.abs(distanceToHead[i] - distanceToHead[j]);
      }
      else{return -1;}
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
  public static int foodChain(int N, @NotNull List<Triad<Integer, Integer, Integer>> statement){
    // self, eat, enemy
    List<Triad<DisjointSet, DisjointSet, DisjointSet>> aniFields = new ArrayList<>(N);
    for(int i = 0; i < N; i++){
      aniFields.add(new Triad<>(new DisjointSet(), new DisjointSet(), new DisjointSet()));
    }
    int ans = 0;
    var funcObj = new Object(){
      DisjointSet getSelf(Triad<DisjointSet, DisjointSet, DisjointSet> t){
        return t.first();
      }

      DisjointSet getEat(Triad<DisjointSet, DisjointSet, DisjointSet> t){
        return t.second();
      }

      DisjointSet getEnemy(Triad<DisjointSet, DisjointSet, DisjointSet> t){
        return t.third();
      }
    };
    for(var t : statement){
      var n = t.first();
      var x = t.second();
      var y = t.third();
      if(n == 1){
        if(!(DisjointSet.inSameSet(funcObj.getEat(aniFields.get(x)), funcObj.getSelf(aniFields.get(y))) ||
                DisjointSet.inSameSet(funcObj.getSelf(aniFields.get(x)), funcObj.getEat(aniFields.get(y))))){
          DisjointSet.union(funcObj.getSelf(aniFields.get(x)), funcObj.getSelf(aniFields.get(y)));
          DisjointSet.union(funcObj.getEat(aniFields.get(x)), funcObj.getEat(aniFields.get(y)));
          DisjointSet.union(funcObj.getEnemy(aniFields.get(x)), funcObj.getEnemy(aniFields.get(y)));
        }
        else{ans++;}
      }
      else if(n == 2){
        if(!(DisjointSet.inSameSet(funcObj.getSelf(aniFields.get(x)), funcObj.getSelf(aniFields.get(y))) ||
                DisjointSet.inSameSet(funcObj.getSelf(aniFields.get(x)), funcObj.getEat(aniFields.get(y))))){
          DisjointSet.union(funcObj.getEat(aniFields.get(x)), funcObj.getSelf(aniFields.get(y)));
          DisjointSet.union(funcObj.getSelf(aniFields.get(x)), funcObj.getEnemy(aniFields.get(y)));
          DisjointSet.union(funcObj.getEnemy(aniFields.get(x)), funcObj.getEat(aniFields.get(y)));
        }
        else{ans++;}
      }
      else{
        throw new IllegalArgumentException("operation is not \"1\" or \"2\"");
      }
    }
    return ans;
  }
}
