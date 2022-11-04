package dev.qhc99.centralibj.algsJ.dataStruc;


import java.util.ArrayDeque;
import java.util.Deque;

public class SharedList<T>{
  public final T Data;
  SharedList<T> parent;

  public SharedList(T data){
    this.Data = data;
  }

  public void setParent( SharedList<T> p){
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
