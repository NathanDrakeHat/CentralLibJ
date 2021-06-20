package org.nathan;

import org.nathan.centralUtils.utils.IOUtils;
import org.nathan.centralUtils.utils.NativeUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



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
    else{
      Map<Integer,Void> currentSet = new ConcurrentHashMap<>();
    }
  }
}
