package dev.qhc99.centralibj.algsJ.misc;

import java.util.ArrayList;

/**
 * greedy algorithm
 */
final class ActivitySelection{
  public static class Node{
    private final int val;
    private Node next;

    Node(int val){
      this.val = val;
      this.next = null;
    }

    Node getNext(){
      return this.next;
    }

    Node setNext(Node n){
      this.next = n;
      return this;
    }

    Node setAndReturnNext(Node n){
      setNext(n);
      return getNext();
    }

    public int[] getResult(){
      var t = new ArrayList<Integer>();
      Node ptr = this;
      do{
        t.add(ptr.val);
        ptr = ptr.getNext();
      }
      while(ptr != null);
      var res = new int[t.size()];
      int idx = 0;
      for(var i : t){
        res[idx++] = i;
      }
      return res;
    }
  }

  public static Node recursiveActivitySelector(int[] s, int[] f){
    return new Node(0).setNext(recursiveActivitySelector(s, f, 0));
  }

  public static Node recursiveActivitySelector(int[] s, int[] f, int k){
    int m = k + 1;
    if(k >= s.length || m >= s.length){
      return null;
    }
    while(m < s.length && s[m] < f[k]) { // f is sorted
      m++;
    }
    return new Node(m).setNext(recursiveActivitySelector(s, f, m));
  }

  /**
   * select end earlier task
   *
   * @param s start
   * @param f finish
   * @return res
   */
  public static Node greedyActivitySelector(int[] s, int[] f){
    int len = s.length;
    Node ptr = new Node(0);
    Node root = ptr;
    int f_idx = 0;
    for(int p = 1; p < len; p++){
      if(s[p] >= f[f_idx]){
        ptr = ptr.setAndReturnNext(new Node(p));
        f_idx = p;
      }
    }
    return root;
  }

}

