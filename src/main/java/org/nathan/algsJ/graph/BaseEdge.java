package org.nathan.algsJ.graph;

import org.jetbrains.annotations.NotNull;

public class BaseEdge<V>{
  @NotNull final V former;
  @NotNull final V latter;

  public BaseEdge(@NotNull V f, @NotNull V l){
    former = f;
    latter = l;
  }

  public @NotNull V from(){
    return former;
  }

  public @NotNull V to(){
    return latter;
  }

  public @NotNull V another(V vertex){
    if(vertex.equals(former)){
      return latter;
    }
    else if(vertex.equals(latter)){
      return former;
    }
    else{
      throw new IllegalArgumentException();
    }
  }
}
