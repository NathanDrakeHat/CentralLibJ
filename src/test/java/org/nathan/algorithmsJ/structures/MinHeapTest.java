package org.nathan.algorithmsJ.structures;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.nathan.centralUtils.utils.ArrayUtils.shuffledSequence;


class MinHeapTest{

  List<List<Integer>> randomAddTestCase = new ArrayList<>();

  {
    for(int i = 0; i < iter; i++){
      randomAddTestCase.add(shuffledSequence(1, 63));
    }
  }

  @Test
  void randomAddTest(){
    for(int i = 0; i < iter; i++){
      List<Integer> l = randomAddTestCase.get(i);
      MinHeap<Integer, String> m = new MinHeap<>(Integer::compare);
      for(Integer integer : l){
        m.add(String.valueOf(integer), integer);
      }
      List<Integer> res = new ArrayList<>();
      while(m.heapSize() > 0) {
        res.add(Integer.valueOf(m.extractMin()));
      }
      for(int j = 0; j < res.size() - 1; j++){
        if(res.get(j).compareTo(res.get(j + 1)) <= 0){
          assertTrue(true);
        }
        else{
          fail();
        }
      }
    }
  }

  static int iter = 10;
  List<List<Integer>> randUpdateKeyCase = new ArrayList<>(iter);

  {
    for(int i = 0; i < iter; i++){
      randUpdateKeyCase.add(shuffledSequence(1, 63));
    }
  }

  @Test
  void randomUpdateKeyTest(){
    for(int i = 0; i < iter; i++){
      List<Integer> l = randUpdateKeyCase.get(i);
      var rand = ThreadLocalRandom.current();
      MinHeap<Integer, String> heap = new MinHeap<>(
              l.stream().map(String::valueOf).collect(Collectors.toList()),
              s -> rand.nextInt(127 - 1) + 1,
              Integer::compareTo);
      List<Integer> res = new ArrayList<>();
      for(var elem : l){
        heap.updateKey(String.valueOf(elem), elem);
      }
      while(heap.heapSize() > 0) {
        res.add(Integer.valueOf(heap.extractMin()));
      }
      for(int j = 0; j < res.size() - 1; j++){
        if(!(res.get(j).compareTo(res.get(j + 1)) <= 0)){
          fail();
        }
      }
    }
  }

  @Test
  void modificationTest(){
    var m = new MinHeap<Integer, Integer>(Integer::compareTo);
    m.add(1, 1);
    m.add(2, 2);
    assertThrows(IllegalStateException.class, () -> {
      for(var i : m){
        m.add(i.first() + 3, i.second() + 3);
      }
    });
  }
}