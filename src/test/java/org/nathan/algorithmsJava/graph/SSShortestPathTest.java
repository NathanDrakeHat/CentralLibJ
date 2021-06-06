package org.nathan.algorithmsJava.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SSShortestPathTest{

  static BFSVertex<String> targetBellmanFordCase_s;
  static BFSVertex<String> targetBellmanFordCase_z;

  static LinkedGraph<BFSVertex<String>> buildBellmanFordCase(){
    String[] names = "s,t,x,y,z".split(",");
    List<BFSVertex<String>> vertices = new ArrayList<>();
    for(var n : names){
      vertices.add(new BFSVertex<>(n));
    }
    var res = new LinkedGraph<>(vertices, true);
    int[] index1 = new int[]{0, 0, 1, 1, 1, 2, 3, 3, 4, 4};
    int[] index2 = new int[]{1, 3, 2, 3, 4, 1, 2, 4, 0, 2};
    double[] weights = new double[]{6, 7, 5, 8, -4, -2, -3, 9, 2, 7};
    for(int i = 0; i < index1.length; i++){
      res.setNeighbor(vertices.get(index1[i]), vertices.get(index2[i]), weights[i]);
    }
    targetBellmanFordCase_s = vertices.get(0);
    targetBellmanFordCase_z = vertices.get(4);
    return res;
  }

  LinkedGraph<BFSVertex<String>> BellmanGraph = buildBellmanFordCase();

  @Test
  void BellmanFordTest(){

    var b = SSShortestPath.BellmanFord(BellmanGraph, targetBellmanFordCase_s);
    BFSVertex<String> target = targetBellmanFordCase_z;
    var vertices = BellmanGraph.allVertices();
    for(var v : vertices){
      if(v.equals(target)){
        target = v;
      }
    }
    assertEquals(-2, target.getDistance());
    List<String> res = new ArrayList<>();
    while(target != null) {
      res.add(target.getId());
      target = target.getParent();
    }
    assertTrue(b);
    assertEquals(List.of("z", "t", "x", "y", "s"), res);
  }

  static BFSVertex<String> targetShortestPathOfDAGForBFS;

  static LinkedGraph<BFSVertex<String>> buildShortestPathOfDAGForBFS(){
    String[] names = "r,s,t,x,y,z".split(",");
    List<BFSVertex<String>> BFS_vertex = new ArrayList<>();
    for(String name : names){
      BFS_vertex.add(new BFSVertex<>(name));
    }


    var BFS_G = new LinkedGraph<>(BFS_vertex, true);
    int[] index1 = new int[]{0, 0, 1, 1, 2, 2, 2, 3, 3, 4};
    int[] index2 = new int[]{1, 2, 2, 3, 3, 4, 5, 4, 5, 5};
    double[] weights = new double[]{5, 3, 2, 6, 7, 4, 2, -1, 1, -2};
    for(int i = 0; i < index1.length; i++){
      BFS_G.setNeighbor(BFS_vertex.get(index1[i]), BFS_vertex.get(index2[i]), weights[i]);
    }

    targetShortestPathOfDAGForBFS = BFS_vertex.get(1);

    return BFS_G;
  }

  LinkedGraph<BFSVertex<String>> ssDAGGraph = buildShortestPathOfDAGForBFS();

  @Test
  void shortestPathOfDAGTest(){

    SSShortestPath.ssDAG(ssDAGGraph, targetShortestPathOfDAGForBFS);
    var vertices = ssDAGGraph.allVertices();
    var l = vertices.stream().sorted(Comparator.comparing(BFSVertex::getId)).collect(Collectors.toList());
    assertNull(l.get(0).getParent());
    assertNull(l.get(1).getParent());
    assertEquals(l.get(1), l.get(2).getParent());
    assertEquals(2, l.get(2).getDistance());

    assertEquals(l.get(1), l.get(3).getParent());
    assertEquals(6, l.get(3).getDistance());

    assertEquals(l.get(3), l.get(4).getParent());
    assertEquals(5, l.get(4).getDistance());

    assertEquals(l.get(4), l.get(5).getParent());
    assertEquals(3, l.get(5).getDistance());
  }


  static BFSVertex<String> targetDijkstraFib;


  static LinkedGraph<BFSVertex<String>> buildDijkstraCase(boolean is_fib){
    String[] names = "s,t,x,y,z".split(",");
    List<BFSVertex<String>> vertices = new ArrayList<>();
    for(var n : names){
      vertices.add(new BFSVertex<>(n));
    }
    var graph = new LinkedGraph<>(vertices, true);
    int[] indices1 = new int[]{0, 0, 1, 1, 2, 3, 3, 3, 4, 4};
    int[] indices2 = new int[]{1, 3, 2, 3, 4, 1, 2, 4, 0, 2};
    double[] weights = new double[]{10, 5, 1, 2, 4, 3, 9, 2, 7, 6};
    for(int i = 0; i < indices1.length; i++){
      graph.setNeighbor(vertices.get(indices1[i]), vertices.get(indices2[i]), weights[i]);
    }
    if(is_fib){
      targetDijkstraFib = vertices.get(0);
    }
    else{
      targetDijkstraMinHeap = vertices.get(0);
    }
    return graph;
  }

  LinkedGraph<BFSVertex<String>> dijkstraFibGraph = buildDijkstraCase(true);

  @Test
  void DijkstraFibonacciHeapTest(){

    SSShortestPath.DijkstraMinHeap(dijkstraFibGraph, targetDijkstraFib);
    var vertices =
            dijkstraFibGraph.allVertices().stream().sorted(Comparator.comparing(BFSVertex::getId)).collect(Collectors.toList());
    assertNull(vertices.get(0).getParent());

    assertEquals(vertices.get(3), vertices.get(1).getParent());
    assertEquals(8, vertices.get(1).getDistance());

    assertEquals(vertices.get(1), vertices.get(2).getParent());
    assertEquals(9, vertices.get(2).getDistance());

    assertEquals(vertices.get(0), vertices.get(3).getParent());
    assertEquals(5, vertices.get(3).getDistance());

    assertEquals(vertices.get(3), vertices.get(4).getParent());
    assertEquals(7, vertices.get(4).getDistance());
  }


  LinkedGraph<BFSVertex<String>> dijkstraMinHeapGraph = buildDijkstraCase(false);
  static BFSVertex<String> targetDijkstraMinHeap;

  @Test
  void DijkstraMinHeapTest(){

    SSShortestPath.DijkstraFibonacciHeap(dijkstraMinHeapGraph, targetDijkstraMinHeap);
    var vertices =
            dijkstraMinHeapGraph.allVertices().stream().sorted(Comparator.comparing(BFSVertex::getId)).collect(Collectors.toList());
    assertNull(vertices.get(0).getParent());

    assertEquals(vertices.get(3), vertices.get(1).getParent());
    assertEquals(8, vertices.get(1).getDistance());

    assertEquals(vertices.get(1), vertices.get(2).getParent());
    assertEquals(9, vertices.get(2).getDistance());

    assertEquals(vertices.get(0), vertices.get(3).getParent());
    assertEquals(5, vertices.get(3).getDistance());

    assertEquals(vertices.get(3), vertices.get(4).getParent());
    assertEquals(7, vertices.get(4).getDistance());
  }
}