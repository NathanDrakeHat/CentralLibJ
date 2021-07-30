package org.nathan;


import org.nathan.centralibj.algsJ.dataStruc.SuffixSumArray;
import org.nathan.centralibj.utils.ArrayUtils;
import org.nathan.centralibj.utils.NativeUtils;

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

    var a = new SuffixSumArray<>(ArrayUtils.lineSpace(1.,16.,1), Double::sum, (i, j) -> i - j);
    System.out.println(a);
  }
}


