package org.nathan.centralibj.algsJ.dataStruc;

/**
 * to use union() and findSet(),
 * extends this class or initial it in a public field
 */
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

  public DisjointSet findGroupId(){
    if(this != this.parent){
      this.parent = findGroupId(this.parent);
    }
    return this.parent;
  }

  public void union(DisjointSet a){
    link(findGroupId(a), findGroupId());
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