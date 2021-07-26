package org.nathan;


import org.nathan.centralibj.utils.ExceptionUtils;
import org.nathan.centralibj.utils.NativeUtils;
import org.nathan.centralibj.utils.misc.Ref;

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


