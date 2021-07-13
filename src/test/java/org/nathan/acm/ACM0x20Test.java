package org.nathan.acm;

import org.junit.jupiter.api.Test;
import org.nathan.algsJ.graph.BaseEdge;
import org.nathan.algsJ.graph.BaseVert;
import org.nathan.algsJ.graph.LinkedGraph;
import static org.nathan.acm.ACM0x20.*;

import org.nathan.centralUtils.utils.ArrayUtils;
import org.nathan.centralUtils.utils.NumericUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ACM0x20Test{
  static final int iteration = 20;

  LinkedGraph<BaseVert<Integer>, BaseEdge<BaseVert<Integer>>> tsCase;

  {
    List<BaseVert<Integer>> vs = new ArrayList<>(4);
    vs.add(new BaseVert<>(1));
    vs.add(new BaseVert<>(2));
    vs.add(new BaseVert<>(3));
    vs.add(new BaseVert<>(4));
    tsCase = new LinkedGraph<>(true, vs);
    tsCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(1)));
    tsCase.addEdge(new BaseEdge<>(vs.get(1), vs.get(2)));
    tsCase.addEdge(new BaseEdge<>(vs.get(2), vs.get(3)));

    tsCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(2)));
    tsCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(3)));

    tsCase.addEdge(new BaseEdge<>(vs.get(1), vs.get(3)));
  }


  @Test
  void topologicalSortTest(){
    var ans = ACM0x20.topologicalSort(tsCase);
    assertEquals(4, ans.size());
    var vs = tsCase.allVertices();
    assertEquals(vs, ans);

    tsCase.addEdge(new BaseEdge<>(vs.get(3), vs.get(0)));
    var ans1 = ACM0x20.topologicalSort(tsCase);
    assertNotEquals(4, ans1.size());
  }

  LinkedGraph<BaseVert<Integer>, BaseEdge<BaseVert<Integer>>> reCase;

  {
    List<BaseVert<Integer>> vs = new ArrayList<>(4);
    vs.add(new BaseVert<>(1));
    vs.add(new BaseVert<>(2));
    vs.add(new BaseVert<>(3));
    vs.add(new BaseVert<>(4));
    reCase = new LinkedGraph<>(true, vs);
    reCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(1)));
    reCase.addEdge(new BaseEdge<>(vs.get(1), vs.get(2)));
    reCase.addEdge(new BaseEdge<>(vs.get(2), vs.get(3)));

    reCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(2)));
    reCase.addEdge(new BaseEdge<>(vs.get(0), vs.get(3)));

    reCase.addEdge(new BaseEdge<>(vs.get(1), vs.get(3)));
  }

  @Test
  void reachabilityCountTest(){
    var ans = ACM0x20.reachabilityCount(reCase);
    var vs = reCase.allVertices();
    assertEquals(4, ans.get(vs.get(0)));
    assertEquals(3, ans.get(vs.get(1)));
    assertEquals(2, ans.get(vs.get(2)));
    assertEquals(1, ans.get(vs.get(3)));
  }

  @Test
  void additionChainsTest(){
    assertEquals(5, ACM0x20.additionChains(12).size());
    assertEquals(6, ACM0x20.additionChains(13).size());
    assertEquals(6, ACM0x20.additionChains(14).size());
    assertEquals(6, ACM0x20.additionChains(15).size());
    assertEquals(5, ACM0x20.additionChains(16).size());
  }

  int[][] sgCases = new int[iteration][];
  int[] sgAnswers = new int[iteration];
  {
    var rand = new SplittableRandom();
    for(int i = 0; i < iteration; i++){
      sgCases[i] = ArrayUtils.randomIntArray(0, Integer.MAX_VALUE - 1, rand.nextInt(10,45));
      Set<Integer> set = new HashSet<>(1);
      set.add(0);
      for(int j = 0; j < sgCases[i].length; j++){
        Set<Integer> next = new HashSet<>(set.size() * 2);
        for(var t : set){
          next.add(t);
          if(NumericUtils.addNotOverflow(t, sgCases[i][j])){
            next.add(t + sgCases[i][j]);
          }
        }
        set = next;
      }
      //noinspection OptionalGetWithoutIsPresent
      sgAnswers[i] = set.stream().max(Integer::compareTo).get();
    }
  }

  @Test
  void sendGiftTest(){
    for(int i = 0; i < iteration; i++){
      assertEquals(sgAnswers[i], sendGift(sgCases[i]));
    }
  }
}
