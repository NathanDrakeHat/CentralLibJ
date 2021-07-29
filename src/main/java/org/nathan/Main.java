package org.nathan;


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
    int x = 7;
    while(x > 0){
      System.out.printf("%d,%d\n", x - (x & -x) + 1, x);
      x -= x & -x;
    }
  }
}


