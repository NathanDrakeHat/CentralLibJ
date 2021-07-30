package org.nathan.centralibj.algsJ.dataStruc;

import org.junit.jupiter.api.Test;
import org.nathan.centralibj.utils.ArrayUtils;

import static org.junit.jupiter.api.Assertions.*;

class SuffixSumTreeArrayTest{

  @Test
  void sumOf(){
    var ds = ArrayUtils.lineSpace(1.,16., 1);
    var ta = new SuffixSumTreeArray<>(ds, Double::sum, (i,j)->i-j);
    var acc = ds.get(0);
    for(int i = 1; i < ds.size(); i++){
      acc += ds.get(i);
      ds.set(i, acc);
    }
    for(int i = 0; i < ds.size(); i++){
      assertEquals(ds.get(i), ta.sumOf(i+1));
    }
  }
}