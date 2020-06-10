package tools;

import java.util.*;

public class LinkedGraph<V extends Comparable<V>> {
    Map<V, Set<V>> vertex_map = new TreeMap<>();

    public LinkedGraph(){}
    public LinkedGraph(V[] vertexes) {
        for(var v : vertexes) {
            this.vertex_map.put(v, new TreeSet<>());}
    }
    public LinkedGraph(Set<V> set){ for(var i : set) { vertex_map.put(i, new TreeSet<>()); } }

    public Set<V> getVertexes() { return new TreeSet<>(vertex_map.keySet()); }
    public void putVertex(V v){ vertex_map.put(v, new TreeSet<>()); }
    public boolean hasVertex(V v) { return vertex_map.containsKey(v); }

    public Set<V> getNeighbors(V v){ return new TreeSet<>(vertex_map.get(v)); }
    public void setNeighbors(V v, V[] vertexes){
        Set<V> ns = vertex_map.get(v);
        if(ns != null) {
            ns.clear();
            Collections.addAll(ns, vertexes);
        }else{
            ns = new TreeSet<>();
            Collections.addAll(ns, vertexes);
            vertex_map.put(v, ns);
        }
    }
    public void clearNeighbors(V v){
        var ns = vertex_map.get(v);
        if(ns != null) ns.clear();
    }

    public void putNeighborPair(V v, V n){
        addOneNeighbor(v, n);
        addOneNeighbor(n, v);
    }
    public void addOneNeighbor(V v, V n){
        Set<V> ns = vertex_map.computeIfAbsent(v, k -> new TreeSet<>());
        ns.add(n);
    }
    public boolean haveOneNeighbor(V v, V neighbor) { return vertex_map.get(v).contains(neighbor); }
    public void removeOneNeighbor(V v, V n){ vertex_map.get(v).remove(n); }
    public void removeOneNeighborPair(V v, V n){
        removeOneNeighbor(v, n);
        removeOneNeighbor(n, v);
    }

}
