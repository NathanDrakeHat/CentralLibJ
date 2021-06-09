package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

public class FlowEdge<Id> extends BaseEdge<Id>{
  int capacity;
  int flow;

  public FlowEdge(@NotNull Id f, @NotNull Id l){
    super(f, l);
  }

}
