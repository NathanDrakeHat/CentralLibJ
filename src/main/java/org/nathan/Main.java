package org.nathan;


import org.nathan.centralUtils.utils.ArrayUtils;
import org.nathan.centralUtils.utils.NativeUtils;

import java.util.Arrays;


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
  }

}


