package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;
import org.nathan.algorithmsJava.structures.DisjointSet;

public final class KruskalVertex<V> extends DisjointSet{
    @NotNull
    private final V content;

    public KruskalVertex(@NotNull V n){
        content = n;
    }

    @SuppressWarnings("unused")
    public @NotNull V getContent(){
        return content;
    }

    @Override
    public String toString(){
        return String.format("KruskalVertex: %s", content);
    }

}
