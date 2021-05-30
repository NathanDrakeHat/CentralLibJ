package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;

public class GraphEdge<T extends Vertex<?>>{
    @NotNull
    private final T former_vertex;
    @NotNull
    private final T later_vertex;
    private final boolean directed;
    double weight;

    GraphEdge(@NotNull T former, @NotNull T later, double weight, boolean is_directed){
        this.weight = weight;
        former_vertex = former;
        later_vertex = later;
        this.directed = is_directed;
    }

    public T formerVertex(){
        return former_vertex;
    }

    public T laterVertex(){
        return later_vertex;
    }

    public boolean directed(){
        return directed;
    }

    public double weight(){
        return this.weight;
    }

    public T getAnotherSide(T vertex){
        if(vertex.equals(former_vertex)){
            return later_vertex;
        }
        else if(vertex.equals(later_vertex)){
            return former_vertex;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public double getWeight(){
        return weight;
    }

    @Override
    public String toString(){
        if(directed){
            return String.format("[Edge(%s >>> %s)], weight:%f", former_vertex, later_vertex, weight);
        }
        else{
            return String.format("[Edge(%s <-> %s)], weight:%f", former_vertex, later_vertex, weight);
        }
    }
}
