package org.nathan;


import org.nathan.algsJ.numeric.NumberTheory;
import org.nathan.centralUtils.utils.NativeUtils;



class Main {
  static class GitPush {
    public static void gitPush(String[] args) {
      NativeUtils.GitProxy(args);
    }
  }


  public static void main(String[] args) {
    if (args.length > 0) {
      GitPush.gitPush(args);
    }
    System.out.println(NumberTheory.rho(644725));
  }
}


