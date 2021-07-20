package org.nathan;


import org.nathan.centralUtils.utils.NativeUtils;

import static org.nathan.acm.ACM0x10.largestXORPair;


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
    var ans = largestXORPair(new int[]{124,124,155});
    System.out.println(ans);
  }
}


