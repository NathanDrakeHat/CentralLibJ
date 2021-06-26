package org.nathan;


import org.nathan.algsJ.structures.TernaryTries;
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
    TernaryTries<Void> nullTST = new TernaryTries<>();
    nullTST.put("a",null);
  }
}


