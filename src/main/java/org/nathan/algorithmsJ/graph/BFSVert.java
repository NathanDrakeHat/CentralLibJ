package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BFSVert<ID> implements Vertex<ID>{
  final ID id;
  @Nullable
  BFSVert<ID> parent;
  double distance; // d
  BFS.COLOR color;

  BFSVert(@NotNull ID name){
    this.id = name;
  }

  BFSVert(){
    id = null;
  }

  public static <S_ID> BFSVert<S_ID> make(S_ID id){
    return new BFSVert<>(id);
  }

  public ID getId(){
    return id;
  }

  public @Nullable BFSVert<ID> getParent(){
    return parent;
  }

  public double getDistance(){
    return distance;
  }

  @Override
  public String toString(){
    return String.format("BFS.Vertex: (%s)", id != null ? id.toString() : "()");
  }
}
