package tool;

import java.util.*;

public class LinkedGraph<V> {
    private class Head{
        V v = null;
        List<V> neighbors = new ArrayList<>();

        Head(V v){ this.v = v; }
    }
    List<Head> vs = new ArrayList<>();
    Map<V, Head> map = new HashMap<>();

    public LinkedGraph(){}
    public LinkedGraph(V[] vertexes) {
        for (var v : vertexes) {
            var h = new Head(v);
            this.vs.add(h);
            this.map.put(v, h);
        }
    }

    public List<V> getVertexes() {
        List<V> res = new ArrayList<>();
        for(var h : vs)
            res.add(h.v);
        return res;
    }

    public List<V> getNeighbors(V v){
        return map.get(v).neighbors;
    }
    public void setNeighbors(V v, V[] vertexes){
        List<V> ns = map.get(v).neighbors;
        ns.clear();
        Collections.addAll(ns, vertexes);
    }
    public void cleanNeighbors(V v){ map.get(v).neighbors.clear(); }

    public void putNeighbor(V v, V n){
        var ns = map.get(v).neighbors;
        if(ns != null)
            ns.add(n);
    }
    public void removeNeighbor(V v, V n){ map.get(v).neighbors.remove(n); }

}
