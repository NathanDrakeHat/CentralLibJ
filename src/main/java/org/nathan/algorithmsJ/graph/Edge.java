package org.nathan.algorithmsJ.graph;

public interface Edge<V extends Vertex<?>>{

  V former();

  V later();

  default V another(V vertex){
    if(vertex.equals(former())){
      return later();
    }
    else if(vertex.equals(later())){
      return former();
    }
    else{
      throw new IllegalArgumentException();
    }
  }
}
