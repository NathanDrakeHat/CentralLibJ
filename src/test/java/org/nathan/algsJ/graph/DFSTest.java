package org.nathan.algsJ.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {


  static LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> makeStronglyConnectedComponentsDemo() {
    String t = "a,b,c,d,e,f,g,h";
    var names = t.split(",");
    var A = new ArrayList<DFS.Vert<String>>(names.length);
    for (int i = 0; i < names.length; i++) {
      A.add(i, new DFS.Vert<>(names[i]));
    }
    LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> G = new LinkGraph<>(true, A);
    G.addEdge(new BaseEdge<>(A.get(0), A.get(1)));

    G.addEdge(new BaseEdge<>(A.get(1), A.get(2)));
    G.addEdge(new BaseEdge<>(A.get(1), A.get(4)));
    G.addEdge(new BaseEdge<>(A.get(1), A.get(5)));

    G.addEdge(new BaseEdge<>(A.get(2), A.get(3)));
    G.addEdge(new BaseEdge<>(A.get(2), A.get(6)));

    G.addEdge(new BaseEdge<>(A.get(3), A.get(2)));
    G.addEdge(new BaseEdge<>(A.get(3), A.get(7)));

    G.addEdge(new BaseEdge<>(A.get(4), A.get(0)));
    G.addEdge(new BaseEdge<>(A.get(4), A.get(5)));

    G.addEdge(new BaseEdge<>(A.get(5), A.get(6)));

    G.addEdge(new BaseEdge<>(A.get(6), A.get(5)));
    G.addEdge(new BaseEdge<>(A.get(6), A.get(7)));

    G.addEdge(new BaseEdge<>(A.get(7), A.get(7)));

    return G;
  }

  static LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> makeGraph() {
    String names = "uvwxyz";
    var vs = new ArrayList<DFS.Vert<String>>(6);

    for (int i = 0; i < 6; i++) {
      vs.add(i, new DFS.Vert<>(String.valueOf(names.charAt(i))));
    }
    var G = new LinkGraph<>(true, vs);
    G.addEdge(new BaseEdge<>(vs.get(0), vs.get(1)));
    G.addEdge(new BaseEdge<>(vs.get(0), vs.get(3)));

    G.addEdge(new BaseEdge<>(vs.get(1), vs.get(4)));

    G.addEdge(new BaseEdge<>(vs.get(2), vs.get(4)));
    G.addEdge(new BaseEdge<>(vs.get(2), vs.get(5)));

    G.addEdge(new BaseEdge<>(vs.get(3), vs.get(1)));

    G.addEdge(new BaseEdge<>(vs.get(4), vs.get(3)));

    G.addEdge(new BaseEdge<>(vs.get(5), vs.get(5)));

    return G;
  }

  LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> dfsGraph = makeGraph();

  @Test
  void depthFirstSearchTest() {
    DFS.depthFirstSearch(dfsGraph);
    var vertices = dfsGraph.allVertices();
    List<DFS.Vert<String>> l = new ArrayList<>(vertices);
    l.sort(Comparator.comparing(DFS.Vert::identity));
    assertEquals(1, l.get(0).discover);
    assertEquals(8, l.get(0).finish);

    if (2 == l.get(1).discover) {
      assertEquals(7, l.get(1).finish);
      if (l.get(3).discover == 4) {
        assertEquals(5, l.get(3).finish);
        assertEquals(3, l.get(4).discover);
        assertEquals(6, l.get(4).finish);
      } else {
        assertEquals(3, l.get(3).discover);
        assertEquals(6, l.get(3).finish);
        assertEquals(4, l.get(4).discover);
        assertEquals(5, l.get(4).finish);
      }
    } else {
      assertEquals(2, l.get(3).discover);
      assertEquals(7, l.get(3).finish);
      if (3 == l.get(4).discover) {
        assertEquals(6, l.get(4).finish);
        assertEquals(4, l.get(1).discover);
        assertEquals(5, l.get(1).finish);
      } else {
        assertEquals(4, l.get(4).discover);
        assertEquals(5, l.get(4).finish);
        assertEquals(3, l.get(1).discover);
        assertEquals(6, l.get(1).finish);
      }

    }

    if (l.get(2).discover == 9) {
      assertEquals(12, l.get(2).finish);
      assertEquals(10, l.get(5).discover);
      assertEquals(11, l.get(5).finish);
    } else {
      assertEquals(11, l.get(2).discover);
      assertEquals(12, l.get(2).finish);
      assertEquals(9, l.get(5).discover);
      assertEquals(10, l.get(5).finish);
    }


  }

  static LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> makeTopographicalDemo() {
    var A = new ArrayList<DFS.Vert<String>>(9);
    String t = "undershorts,pants,belt,shirt,tie,jacket,socks,shoes,watch";
    var names = t.split(",");
    for (int i = 0; i < 9; i++) {
      A.add(i, new DFS.Vert<>(names[i]));
    }
    LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> G = new LinkGraph<>(true, A);
    G.addEdge(new BaseEdge<>(A.get(0), A.get(1)));
    G.addEdge(new BaseEdge<>(A.get(0), A.get(6)));

    G.addEdge(new BaseEdge<>(A.get(1), A.get(2)));
    G.addEdge(new BaseEdge<>(A.get(1), A.get(6)));

    G.addEdge(new BaseEdge<>(A.get(2), A.get(5)));

    G.addEdge(new BaseEdge<>(A.get(3), A.get(2)));
    G.addEdge(new BaseEdge<>(A.get(3), A.get(4)));

    G.addEdge(new BaseEdge<>(A.get(4), A.get(5)));

    G.addEdge(new BaseEdge<>(A.get(6), A.get(7)));

    return G;
  }


  LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> topologicalGraph = makeTopographicalDemo();

  @Test
  void topologicalSortTest() {

    var l = DFS.topologicalSort(topologicalGraph);
    boolean flag = true;
    for (int i = 1; i < l.size(); i++) {
      for (int j = 0; j < i; j++) {
        flag = topologicalSorted(l.get(j), l.get(i), topologicalGraph);
        if (!flag) {
          break;
        }
      }
      if (!flag) {
        break;
      }
    }
    assertTrue(flag);
  }

  boolean topologicalSorted(
          DFS.Vert<String> target,
          DFS.Vert<String> current,
          LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> G) {
    if (current.equals(target)) {
      return false;
    }
    var edges = G.adjacentEdgesOf(current);
    if (edges.isEmpty()) {
      return true;
    } else {
      boolean t = true;
      for (var edge : edges) {
        var i = edge.another(current);
        t = topologicalSorted(target, i, G);
        if (!t) {
          break;
        }
      }
      return t;
    }
  }

  LinkGraph<DFS.Vert<String>, BaseEdge<DFS.Vert<String>>> sortedTestGraph = makeTopographicalDemo();

  @Test
  void topologicalSortedTest() {

    boolean flag = true;
    List<DFS.Vert<String>> t = new ArrayList<>(sortedTestGraph.allVertices());
    for (int i = 1; i < t.size(); i++) {
      for (int j = 0; j < i; j++) {
        flag = topologicalSorted(t.get(j), t.get(i), sortedTestGraph);
        if (!flag) {
          break;
        }
      }
      if (!flag) {
        break;
      }
    }
    assertFalse(flag);
  }

  @Test
  void stronglyConnectedComponentsTest() {
    var G = makeStronglyConnectedComponentsDemo();
    DFS.stronglyConnectedComponents(G);
    var vertices = G.allVertices();
    List<DFS.Vert<String>> vs = new ArrayList<>(vertices);
    assertTrue((getRoot(vs.get(0)) == getRoot(vs.get(1))) & (getRoot(vs.get(1)) == getRoot(vs.get(4))));
    assertSame(getRoot(vs.get(2)), getRoot(vs.get(3)));
    assertSame(getRoot(vs.get(5)), getRoot(vs.get(6)));
    assertSame(getRoot(vs.get(7)), vs.get(7));

  }

  DFS.Vert<String> getRoot(DFS.Vert<String> v) {
    var t = v;
    while (t.parent != null) {
      t = t.parent;
    }
    return t;
  }
}