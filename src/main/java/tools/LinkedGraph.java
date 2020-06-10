package tools;

import java.util.*;

public class LinkedGraph<V extends Comparable<V>> {
    Map<V, List<V>> vertex_map = new TreeMap<>();

    public LinkedGraph(){}
    public LinkedGraph(V[] vertexes) { for(var v : vertexes) { this.vertex_map.put(v, new ArrayList<>());} }
    public LinkedGraph(Set<V> set){ for(var i : set) { vertex_map.put(i, new ArrayList<>()); } }

    public Set<V> getVertexes() { return vertex_map.keySet(); }
    public void putVertex(V v){ vertex_map.put(v, new ArrayList<>()); }
    public boolean hasVertex(V v) { return vertex_map.containsKey(v); }

    public List<V> getNeighbors(V v){ return vertex_map.get(v); }
    public void setNeighbors(V v, V[] vertexes){
        List<V> ns = vertex_map.get(v);
        if(ns != null) {
            ns.clear();
            Collections.addAll(ns, vertexes);
        }else{
            ns = new ArrayList<>();
            Collections.addAll(ns, vertexes);
            vertex_map.put(v, ns);
        }
    }
    public void clearNeighbors(V v){
        var ns = vertex_map.get(v);
        if(ns != null) ns.clear();
    }

    public void putNeighbor(V v, V n){
        addNeighbor(v, n);
        addNeighbor(n, v);
    }
    protected void addNeighbor(V v, V n){
        List<V> ns = vertex_map.get(v);
        if(ns != null) {
            if(!ns.contains(n)) ns.add(n);
        }else{
            ns = new ArrayList<>();
            vertex_map.put(v, ns);
            ns.add(n);
        }
    }
    public boolean haveNeighbor(V v, V neighbor) { return vertex_map.get(v).contains(neighbor); }
    public void removeNeighbor(V v, V n){ vertex_map.get(v).remove(n); }

}
