package org.nathan.algorithmsJava.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.nathan.algorithmsJava.graph.BFS.BFSVertex;

class APShortestPathTest {

    static LinkedGraph<BFSVertex<String>> build() {
        String[] names = "1,2,3,4,5".split(",");
        List<BFSVertex<String>> vertices = new ArrayList<>();
        for (var name : names) {
            vertices.add(new BFSVertex<>(name));
        }
        var res = new LinkedGraph<>(vertices, LinkedGraph.Direction.DIRECTED);
        res.setNeighbor(vertices.get(0), vertices.get(1), 3);
        res.setNeighbor(vertices.get(0), vertices.get(2), 8);
        res.setNeighbor(vertices.get(0), vertices.get(4), -4);

        res.setNeighbor(vertices.get(1), vertices.get(3), 1);
        res.setNeighbor(vertices.get(1), vertices.get(4), 7);

        res.setNeighbor(vertices.get(2), vertices.get(1), 4);

        res.setNeighbor(vertices.get(3), vertices.get(0), 2);
        res.setNeighbor(vertices.get(3), vertices.get(2), -5);

        res.setNeighbor(vertices.get(4), vertices.get(3), 6);
        return res;
    }

    @Test
    void slowAllPairsShortestPaths() {
        var res = APShortestPath.slowAllPairsShortestPaths(new double[][]{
                {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, Double.POSITIVE_INFINITY},
                {3, 0, 4, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                {8, Double.POSITIVE_INFINITY, 0, -5, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 0, 6},
                {-4, 7, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0}
        });
        var answer = new double[][]{
                {0, 3, 7, 2, 8},
                {1, 0, 4, -1, 5},
                {-3, -4, 0, -5, 1},
                {2, 1, 5, 0, 6},
                {-4, -1, 3, -2, 0}
        };
        assertArrayEquals(answer, res);
    }

    @Test
    void fasterAllPairsShortestPaths() {
        var res = APShortestPath.fasterAllPairsShortestPaths(new double[][]{
                {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, Double.POSITIVE_INFINITY},
                {3, 0, 4, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                {8, Double.POSITIVE_INFINITY, 0, -5, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 0, 6},
                {-4, 7, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0}
        });
        var answer = new double[][]{
                {0, 3, 7, 2, 8},
                {1, 0, 4, -1, 5},
                {-3, -4, 0, -5, 1},
                {2, 1, 5, 0, 6},
                {-4, -1, 3, -2, 0}
        };
        assertArrayEquals(answer, res);
    }

    @Test
    void FloydWarshallTest() {
        var res = APShortestPath.FloydWarshall(new double[][]{
                {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, Double.POSITIVE_INFINITY},
                {3, 0, 4, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                {8, Double.POSITIVE_INFINITY, 0, -5, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 0, 6},
                {-4, 7, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0}
        });
        var answer = new double[][]{
                {0, 3, 7, 2, 8},
                {1, 0, 4, -1, 5},
                {-3, -4, 0, -5, 1},
                {2, 1, 5, 0, 6},
                {-4, -1, 3, -2, 0}
        };
        assertArrayEquals(answer, res);
    }

    @Test
    void transitiveClosureTest() {
        var res = APShortestPath.transitiveClosure(new double[][]{
                {0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1},
                {Double.POSITIVE_INFINITY, 0, 1, Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY, 1, 0, 1},
                {Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, 0}
        });

        var answer = new boolean[][]{
                {true, true, true, true},
                {false, true, true, true},
                {false, true, true, true},
                {false, true, true, true}
        };
        assertArrayEquals(answer, res);
    }

    @Test
    void JohnsonTest() {
        var res = APShortestPath.Johnson(build(), SSShortestPath::DijkstraFibonacciHeap);
        var answer = new double[][]{
                {0.0, 1.0, -3.0, 2.0, -4.0},
                {3.0, 0.0, -4.0, 1.0, -1.0},
                {7.0, 4.0, 0.0, 5.0, 3.0},
                {2.0, -1.0, -5.0, 0.0, -2.0},
                {8.0, 5.0, 1.0, 6.0, 0.0},
        };
        assertTrue(res.isPresent());
        assertArrayEquals(answer, res.get());

        res = APShortestPath.Johnson(build(),SSShortestPath::DijkstraFibonacciHeap);
        assertTrue(res.isPresent());
        assertArrayEquals(answer, res.get());

    }
}