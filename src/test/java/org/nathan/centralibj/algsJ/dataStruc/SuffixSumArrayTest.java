package org.nathan.centralibj.algsJ.dataStruc;

import org.junit.jupiter.api.Test;


import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SuffixSumArrayTest{

  @Test
  void sumOf(){
    var ds = IntStream.range(1,17).toArray();
    var ta = new SuffixSumArray(ds);
    var acc = ds[0];
    for(int i = 1; i < ds.length; i++){
      acc += ds[i];
      ds[i] =  acc;
    }
    for(int i = 0; i < ds.length; i++){
      assertEquals(ds[i], ta.prefixSumOf(i+1));
    }
  }
}