package org.nathan.algsJ.graph;

import org.jetbrains.annotations.NotNull;

public class BaseEdge<V>{
  @NotNull
  final V vert_from;
  @NotNull
  final V vert_to;

  public BaseEdge(@NotNull V f, @NotNull V l){
    vert_from = f;
    vert_to = l;
  }

  public @NotNull V from(){
    return vert_from;
  }

  public @NotNull V to(){
    return vert_to;
  }

  public @NotNull V another(V vertex){
    if(vertex.equals(vert_from)){
      return vert_to;
    }
    else if(vertex.equals(vert_to)){
      return vert_from;
    }
    else{
      throw new IllegalArgumentException();
    }
  }
}
