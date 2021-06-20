package org.nathan.algsJ.graph;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MaxFlowTest{

  LinkGraph<BaseVert<Character>, FlowEdge<BaseVert<Character>>> FordFulkersonGraph;
  BaseVert<Character> FordFulkersonSource;
  BaseVert<Character> FordFulkersonDestination;
  List<BaseVert<Character>> FordFulkersonVerts;
  {
    String names = "m,n,o,p,q,r,s,t";
    List<BaseVert<Character>> verts =
            Arrays.stream(names.split(",")).map(s->new BaseVert<>(s.charAt(0))).collect(Collectors.toList());
    LinkGraph<BaseVert<Character>, FlowEdge<BaseVert<Character>>> g = new LinkGraph<>(false, verts);
    int[] s = new int[]{6, 0, 0, 0, 1, 1, 3, 5, 3, 5, 4, 2, 6, 6, 2};
    int[] t = new int[]{0, 2, 3, 1, 3, 7, 7, 7, 5, 2, 5, 4, 4, 2, 3};
    int[] c = new int[]{10,4, 15,9, 15,10,10,10,15,6, 16,4, 15,5, 8};
    for(int idx = 0; idx < s.length; idx++){
      g.addEdge(new FlowEdge<>(verts.get(s[idx]), verts.get(t[idx]), c[idx], 0));
    }
    FordFulkersonGraph = g;
    FordFulkersonSource = verts.get(6);
    FordFulkersonDestination = verts.get(7);
    FordFulkersonVerts = verts;
  }
  @Test
  public void FordFulkersonTest(){
    var solver = new MaxFlow.ForFulkersonSolver<>(FordFulkersonGraph);
    solver.solve(FordFulkersonSource, FordFulkersonDestination);
    assertEquals(28, solver.maxFlow());
    for(int i = 0; i < FordFulkersonVerts.size(); i++){
      var vert = FordFulkersonVerts.get(i);
      if(i != 2 && i != 4 && i !=5 && i!= 6){
        assertFalse(solver.inMinCut(vert));
      }
      else {
        assertTrue(solver.inMinCut(vert));
      }
    }
  }
}
