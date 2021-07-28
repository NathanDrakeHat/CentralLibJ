package org.nathan.centralibj.acm;

import java.util.Arrays;
import java.util.stream.IntStream;

class ACM0x40{
  /**
   * NOI2022/CODEVS1540
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
     *
     * @param i index
     * @param j index
     * @return distance if in the same line or -1
     */
    public int distance(int i, int j){
      int headI = headOf(i), headJ = headOf(j);
      if(headI == headJ){
        return Math.abs(distanceToHead[i] - distanceToHead[j]);
      }
      else return -1;
    }
  }

  public static class FoodChain{
    private int falsehood;

  }
}
