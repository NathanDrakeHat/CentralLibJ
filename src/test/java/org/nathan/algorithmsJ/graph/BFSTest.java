package org.nathan.algorithmsJ.graph;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BFSTest{


  List<BFSVert<Character>> vertices;
  LinkGraph<BFSVert<Character>> graph;

  {
    vertices = Data.makeVertexes();
    graph = Data.makeGraph(vertices);
  }

  @Test
  void breathFirstSearch(){
    BFS.breathFirstSearch(graph, vertices.get(1));
    assertEquals(List.of('s', 'w', 'x', 'y'), BFS.getPath(vertices.get(1), vertices.get(7)));
  }

  public static class Data{
    public static String names = "rstuvwxy";

    public static List<BFSVert<Character>> makeVertexes(){
      List<BFSVert<Character>> vs = new ArrayList<>(8);
      for(int i = 0; i < 8; i++){
        vs.add(i, new BFSVert<>(names.charAt(i)));
      }
      return vs;
    }

    public static LinkGraph<BFSVert<Character>> makeGraph(List<BFSVert<Character>> vs){
      var G = new LinkGraph<>(vs, false);
      G.setNeighbor(vs.get(0), vs.get(1));
      G.setNeighbor(vs.get(0), vs.get(4));

      G.setNeighbor(vs.get(1), vs.get(5));

      G.setNeighbor(vs.get(2), vs.get(3));
      G.setNeighbor(vs.get(2), vs.get(5));
      G.setNeighbor(vs.get(2), vs.get(6));

      G.setNeighbor(vs.get(3), vs.get(6));

      G.setNeighbor(vs.get(5), vs.get(6));

      G.setNeighbor(vs.get(6), vs.get(7));

      return G;
    }
  }
}