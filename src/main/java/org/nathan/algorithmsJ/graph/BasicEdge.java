package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

public class BasicEdge<V extends Vertex<?>> implements Edge<V>{

  @NotNull final V former;
  @NotNull final V later;

  public BasicEdge(@NotNull V f, @NotNull V l){
    former = f;
    later = l;
  }

  @Override
  public @NotNull V former(){
    return former;
  }

  @Override
  public @NotNull V later(){
    return later;
  }
}
