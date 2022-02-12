package org.qhc99.centralibj.algsJ.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HuffmanTest{

  Map<String, Double> data;
  Map<String, Integer> encodeLen;
  {
    data = new HashMap<>(6);
    encodeLen = new HashMap<>(6);
    data.put("1", 1.);
    encodeLen.put("1", 3);
    data.put("2", 2.);
    encodeLen.put("2", 3);
    data.put("3", 3.);
    encodeLen.put("3", 2);
    data.put("5", 5.);
    encodeLen.put("5", 2);
    data.put("8", 8.);
    encodeLen.put("8", 1);
    data.put("9", 9.);
    encodeLen.put("9", 1);
  }

  @Test
  void encodeTest(){
    var ans = Huffman.encode(data, 3);
    for(var kv : ans.entrySet()){
      kv.setValue(kv.getValue().replaceAll(",", ""));
      assertEquals(encodeLen.get(kv.getKey()), kv.getValue().length());
    }

    List<String> code = ans.values().stream().toList();
    for(int i = 0; i < code.size(); i++){
      for(int j = 0; j < code.size(); j++){
        if(i != j){
          assertFalse(code.get(i).startsWith(code.get(j)));
        }
      }
    }
  }
}