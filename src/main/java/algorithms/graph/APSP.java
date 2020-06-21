package algorithms.graph;

// all pair shortest path
public class APSP {
    private static <V> double[][] extendedShortestPath(double[][] L, MatrixGraph<V> W){
        var n = W.getVerticesCount();
        var L_ = new double[n][n];
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                L_[i][j] = Double.POSITIVE_INFINITY;
                for(int k = 1; k <= n; k++){
                    L_[i][j] = Math.max(L_[i][j],L[i][k] + W.getWeightAt(k,j));
                }
            }
        }
        return L_;
    }
}
