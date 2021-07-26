package org.nathan;


import it.unimi.dsi.util.XoRoShiRo128PlusRandom;
import org.nathan.centralibj.utils.NativeUtils;

// TODO faster random
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
    XoRoShiRo128PlusRandom a = new XoRoShiRo128PlusRandom();
  }
}


