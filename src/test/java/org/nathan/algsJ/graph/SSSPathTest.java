package org.nathan.algsJ.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SSSPathTest {

    static BFS.Vert<String> targetBellmanFordCase_s;
    static BFS.Vert<String> targetBellmanFordCase_z;

    static LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> buildBellmanFordCase() {
        String[] names = "s,t,x,y,z".split(",");
        List<BFS.Vert<String>> vertices = new ArrayList<>();
        for (var n : names) {
            vertices.add(new BFS.Vert<>(n));
        }
        LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> res = new LinkGraph<>(true, vertices);
        int[] index1 = new int[]{0, 0, 1, 1, 1, 2, 3, 3, 4, 4};
        int[] index2 = new int[]{1, 3, 2, 3, 4, 1, 2, 4, 0, 2};
        double[] weights = new double[]{6, 7, 5, 8, -4, -2, -3, 9, 2, 7};
        for (int i = 0; i < index1.length; i++) {
            res.addEdge(new WeightEdge<>(vertices.get(index1[i]), vertices.get(index2[i]), weights[i]));
        }
        targetBellmanFordCase_s = vertices.get(0);
        targetBellmanFordCase_z = vertices.get(4);
        return res;
    }

    LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> BellmanGraph = buildBellmanFordCase();

    @Test
    void BellmanFordTest() {

        var b = SSSPath.BellmanFord(BellmanGraph, targetBellmanFordCase_s);
        BFS.Vert<String> target = targetBellmanFordCase_z;
        var vertices = BellmanGraph.allVertices();
        for (var v : vertices) {
            if (v.equals(target)) {
                target = v;
            }
        }
        assertEquals(-2, target.getDistance());
        List<String> res = new ArrayList<>();
        while (target != null) {
            res.add(target.identity());
            target = target.getParent();
        }
        assertTrue(b);
        assertEquals(List.of("z", "t", "x", "y", "s"), res);
    }

    static BFS.Vert<String> targetShortestPathOfDAGForBFS;

    static LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> buildShortestPathOfDAGForBFS() {
        String[] names = "r,s,t,x,y,z".split(",");
        List<BFS.Vert<String>> BFS_vertex = new ArrayList<>();
        for (String name : names) {
            BFS_vertex.add(new BFS.Vert<>(name));
        }


        LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> BFS_G = new LinkGraph<>(true, BFS_vertex);
        int[] index1 = new int[]{0, 0, 1, 1, 2, 2, 2, 3, 3, 4};
        int[] index2 = new int[]{1, 2, 2, 3, 3, 4, 5, 4, 5, 5};
        double[] weights = new double[]{5, 3, 2, 6, 7, 4, 2, -1, 1, -2};
        for (int i = 0; i < index1.length; i++) {
            BFS_G.addEdge(new WeightEdge<>(BFS_vertex.get(index1[i]), BFS_vertex.get(index2[i]), weights[i]));
        }

        targetShortestPathOfDAGForBFS = BFS_vertex.get(1);

        return BFS_G;
    }

    LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> ssDAGGraph = buildShortestPathOfDAGForBFS();

    @Test
    void shortestPathOfDAGTest() {

        SSSPath.ssDAG(ssDAGGraph, targetShortestPathOfDAGForBFS);
        var vertices = ssDAGGraph.allVertices();
        var l = vertices.stream().sorted(Comparator.comparing(BFS.Vert::identity)).collect(Collectors.toList());
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


    static BFS.Vert<String> targetDijkstraFib;


    static LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> buildDijkstraCase(boolean is_fib) {
        String[] names = "s,t,x,y,z".split(",");
        List<BFS.Vert<String>> vertices = new ArrayList<>();
        for (var n : names) {
            vertices.add(new BFS.Vert<>(n));
        }
        LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> graph = new LinkGraph<>(true, vertices);
        int[] indices1 = new int[]{0, 0, 1, 1, 2, 3, 3, 3, 4, 4};
        int[] indices2 = new int[]{1, 3, 2, 3, 4, 1, 2, 4, 0, 2};
        double[] weights = new double[]{10, 5, 1, 2, 4, 3, 9, 2, 7, 6};
        for (int i = 0; i < indices1.length; i++) {
            graph.addEdge(new WeightEdge<>(vertices.get(indices1[i]), vertices.get(indices2[i]), weights[i]));
        }
        if (is_fib) {
            targetDijkstraFib = vertices.get(0);
        } else {
            targetDijkstraMinHeap = vertices.get(0);
        }
        return graph;
    }

    LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> dijkstraFibGraph = buildDijkstraCase(true);

    @Test
    void DijkstraFibonacciHeapTest() {

        SSSPath.DijkstraMinHeap(dijkstraFibGraph, targetDijkstraFib);
        var vertices =
                dijkstraFibGraph.allVertices().stream().sorted(Comparator.comparing(BFS.Vert::identity)).collect(Collectors.toList());
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


    LinkGraph<BFS.Vert<String>, WeightEdge<BFS.Vert<String>>> dijkstraMinHeapGraph = buildDijkstraCase(false);
    static BFS.Vert<String> targetDijkstraMinHeap;

    @Test
    void DijkstraMinHeapTest() {

        SSSPath.DijkstraFibonacciHeap(dijkstraMinHeapGraph, targetDijkstraMinHeap);
        var vertices =
                dijkstraMinHeapGraph.allVertices().stream().sorted(Comparator.comparing(BFS.Vert::identity)).collect(Collectors.toList());
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