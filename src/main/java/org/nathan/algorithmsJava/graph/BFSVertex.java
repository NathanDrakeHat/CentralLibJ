package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BFSVertex<V> implements Vertex<V>{
    final V id;
    @Nullable
    BFSVertex<V> parent;
    double distance; // d
    BFS.COLOR color;

    public BFSVertex(@NotNull V name){
        this.id = name;
    }

    BFSVertex(){
        id = null;
    }

    public V getId(){
        return id;
    }

    public @Nullable BFSVertex<V> getParent(){
        return parent;
    }

    public double getDistance(){
        return distance;
    }

    @Override
    public String toString(){
        return String.format("BFS.Vertex: (%s)", id != null ? id.toString() : "()");
    }
}
