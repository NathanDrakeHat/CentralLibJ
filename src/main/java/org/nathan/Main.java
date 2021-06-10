package org.nathan;

import org.nathan.centralUtils.utils.IOUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


class Main{
  static class GitPush{
    public static void gitPush(String[] args){
      if(args.length == 1){
        var i = Integer.valueOf(args[0]);
        IOUtils.system(String.format("git_proxy \"git push\" %s", args[0]));
      }
    }
  }


  public static void main(String[] args){
    if(args.length > 0){
      GitPush.gitPush(args);
    }
    else{
      List<Integer> l = new ArrayList<>();
    }
  }
}
