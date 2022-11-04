package dev.qhc99.centralibj.algsJ.graph;


public class BaseEdge<V>{
  
  final V vert_from;
  
  final V vert_to;

  public BaseEdge( V f,  V l){
    vert_from = f;
    vert_to = l;
  }

  public  V from(){
    return vert_from;
  }

  public  V to(){
    return vert_to;
  }

  public  V another(V vertex){
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
