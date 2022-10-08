package dev.qhc99.centralibj.algsJ.graph;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BFSTest{


  List<BFS.Vert<Character>> vertices;
  LinkedGraph<BFS.Vert<Character>, BaseEdge<BFS.Vert<Character>>> graph;

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

    public static List<BFS.Vert<Character>> makeVertexes(){
      List<BFS.Vert<Character>> vs = new ArrayList<>(8);
      for(int i = 0; i < 8; i++){
        vs.add(i, new BFS.Vert<>(names.charAt(i)));
      }
      return vs;
    }

    public static LinkedGraph<BFS.Vert<Character>, BaseEdge<BFS.Vert<Character>>> makeGraph(List<BFS.Vert<Character>> vs){
      var c = BFS.Vert.class;
      LinkedGraph<BFS.Vert<Character>, BaseEdge<BFS.Vert<Character>>> G = new LinkedGraph<>(false, vs);
      G.addEdge(new BaseEdge<>(vs.get(0), vs.get(1)));
      G.addEdge(new BaseEdge<>(vs.get(0), vs.get(4)));

      G.addEdge(new BaseEdge<>(vs.get(1), vs.get(5)));

      G.addEdge(new BaseEdge<>(vs.get(2), vs.get(3)));
      G.addEdge(new BaseEdge<>(vs.get(2), vs.get(5)));
      G.addEdge(new BaseEdge<>(vs.get(2), vs.get(6)));

      G.addEdge(new BaseEdge<>(vs.get(3), vs.get(6)));

      G.addEdge(new BaseEdge<>(vs.get(5), vs.get(6)));

      G.addEdge(new BaseEdge<>(vs.get(6), vs.get(7)));

      return G;
    }
  }
}