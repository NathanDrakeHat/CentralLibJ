package org.nathan.AlgorithmsJava.tools;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.ToDoubleFunction;

// key must be a number
public final class MinHeap<V> implements Iterable<Pair<Double, V>> {

    private final List<Node<V>> array = new ArrayList<>();
    private final Map<V, Node<V>> value_node_map = new HashMap<>();
    private int heap_size = 0;
    public MinHeap() {
    }

    public MinHeap(@NotNull Iterable<V> c, @NotNull ToDoubleFunction<V> getKey) {
        heap_size = 0;
        int idx = 0;
        for (var i : c) {
            Objects.requireNonNull(i);
            heap_size++;
            var n = new Node<>(getKey.applyAsDouble(i), i, idx++);
            array.add(n);
            value_node_map.put(n.value, n);
        }
        buildMinHeap();
    }

    public V extractMin() {
        if (heap_size == 0) {
            throw new NoSuchElementException();
        }
        var res = array.get(0);
        array.set(0, array.get(heap_size - 1));
        array.remove(heap_size - 1);
        heap_size--;
        minHeapify(0);
        value_node_map.remove(res.value);
        return res.value;
    }

    public void update(V value, double new_key) {
        var node = value_node_map.get(value);
        if (node == null) {
            throw new NoSuchElementException("No such value.");
        }
        if (new_key < node.key) {
            node.key = new_key;
            updateMin(node);
        }
        else if (new_key > node.key) {
            node.key = new_key;
            minHeapify(node.index);
        }
    }

    private void updateMin(Node<V> node) {
        boolean min_property_broken = true;
        int parent_idx = (node.index + 1) / 2 - 1;
        while (min_property_broken && parent_idx >= 0) {
            min_property_broken = minHeapify(parent_idx);
            parent_idx = (parent_idx + 1) / 2 - 1;
        }
    }

    public void add(double key, V value) {
        heap_size++;
        var n = new Node<>(key, value, heap_size - 1);
        array.add(n);
        value_node_map.put(value, n);
        updateMin(n);
    }

    public boolean contains(V value) {
        return value_node_map.containsKey(value);
    }

    public int length() {
        return heap_size;
    }

    private boolean minHeapify(int idx) {
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
            minHeapify(min_idx);
            return true;
        }
        return false;
    }

    private void buildMinHeap() {
        for (int i = heap_size / 2 - 1; i >= 0; i--) {
            minHeapify(i);
        }
    }

    @Override
    public Iterator<Pair<Double, V>> iterator() {
        return array.stream().map((n) -> new Pair<>(n.key, n.value)).iterator();
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
