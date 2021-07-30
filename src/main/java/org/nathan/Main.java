package org.nathan;


import org.nathan.centralibj.algsJ.dataStruc.SuffixSumTreeArray;
import org.nathan.centralibj.utils.ArrayUtils;
import org.nathan.centralibj.utils.NativeUtils;

import java.util.List;
import java.util.stream.DoubleStream;

class Main{
  static class GitPush{
    public static void gitPush(String[] args){
      NativeUtils.GitProxy(args);
    }
  }


  public static void main(String[] args){
    if(args.length > 0){
      GitPush.gitPush(args);
    }

    var a = new SuffixSumTreeArray<>(ArrayUtils.lineSpace(1.,16.,1), Double::sum, (i, j) -> i - j);
    System.out.println(a);
  }
}


