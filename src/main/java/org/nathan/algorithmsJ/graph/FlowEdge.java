package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

public class FlowEdge<Id> extends BaseEdge<Id>{
  int capacity;
  int flow;

  public FlowEdge(@NotNull Id f, @NotNull Id l, int capacity, int flow){
    super(f, l);
    this.capacity = capacity;
    this.flow = flow;
  }

}
