package org.qhc99.centralibj.algsJ.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class APSPathTest{

  static LinkedGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> buildJohnsonTestCase(){
    String[] names = "1,2,3,4,5".split(",");
    List<BFS.Vert<String>> vertices = new ArrayList<>();
    for(var name : names){
      vertices.add(new BFS.Vert<>(name));
    }
    LinkedGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> res = new LinkedGraph<>(true, vertices);
    res.addEdge(new WeightEdge<>(vertices.get(0), vertices.get(1), 3));
    res.addEdge(new WeightEdge<>(vertices.get(0), vertices.get(2), 8));
    res.addEdge(new WeightEdge<>(vertices.get(0), vertices.get(4), -4));

    res.addEdge(new WeightEdge<>(vertices.get(1), vertices.get(3), 1));
    res.addEdge(new WeightEdge<>(vertices.get(1), vertices.get(4), 7));

    res.addEdge(new WeightEdge<>(vertices.get(2), vertices.get(1), 4));

    res.addEdge(new WeightEdge<>(vertices.get(3), vertices.get(0), 2));
    res.addEdge(new WeightEdge<>(vertices.get(3), vertices.get(2), -5));

    res.addEdge(new WeightEdge<>(vertices.get(4), vertices.get(3), 6));
    return res;
  }


  double[][] slowAllPairsSSAnswer = new double[][]{
          {0, 3, 7, 2, 8},
          {1, 0, 4, -1, 5},
          {-3, -4, 0, -5, 1},
          {2, 1, 5, 0, 6},
          {-4, -1, 3, -2, 0}
  };


  @Test
  void slowAllPairsShortestPaths(){
    var res = APSPath.slowAllPairsShortestPaths(new double[][]{
            {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, Double.POSITIVE_INFINITY},
            {3, 0, 4, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
            {8, Double.POSITIVE_INFINITY, 0, -5, Double.POSITIVE_INFINITY},
            {Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 0, 6},
            {-4, 7, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0}
    });

    assertArrayEquals(slowAllPairsSSAnswer, res);
  }


  double[][] fastAllPairsSSAnswer = new double[][]{
          {0, 3, 7, 2, 8},
          {1, 0, 4, -1, 5},
          {-3, -4, 0, -5, 1},
          {2, 1, 5, 0, 6},
          {-4, -1, 3, -2, 0}
  };

  @Test
  void fasterAllPairsShortestPaths(){
    var res = APSPath.fasterAllPairsShortestPaths(new double[][]{
            {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, Double.POSITIVE_INFINITY},
            {3, 0, 4, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
            {8, Double.POSITIVE_INFINITY, 0, -5, Double.POSITIVE_INFINITY},
            {Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 0, 6},
            {-4, 7, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0}
    });
    assertArrayEquals(fastAllPairsSSAnswer, res);
  }


  double[][] FloydWarshallAnswer = new double[][]{
          {0, 3, 7, 2, 8},
          {1, 0, 4, -1, 5},
          {-3, -4, 0, -5, 1},
          {2, 1, 5, 0, 6},
          {-4, -1, 3, -2, 0}
  };

  @Test
  void FloydWarshallTest(){
    var res = APSPath.FloydWarshall(new double[][]{
            {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, Double.POSITIVE_INFINITY},
            {3, 0, 4, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
            {8, Double.POSITIVE_INFINITY, 0, -5, Double.POSITIVE_INFINITY},
            {Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 0, 6},
            {-4, 7, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0}
    });

    assertArrayEquals(FloydWarshallAnswer, res);
  }

  boolean[][] transitiveClosureAnswer = new boolean[][]{
          {true, true, true, true},
          {false, true, true, true},
          {false, true, true, true},
          {false, true, true, true}
  };

  @Test
  void transitiveClosureTest(){
    var res = APSPath.transitiveClosure(new double[][]{
            {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1},
            {Double.POSITIVE_INFINITY, 0, 1, Double.POSITIVE_INFINITY},
            {Double.POSITIVE_INFINITY, 1, 0, 1},
            {Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 0}
    });
    assertArrayEquals(transitiveClosureAnswer, res);
  }

  LinkedGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> JohnsonFibTest = buildJohnsonTestCase();
  LinkedGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> JohnsonMinHeapTest = buildJohnsonTestCase();
  double[][] JohnsonAnswer = new double[][]{
          {0.0, 1.0, -3.0, 2.0, -4.0},
          {3.0, 0.0, -4.0, 1.0, -1.0},
          {7.0, 4.0, 0.0, 5.0, 3.0},
          {2.0, -1.0, -5.0, 0.0, -2.0},
          {8.0, 5.0, 1.0, 6.0, 0.0},
  };

  @Test
  void JohnsonFibonacciHeapTest(){
    var res = APSPath.Johnson(JohnsonFibTest, SSSPath::DijkstraFibonacciHeap);
    assertTrue(res.isPresent());
    assertArrayEquals(JohnsonAnswer, res.get());
  }

  @Test
  void JohnsonMinHeapTest(){
    var res = APSPath.Johnson(JohnsonMinHeapTest, SSSPath::DijkstraMinHeap);
    assertTrue(res.isPresent());
    assertArrayEquals(JohnsonAnswer, res.get());
  }
}