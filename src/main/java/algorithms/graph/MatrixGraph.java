package algorithms.graph;

import java.util.*;

public class MatrixGraph<V> {
    public enum Direction{
        DIRECTED, NON_DIRECTED
    }
//    private final List<V> vertices = new ArrayList<>();
    private final Map<V, Integer> vertex_index_map = new HashMap<>();
    private final Direction graph_direction;
    private final double[][] weight_matrix;
    private int size;

    public MatrixGraph(List<V> vertices, Direction direction){
        Objects.requireNonNull(direction);
        Objects.requireNonNull(vertices);
//        this.vertices.addAll(vertices);
        graph_direction = direction;
        size = 0;
        for(var v : vertices){
            Objects.requireNonNull(v);
            vertex_index_map.put(v,size++);
        }
        weight_matrix = new double[size][size];
    }

    public void setWeightMatrix(double[][] w){
        for(int i = 0; i < size; i++){
            System.arraycopy(w[i],0,weight_matrix[i],0,size);
        }
    }

    public void setWeight(V row, V col, double weight){
        Objects.requireNonNull(row);
        Objects.requireNonNull(col);
        var row_idx = vertex_index_map.get(row);
        var col_idx = vertex_index_map.get(col);
        setWeight(row_idx,col_idx,weight);
        if(graph_direction == Direction.NON_DIRECTED){
            setWeight(col_idx,row_idx,weight);
        }
    }

    public void setWeight(int r, int c, double weight){ weight_matrix[r][c] = weight; }

    public int getVerticesCount(){ return size; }

    public double getWeightAt(int r, int c) { return weight_matrix[r][c]; }
}
