package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * depth first search
 */
public final class DFS {
    public static <T> void depthFirstSearch(@NotNull LinkedGraph<DFSVertex<T>> G) {
        var vertices = G.getAllVertices();
        vertices.parallelStream().forEach(v->{
            v.color = COLOR.WHITE;
            v.parent = null;
        });
        int time = 0;
        for (var v : vertices) {
            if (v.color == COLOR.WHITE) {
                time = DFSVisit(G, v, time);
            }
        }
    }

    private static <T> int DFSVisit(LinkedGraph<DFSVertex<T>> G, DFSVertex<T> u, int time) {
        time++;
        u.discover = time;
        u.color = COLOR.GRAY;
        var u_edges = G.getEdgesAt(u);
        for (var edge : u_edges) {
            var v = edge.getAnotherSide(u);
            if (v.color == COLOR.WHITE) {
                v.parent = u;
                time = DFSVisit(G, v, time);
            }
        }
        u.color = COLOR.BLACK;
        time++;
        u.finish = time;
        return time;
    }

    public static <T> List<DFSVertex<T>> topologicalSort(@NotNull LinkedGraph<DFSVertex<T>> G) {
        depthFirstSearch(G);
        List<DFSVertex<T>> l = new ArrayList<>(G.getAllVertices());
        l.sort((o1, o2) -> o2.finish - o1.finish); // descend order
        return l;
    }

    public static <T> void stronglyConnectedComponents(@NotNull LinkedGraph<DFSVertex<T>> G) {
        var l = topologicalSort(G);
        var G_T = transposeGraph(G);
        depthFirstSearchOrderly(G_T, l);
    }

    private static <T> void depthFirstSearchOrderly(LinkedGraph<DFSVertex<T>> G, List<DFSVertex<T>> order) {
        var vertices = G.getAllVertices();
        for (var v : vertices) {
            v.color = COLOR.WHITE;
            v.parent = null;
        }
        int time = 0;
        for (var v : order) {
            if (v.color == COLOR.WHITE) {
                time = DFSVisit(G, v, time);
            }
        }
    }

    private static <T> LinkedGraph<DFSVertex<T>> transposeGraph(LinkedGraph<DFSVertex<T>> graph) {
        var new_graph = new LinkedGraph<>(graph.getAllVertices(), true);
        var vertices = graph.getAllVertices();
        for (var v : vertices) {
            var edges = graph.getEdgesAt(v);
            for (var edge : edges) {
                var n = edge.getAnotherSide(v);
                new_graph.setNeighbor(n, v);
            }
        }
        return new_graph;
    }

    enum COLOR {WHITE, GRAY, BLACK}

}