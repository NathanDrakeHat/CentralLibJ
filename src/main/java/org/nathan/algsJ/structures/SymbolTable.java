package org.nathan.algsJ.structures;

public interface SymbolTable<Key extends Comparable<Key>, Value> {


  void put(Key key, Value value);

  Value get(Key key);

  void delete(Key key);

  boolean contains(Key key);

  boolean isEmpty();

  int size();

  Key min();

  Key max();

  Key floor(Key key);

  Key ceiling(Key key);

  int rank(Key key);

  Key select(int rank);

  default void deleteMin() {
    delete(min());
  }

  default void deleteMax() {
    delete(max());
  }

  int size(Key low, Key high);

  Iterable<Key> keys(Key low, Key high);

  default Iterable<Key> keys() {
    return keys(min(), max());
  }
}
