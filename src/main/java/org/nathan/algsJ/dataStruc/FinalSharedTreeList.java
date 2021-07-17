package org.nathan.algsJ.dataStruc;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;

public class FinalSharedTreeList<T>{
  public final T Data;
  FinalSharedTreeList<T> parent;

  public FinalSharedTreeList(T data){
    this.Data = data;
  }

  public void setParent(@NotNull FinalSharedTreeList<T> p){
    parent = p;
  }

  public Deque<T> toDeque(){
    Deque<T> ans = new ArrayDeque<>();
    var p = this;
    while(p != null){
      ans.addFirst(p.Data);
      p = p.parent;
    }
    return ans;
  }
}
