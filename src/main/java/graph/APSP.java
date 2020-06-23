package graph;

import java.util.Collection;

// all pair shortest path
public class APSP {
    private static double[][] extendedShortestPath(double[][] L_origin, double[][] W){
        var n = W.length;
        var L_next = new double[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                L_next[i][j] = Double.POSITIVE_INFINITY;
                for(int k = 0; k < n; k++){
                    L_next[i][j] = Math.min(L_next[i][j],L_origin[i][k] + W[k][j]);
                }
            }
        }
        return L_next;
    }
    public static double[][] slowAllPairsShortestPaths(double[][] W){
        var n = W.length;
        var L = W;
        for(int m = 2; m <= n-1; m++){
            L = extendedShortestPath(L, W);
        }
        // L^(n-1)
        return L;
    }

    private static double[][] squareMatrixMultiply(double[][] A, double[][] B){
        var n = A.length;
        double[][] C = new double[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                C[i][j] = 0;
                for(int k = 0; k < n; k++){
                    C[i][j] += A[i][k] + B[k][j];
                }
            }
        }
        return C;
    }
    public static double[][] fasterAllPairsShortestPaths(double[][] W){
        var n = W.length;
        var L = W;
        int m = 1;
        for(;m < n - 1; m *= 2){
            L = extendedShortestPath(L, L);
        }
        return L;
    }

    // no negative-weight cycles
    public static double[][] algorithmFloydWarshall(double[][] W){
        var n = W.length;
        var D_origin = W;
        for(int k = 0; k < n; k++){
            var D_current = new double[n][n];
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    D_current[i][j] = Math.min(D_origin[i][j], D_origin[i][k]+D_origin[k][j]);
                }
            }
            D_origin = D_current;
        }
        return D_origin;
    }

    public static boolean[][] transitiveClosure(double[][] W){
        var n = W.length;
        var T = new boolean[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                T[i][j] = (i == j) || (W[i][j] != Double.POSITIVE_INFINITY);
            }
        }
        for(int k = 0; k < n; k++){
            var T_k = new boolean[n][n];
            for(int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    T_k[i][j] = T[i][j] || (T[i][k] && T[k][j]);
                }
            }
            T = T_k;
        }
        return T;
    }

    // O(V^2*lgV + V*E)
    public static <T>
    double[][] algorithmJohnson(Graph<BFS.BFSVertex<T>> graph, BFS.BFSVertex<T> s) throws NegativeCyclesException {
        var vertices = graph.getAllVertices();
        vertices.add(s);
        var edges = graph.getAllEdges();
        var new_graph = buildGraph(graph,s,vertices,edges);
        if(!SSSP.algorithmBellmanFord(new_graph, s))
            throw new NegativeCyclesException();
        else{
            for(var vertex : vertices){

            }


            return null;
        }
    }
    private static <T> Graph<BFS.BFSVertex<T>> buildGraph(Graph<BFS.BFSVertex<T>> graph,
                                                          BFS.BFSVertex<T> s,
                                                          Collection<BFS.BFSVertex<T>> vertices,
                                                          Collection<Graph.Edge<BFS.BFSVertex<T>>> edges){
        var new_graph = new Graph<>(vertices, Graph.Direction.DIRECTED);

        for(var edge : edges)
            new_graph.setNeighbor(edge.getFormerVertex(),edge.getLaterVertex(),edge.getWeight());
        for(var vertex : vertices){
            if(vertex != s) {
                new_graph.setNeighbor(s, vertex, 0);
                edges.add(new Graph.Edge<>(s,vertex,0, Graph.Direction.DIRECTED));
            }
        }
        return new_graph;
    }

    public static class NegativeCyclesException extends Exception{

        public NegativeCyclesException(){}
        public NegativeCyclesException(String s){
            super(s);
        }
    }
}
