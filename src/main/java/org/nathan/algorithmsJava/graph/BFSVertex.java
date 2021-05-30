package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BFSVertex<V>{
    final V content;
    @Nullable
    BFSVertex<V> parent;
    double distance; // d
    BFS.COLOR color;

    public BFSVertex(@NotNull V name){
        this.content = name;
    }

    BFSVertex(){
        content = null;
    }

    public V getContent(){
        return content;
    }

    public @Nullable BFSVertex<V> getParent(){
        return parent;
    }

    public double getDistance(){
        return distance;
    }

    @Override
    public String toString(){
        return String.format("BFS.Vertex: (%s)", content != null ? content.toString() : "()");
    }
}
