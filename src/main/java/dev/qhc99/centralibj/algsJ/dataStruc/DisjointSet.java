package dev.qhc99.centralibj.algsJ.dataStruc;


public final class DisjointSet{
  private int rank = 0;
  private DisjointSet parent = this;

  /**
   * find identifier of the set of an element
   *
   * @param x element
   * @return identifier
   */
  private static DisjointSet findGroupId(DisjointSet x){
    if(x != x.parent){
      x.parent = findGroupId(x.parent);
    }
    return x.parent;
  }

  public static boolean inSameSet(DisjointSet a, DisjointSet b){
    return findGroupId(a) == findGroupId(b);
  }

  public static void union(DisjointSet a, DisjointSet b){
    link(findGroupId(a), findGroupId(b));
  }

  private static void link(DisjointSet x, DisjointSet y){
    if(x.rank > y.rank){
      y.parent = x;
    }
    else{
      x.parent = y;
      if(x.rank == y.rank){
        y.rank = y.rank + 1;
      }
    }
  }
}