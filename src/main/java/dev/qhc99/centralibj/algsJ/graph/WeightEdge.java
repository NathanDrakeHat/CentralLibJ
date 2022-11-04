package dev.qhc99.centralibj.algsJ.graph;


public class WeightEdge<V> extends BaseEdge<V>{

  double weight;

  public WeightEdge(V f, V l, double weight){
    super(f, l);
    this.weight = weight;
  }

  public double weight(){
    return weight;
  }
}
