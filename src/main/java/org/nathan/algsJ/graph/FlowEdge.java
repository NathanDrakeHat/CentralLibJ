package org.nathan.algsJ.graph;

import org.jetbrains.annotations.NotNull;

public class FlowEdge<V> extends BaseEdge<V> {
  final double capacity;
  double flow;

  public FlowEdge(@NotNull V f, @NotNull V l, double capacity, double flow) {
    super(f, l);
    this.capacity = capacity;
    this.flow = flow;
  }

  public double capacity() {
    return capacity;
  }

  public double flow() {
    return flow;
  }

  public double residualCapacityTo(@NotNull V v) {
    if (v == latter) {
      return capacity - flow;
    } else if (v == former) {
      return flow;
    } else {
      throw new IllegalArgumentException("edge dose not contain this vertex.");
    }
  }

  public void addFlowTo(@NotNull V v, double delta) {
    if (v == latter) {
      flow += delta;
    } else if (v == former) {
      flow -= delta;
    } else {
      throw new IllegalArgumentException("edge dose not contain this vertex.");
    }

    if (flow < 0 || flow > capacity) {
      throw new IllegalArgumentException("delta exceeds capacity.");
    }
  }

  @Override
  public String toString() {
    return String.format("FlowEdge[%s--f:%f/c:%f-->%s]", former, flow, capacity, latter);
  }
}
