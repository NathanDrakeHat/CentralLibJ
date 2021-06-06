package org.nathan;

import org.nathan.centralUtils.utils.IOUtils;


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
      int i;
    }
  }
}
