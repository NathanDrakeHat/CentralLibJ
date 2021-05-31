package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BFSVertex<ID> implements Vertex<ID>{
    final ID id;
    @Nullable
    BFSVertex<ID> parent;
    double distance; // d
    BFS.COLOR color;

    BFSVertex(@NotNull ID name){
        this.id = name;
    }

    BFSVertex(){
        id = null;
    }

    public static <S_ID> BFSVertex<S_ID> make(S_ID id){
        return new BFSVertex<>(id);
    }

    public ID getId(){
        return id;
    }

    public @Nullable BFSVertex<ID> getParent(){
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
