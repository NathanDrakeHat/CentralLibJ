package org.nathan.algsJ.DP;


final class CutRod{
  static class CutResult{
    public static class Node{
      public int value;
      private Node next;

      public Node(int x){
        this.value = x;
      }

      public Node getNext(){
        return this.next;
      }

      public void setNext(Node n){
        this.next = n;
      }

      public int getValue(){
        return this.value;
      }
    }

    private int max_price;
    private Node root;
    private int total = 0;
    private Node ptr;

    public void insert(int x){
      this.total++;
      if(root == null){
        root = new Node(x);
        ptr = root;
        return;
      }
      Node n = new Node(x);
      this.ptr.setNext(n);
      this.ptr = this.ptr.getNext();
    }

    public int[] getApproach(){
      int[] temp = new int[this.total];
      Node p;
      p = this.root;
      for(int i = 0; i < this.total; i++){
        temp[i] = p.getValue();
        p = p.getNext();
      }
      return temp;
    }

    public int getPrice(){
      return this.max_price;
    }

    public void setPrice(int t){
      this.max_price = t;
    }

    public int getSum(){
      Node p;
      p = this.root;
      int sum = 0;
      for(int i = 0; i < this.total; i++){
        sum += p.getValue();
        p = p.getNext();
      }
      return sum;
    }
  }

  public static CutResult recursiveCutRod(int[] p, int rod_len){
    if(rod_len <= 0){
      throw new IllegalArgumentException();
    }

    var res = new CutResult();
    int q;
    if(rod_len <= 10){
      q = p[(rod_len - 1)];
    }
    else{
      q = 0;
    }

    for(int i = 1; (i < rod_len) && (i - 1 < 10); i++){
      if(q < p[i - 1] + recursiveCutRod(p, rod_len - i).getPrice()){
        q = p[i - 1] + recursiveCutRod(p, rod_len - i).getPrice();
        res.insert(i);
      }
    }
    int sum = res.getSum();
    if(sum == 0){
      res.insert(rod_len);
    }
    else{
      res.insert(rod_len - sum);
    }
    res.setPrice(q);
    return res;
  }

  public static CutResult topDownCutRod(int[] p, int rod_len){
    // recursive with memory
    if(rod_len <= 0){
      throw new IllegalArgumentException();
    }
    CutResult[] memory = new CutResult[rod_len];
    return topDownCutRod(p, rod_len, memory);
  }

  private static CutResult topDownCutRod(int[] p, int rod_len, CutResult[] memory){
    if(memory[rod_len - 1] != null){
      return memory[rod_len - 1];
    }

    CutResult res = new CutResult();
    int q;
    if(rod_len <= 10){
      q = p[rod_len - 1];
    }
    else{
      q = 0;
    }

    for(int i = 1; (i < rod_len) && (i - 1 < 10); i++){
      if(q < p[i - 1] + topDownCutRod(p, rod_len - i, memory).getPrice()){
        q = p[i - 1] + topDownCutRod(p, rod_len - i, memory).getPrice();
        res.insert(i);
      }
    }
    int sum = res.getSum();
    if(sum == 0){
      res.insert(rod_len);
    }
    else{
      res.insert(rod_len - sum);
    }
    res.setPrice(q);
    return res;
  }

  public static CutResult bottomUpCutRod(int[] p, int rod_len){
    if(rod_len <= 0){
      throw new IllegalArgumentException();
    }
    CutResult[] cache = new CutResult[rod_len];
    for(int i = 1; i <= rod_len; i++){ // cache problem from 1 to rod_len
      int max_price = 0;
      CutResult res = new CutResult();
      if(i - 1 < 10){
        max_price = p[i - 1];
      } // not cut
      for(int j = 1; (j < i) && (j - 1 < 10); j++){ // cut
        if(max_price < p[j - 1] + cache[i - j - 1].getPrice()){
          max_price = p[j - 1] + cache[i - j - 1].getPrice();
          res.insert(j);
        }
      }
      int sum = res.getSum();
      if(sum == 0){
        res.insert(rod_len);
      }
      else{
        res.insert(rod_len - sum);
      }
      res.setPrice(max_price);
      cache[i - 1] = res;
    }
    return cache[rod_len - 1];
  }
}