package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;

public class PrimVertex<V>{
    @NotNull
    private final V content;
    PrimVertex<V> parent;
    double key = 0;

    public PrimVertex(@NotNull V name){
        this.content = name;
    }

    public @NotNull V getContent(){
        return content;
    }

    public double getKey(){
        return key;
    }

    @Override
    public String toString(){
        return String.format("PrimVertex: (%s)", content);
    }
}
