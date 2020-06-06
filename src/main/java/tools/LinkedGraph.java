package tools;

import java.util.*;

public class LinkedGraph<V extends Comparable<V>> {
    Map<V, List<V>> map = new TreeMap<>();

    public LinkedGraph(){}
    public LinkedGraph(V[] vertexes) { for(var v : vertexes) { this.map.put(v, new ArrayList<>());} }
    public LinkedGraph(Set<V> set){ for(var i : set) { map.put(i, new ArrayList<>()); } }

    public Set<V> getVertexes() { return map.keySet(); }
    public void putVertex(V v){ map.put(v, new ArrayList<>()); }
    public boolean hasVertex(V v) { return map.containsKey(v); }

    public List<V> getNeighbors(V v){ return map.get(v); }
    public void setNeighbors(V v, V[] vertexes){
        List<V> ns = map.get(v);
        if(ns != null) {
            ns.clear();
            Collections.addAll(ns, vertexes);
        }else{
            ns = new ArrayList<>();
            Collections.addAll(ns, vertexes);
            map.put(v, ns);
        }
    }
    public void cleanNeighbors(V v){
        var ns = map.get(v);
        if(ns != null) ns.clear();
    }

    public void putNeighbor(V v, V n){
        List<V> ns = map.get(v);
        if(ns != null) {
            if(!ns.contains(n)) ns.add(n);
        }else{
            ns = new ArrayList<>();
            map.put(v, ns);
            ns.add(n);
        }
    }
    public boolean haveNeighbor(V v, V neighbor) { return map.get(v).contains(neighbor); }
    public void removeNeighbor(V v, V n){ map.get(v).remove(n); }

}
