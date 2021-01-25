package org.nathan.AlgorithmsJava.tools;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.ToDoubleFunction;


public final class MinHeap<V> {

    private final List<Node<V>> array = new ArrayList<>();
    private final Map<V, Node<V>> value_node_map = new HashMap<>();

    public MinHeap(){}

    public MinHeap(@NotNull Iterable<V> values, @NotNull ToDoubleFunction<V> getKey) {
        int idx = 0;
        for (var value : values) {
            Objects.requireNonNull(value);
            var n = new Node<>(getKey.applyAsDouble(value), value, idx);
            idx++;
            array.add(n);
            value_node_map.put(n.value, n);
        }
        buildMinHeap();
    }

    public V extractMin() {
        if (heapSize() == 0) {
            throw new NoSuchElementException();
        }
        var res = array.get(0);
        updateArray(0,array.get(heapSize()-1));
        array.remove(heapSize() - 1);
        minHeapify(0);
        value_node_map.remove(res.value);
        return res.value;
    }

    public void Add(V value, double key){
        Node<V> n = new Node<>(key,value,heapSize());
        array.add(n);
        value_node_map.put(value, n);
        decreaseKey(heapSize()-1);
    }

    public boolean contains(V value) {
        return value_node_map.containsKey(value);
    }

    public int length() {
        return heapSize();
    }

    public void updateKey(@NotNull V value, double new_key) {
        var node = value_node_map.get(value);
        if (node == null) {
            throw new NoSuchElementException("No such value.");
        }
        if (new_key < node.key) {
            node.key = new_key;
            decreaseKey(node.index);
        }
        else if (new_key > node.key) {
            node.key = new_key;
            minHeapify(node.index);
        }
    }

    public int heapSize() {
        return array.size();
    }

    private void updateArray(int index, Node<V> node){
        array.set(index, node);
        node.index = index;
    }

    private void decreaseKey(int idx) {
        while (idx > 0 && array.get(parentIndex(idx)).key > array.get(idx).key){
            int p_index = parentIndex(idx);
            var t = array.get(idx);
            updateArray(idx, array.get(p_index));
            updateArray(p_index,t);
            idx = p_index;
        }
    }
    
    private void minHeapify(int idx) {
        int l_idx = leftIndex(idx);
        int r_idx = rightIndex(idx);
        int min_idx = idx;
        if ((l_idx < heapSize()) && (array.get(l_idx).key < array.get(min_idx).key)) {
            min_idx = l_idx;
        }
        if ((r_idx < heapSize()) && (array.get(r_idx).key < array.get(min_idx).key)) {
            min_idx = r_idx;
        }
        if (min_idx != idx) {
            var t = array.get(min_idx);
            updateArray(min_idx,array.get(idx));
            updateArray(idx,t);
            minHeapify(min_idx);
        }
    }

    private void buildMinHeap() {
        for (int i = parentIndex(heapSize() - 1); i >= 0; i--) {
            minHeapify(i);
        }
    }

    private static int leftIndex(int idx){
        return 2 * (idx + 1) - 1;
    }

    private static int rightIndex(int idx){
        return 2 * (idx + 1);
    }

    private static int parentIndex(int idx){
        return (idx + 1) / 2 - 1;
    }

    private static class Node<E> {
        double key;
        E value;
        int index;

        Node(double key, E value, int index) {
            this.key = key;
            this.value = value;
            this.index = index;
        }
    }
}
