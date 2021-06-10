package org.nathan.algorithmsJ.structures;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;
import java.util.function.Function;

public class ExtremumHeap<K, V> implements Iterable<Tuple<K, V>>{
  private final List<Node<K, V>> array = new ArrayList<>();
  private final Map<V, Node<K, V>> value_node_map = new HashMap<>();
  private final Comparator<K> key_comparer;
  private boolean iterating = false;
  private final boolean isMinHeap;

  public ExtremumHeap(boolean isMinHeap, @NotNull Comparator<K> comparer){
    this.isMinHeap = isMinHeap;
    key_comparer = comparer;
  }

  public ExtremumHeap(
          boolean isMinHeap,
          @NotNull Iterable<V> values,
          @NotNull Function<V, K> getKey,
          @NotNull Comparator<K> comparer){
    this.isMinHeap = isMinHeap;
    key_comparer = comparer;
    for(var value : values){
      Objects.requireNonNull(value);
      if(value_node_map.containsKey(value)){
        throw new IllegalArgumentException("values should be unique");
      }
      var n = new Node<>(getKey.apply(value), value, array.size());
      array.add(n);
      value_node_map.put(n.value, n);
    }
    buildHeap();
  }

  public V extractExtremum(){
    modified();
    if(heapSize() == 0){
      throw new NoSuchElementException();
    }
    var res = array.get(0);
    updateArrayAndNode(0, array.get(heapSize() - 1));
    array.remove(heapSize() - 1);
    if(isMinHeap){
      minHeapify(0);
    }
    else {
      maxHeapify(0);
    }
    value_node_map.remove(res.value);
    return res.value;
  }

  public void add(@NotNull V value, @NotNull K key){
    modified();
    if(value_node_map.containsKey(value)){
      throw new IllegalArgumentException("value should be unique");
    }
    Node<K, V> n = new Node<>(key, value, heapSize());
    array.add(n);
    value_node_map.put(value, n);
    if(isMinHeap){
      decreaseKey(heapSize() - 1);
    }
    else {
      increaseKey(heapSize() - 1);
    }
  }

  public boolean contains(@NotNull V value){
    return value_node_map.containsKey(value);
  }

  public int length(){
    return heapSize();
  }

  public void updateKey(@NotNull V value, @NotNull K new_key){
    modified();
    var node = value_node_map.get(value);
    if(node == null){
      throw new NoSuchElementException("No such value.");
    }
    if(isMinHeap){
      if(key_comparer.compare(new_key, node.key) < 0){
        node.key = new_key;
        decreaseKey(node.index);
      }
      else if(key_comparer.compare(new_key, node.key) > 0){
        node.key = new_key;
        minHeapify(node.index);
      }
    }
    else {
      if(key_comparer.compare(new_key, node.key) < 0){
        node.key = new_key;
        maxHeapify(node.index);
      }
      else if(key_comparer.compare(new_key, node.key) > 0){
        node.key = new_key;
        increaseKey(node.index);
      }
    }
  }

  public int heapSize(){
    return array.size();
  }

  private void modified(){
    iterating = false;
  }

  private void updateArrayAndNode(int index, Node<K, V> node){
    array.set(index, node);
    node.index = index;
  }

  private void decreaseKey(int idx){
    while(idx > 0 &&
            key_comparer.compare(array.get(parentIndex(idx)).key, array.get(idx).key) > 0) {
      int p_index = parentIndex(idx);
      var t = array.get(idx);
      updateArrayAndNode(idx, array.get(p_index));
      updateArrayAndNode(p_index, t);
      idx = p_index;
    }
  }

  private void increaseKey(int idx){
    while(idx > 0 &&
            key_comparer.compare(array.get(parentIndex(idx)).key, array.get(idx).key) < 0) {
      int p_index = parentIndex(idx);
      var t = array.get(idx);
      updateArrayAndNode(idx, array.get(p_index));
      updateArrayAndNode(p_index, t);
      idx = p_index;
    }
  }

  private void minHeapify(int idx){
    int l_idx = leftIndex(idx);
    int r_idx = rightIndex(idx);
    int min_idx = idx;
    if((l_idx < heapSize()) && key_comparer.compare(array.get(l_idx).key, array.get(min_idx).key) < 0){
      min_idx = l_idx;
    }
    if((r_idx < heapSize()) && key_comparer.compare(array.get(r_idx).key, array.get(min_idx).key) < 0){
      min_idx = r_idx;
    }
    if(min_idx != idx){
      var t = array.get(min_idx);
      updateArrayAndNode(min_idx, array.get(idx));
      updateArrayAndNode(idx, t);
      minHeapify(min_idx);
    }
  }

  private void maxHeapify(int idx){
    int l_idx = leftIndex(idx);
    int r_idx = rightIndex(idx);
    int max_idx = idx;
    if((l_idx < heapSize()) && key_comparer.compare(array.get(l_idx).key, array.get(max_idx).key) > 0){
      max_idx = l_idx;
    }
    if((r_idx < heapSize()) && key_comparer.compare(array.get(r_idx).key, array.get(max_idx).key) > 0){
      max_idx = r_idx;
    }
    if(max_idx != idx){
      var t = array.get(max_idx);
      updateArrayAndNode(max_idx, array.get(idx));
      updateArrayAndNode(idx, t);
      maxHeapify(max_idx);
    }
  }

  private void buildHeap(){
    for(int i = parentIndex(heapSize() - 1); i >= 0; i--){
      if(isMinHeap){
        minHeapify(i);
      }
      else {
        maxHeapify(i);
      }
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

  private static class Node<IK, IV>{
    IK key;
    IV value;
    int index;

    Node(IK key, IV value, int index){
      this.key = key;
      this.value = value;
      this.index = index;
    }
  }

  private class HeapIterator implements Iterator<Tuple<K, V>>{

    HeapIterator(){
      iterating = true;
    }

    @Override
    public boolean hasNext(){
      if(!iterating){
        throw new IllegalStateException("concurrent modification");
      }
      return heapSize() > 0;
    }

    @Override
    public Tuple<K, V> next(){
      var key = array.get(0).key;
      return new Tuple<>(key, extractExtremum());
    }
  }

  @NotNull
  @Override
  public Iterator<Tuple<K, V>> iterator(){
    return new HeapIterator();
  }
}
