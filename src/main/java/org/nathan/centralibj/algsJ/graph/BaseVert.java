package org.nathan.centralibj.algsJ.graph;

public class BaseVert<Id> {
  final Id identity;

  public BaseVert(Id id){
    identity = id;
  }

  public Id identity(){
    return identity;
  }

  @Override
  public String toString(){
    return String.format("vertex<%s>", identity);
  }
}
