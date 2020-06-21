package graph;

// all pair shortest path
public class APSP {
    private static <V> double[][] extendedShortestPath(double[][] L_origin, double[][] W){
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
    public static <V> double[][] slowAllPairsShortestPaths(double[][] W){
        var n = W.length;
        var L = W;
        for(int m = 2; m <= n-1; m++){
            L = extendedShortestPath(L, W);
        }
        // L^(n-1)
        return L;
    }

    private static <V> double[][] squareMatrixMultiply(double[][] A, double[][] B){
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
    public static <V> double[][] fasterAllPairsShortestPaths(double[][] W){
        var n = W.length;
        var L = W;
        int m = 1;
        for(;m < n - 1; m *= 2){
            L = extendedShortestPath(L, L);
        }
        return L;
    }
}
