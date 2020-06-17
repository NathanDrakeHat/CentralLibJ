package algorithms.graph;

import tools.KeyValuePair;

import java.util.*;
import java.util.function.Function;

public class MinHeap<V> {
    private final List<KeyValuePair<Double,V>> array = new ArrayList<>();
    private final Map<V, NodeAndIndex> valuePairMap = new HashMap<>();
    private int length;
    private class NodeAndIndex{
        KeyValuePair<Double,V> node;
        int index;
        NodeAndIndex(KeyValuePair<Double,V> node, int index){
            this.node = node;
            this.index = index;
        }
    }

    public MinHeap(Set<V> l, Function<V, Double> getKey){
        length = l.size();
        int idx = 0;
        for(var i : l){
            var n = new KeyValuePair<>(getKey.apply(i),i);
            array.add(n);
            valuePairMap.put(n.getValue(),new NodeAndIndex(n,idx++));
        }
    }

    public V extractMin(){
        var res = array.get(0);
        array.set(0, array.get(length-1));
        length--;
        minHeapify(array,0,length);
        valuePairMap.remove(res.getValue());
        return res.getValue();
    }

    public void decreaseKey(V value, double new_key){
        var store = valuePairMap.get(value);
        if(store.node.getKey() <= new_key) throw new IllegalArgumentException();
        else{
            for(int i = store.index; i >= 0; i--){
                minHeapify(array,i,length);
            }
        }
    }

    public int length() { return length; }

    private void minHeapify(List<KeyValuePair<Double,V>> arr, int idx, int heap_size){
        int l = 2 * (idx + 1);
        int l_idx = l - 1;
        int r = 2 * (idx + 1) + 1;
        int r_idx = r - 1;
        int min_idx = idx;
        if ((l_idx < heap_size) && (arr.get(l_idx).getKey() < arr.get(min_idx).getKey())) {
            min_idx = l_idx;
        }
        if ((r_idx < heap_size) && (arr.get(r_idx).getKey() < arr.get(min_idx).getKey())) {
            min_idx = r_idx;
        }
        if (min_idx != idx) {
            var t = arr.get(min_idx);
            arr.set(min_idx,arr.get(idx))  ;
            arr.set(idx,t);
            minHeapify(arr, min_idx, heap_size);
        }
    }
    private void buildMinHeap(List<KeyValuePair<Double,V>> arr){
        for(int i = arr.size()/2 - 1; i >= 0; i--){
            minHeapify(arr, i, arr.size());
        }
    }

}
