package tools;

import java.util.*;
import java.util.function.Function;

// key must be a number
public class MinHeap<V> {
    private class NodeAndIndex{
        KeyValuePair<Double,V> node;
        int index;
        NodeAndIndex(KeyValuePair<Double,V> node, int index){
            this.node = node;
            this.index = index;
        }
    }
    private final List<KeyValuePair<Double,V>> array = new ArrayList<>();
    private final Map<V, NodeAndIndex> value_node_map = new HashMap<>();
    private int heap_size;

    public MinHeap(Collection<V> c, Function<V, Double> getKey){
        Objects.requireNonNull(c);
        Objects.requireNonNull(getKey);
        heap_size = 0;
        int idx = 0;
        for(var i : c){
            Objects.requireNonNull(i);
            heap_size++;
            var n = new KeyValuePair<>(getKey.apply(i),i);
            array.add(n);
            value_node_map.put(n.getValue(),new NodeAndIndex(n,idx++));
        }
        buildMinHeap(array);
    }

    public V extractMin(){
        var res = array.get(0);
        array.set(0, array.get(heap_size -1));
        array.set(heap_size-1,null);
        heap_size--;
        minHeapify(array,0, heap_size);
        value_node_map.remove(res.getValue());
        return res.getValue();
    }

    public void decreaseKey(V value, double new_key){
        var store = value_node_map.get(value);
        if(store.node.getKey() < new_key) throw new IllegalArgumentException();
        else{
            store.node.setKey(new_key);
            boolean min_property_broken = true;
            int parent_idx = (store.index+1)/2-1;
            while (min_property_broken && parent_idx >= 0){
                min_property_broken = minHeapify(array, parent_idx, heap_size);
                parent_idx = (parent_idx+1)/2-1;
            };
        }
    }

    public boolean contains(V value) { return value_node_map.containsKey(value); }

    public int length() { return heap_size; }

    private boolean minHeapify(List<KeyValuePair<Double,V>> arr, int idx, int heap_size){
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
            return true;
        }
        return false;
    }
    private void buildMinHeap(List<KeyValuePair<Double,V>> arr){
        for(int i = heap_size /2 - 1; i >= 0; i--){
            minHeapify(arr, i, heap_size);
        }
    }
}
