package tools;

import java.util.*;
import java.util.function.ToDoubleFunction;

// key must be a number
public class MinHeap<V> {
    private class Node{
        double key;
        V value;
        Node(double key, V value){
            this.key = key;
            this.value = value;
        }
    }
    private class PairAndIndex {
        Node node;
        int index;

        PairAndIndex(Node node, int index) {
            this.node = node;
            this.index = index;
        }
    }

    private final List<Node> array = new ArrayList<>();
    private final Map<V, PairAndIndex> value_node_map = new HashMap<>();
    private int heap_size = 0;

    public MinHeap() {
    }

    public MinHeap(Collection<V> c, ToDoubleFunction<V> getKey) {
        Objects.requireNonNull(c);
        Objects.requireNonNull(getKey);
        heap_size = 0;
        int idx = 0;
        for (var i : c) {
            Objects.requireNonNull(i);
            heap_size++;
            var n = new Node(getKey.applyAsDouble(i), i);
            array.add(n);
            value_node_map.put(n.value, new PairAndIndex(n, idx++));
        }
        buildMinHeap();
    }

    public V forceExtractMin() {
        if (heap_size == 0) {
            throw new NoSuchElementException();
        }
        var res = array.get(0);
        array.set(0, array.get(heap_size - 1));
        array.remove(heap_size - 1);
        heap_size--;
        minHeapify(0, heap_size);
        value_node_map.remove(res.value);
        return res.value;
    }

    public void decreaseKey(V value, double new_key) {
        var store = value_node_map.get(value);
        if (store.node.key < new_key) {
            throw new IllegalArgumentException();
        }
        else {
            store.node.key = new_key;
            fix(store);
        }
    }

    private void fix(PairAndIndex store) {
        boolean min_property_broken = true;
        int parent_idx = (store.index + 1) / 2 - 1;
        while (min_property_broken && parent_idx >= 0) {
            min_property_broken = minHeapify(parent_idx, heap_size);
            parent_idx = (parent_idx + 1) / 2 - 1;
        }
    }

    public void add(double key, V value) {
        if (heap_size == array.size()) {
            heap_size++;
            var n = new Node(key, value);
            array.add(n);
            var v_n = new PairAndIndex(n, heap_size - 1);
            value_node_map.put(value, v_n);
            fix(v_n);
        }
        else {
            heap_size++;
            var n = new Node(key, value);
            array.set(heap_size - 1, n);
            var v_n = new PairAndIndex(n, heap_size - 1);
            value_node_map.put(value, v_n);
            fix(v_n);
        }
    }

    public boolean contains(V value) {
        return value_node_map.containsKey(value);
    }

    public int length() {
        return heap_size;
    }

    private boolean minHeapify(int idx, int heap_size) {
        int l = 2 * (idx + 1);
        int l_idx = l - 1;
        int r = 2 * (idx + 1) + 1;
        int r_idx = r - 1;
        int min_idx = idx;
        if ((l_idx < heap_size) && (array.get(l_idx).key < array.get(min_idx).key)) {
            min_idx = l_idx;
        }
        if ((r_idx < heap_size) && (array.get(r_idx).key < array.get(min_idx).key)) {
            min_idx = r_idx;
        }
        if (min_idx != idx) {
            var t = array.get(min_idx);
            array.set(min_idx, array.get(idx));
            value_node_map.get(array.get(idx).value).index = min_idx;
            array.set(idx, t);
            value_node_map.get(t.value).index = idx;
            minHeapify(min_idx, heap_size);
            return true;
        }
        return false;
    }

    private void buildMinHeap() {
        for (int i = heap_size / 2 - 1; i >= 0; i--) {
            minHeapify(i, heap_size);
        }
    }
}
