package tools;

import java.util.Comparator;
import java.util.Objects;

public final class KeyValuePair<K, V> implements Comparable<KeyValuePair<K, V>>{
    private K key;
    private V value;
    private Comparator<K> k_comparator;
    public KeyValuePair(K key, V value){
        Objects.requireNonNull(key);
        this.key = key;
        this.value = value;
        if(!(key instanceof Comparable)) throw new IllegalArgumentException();
    }
    public KeyValuePair(K key, V value, Comparator<K> k_comparator){
        Objects.requireNonNull(k_comparator);
        Objects.requireNonNull(key);
        this.key = key;
        this.value = value;
        this.k_comparator = k_comparator;
    }

    public void setComparator(Comparator<K> k_comparator){ this.k_comparator = k_comparator; }

    public K getKey() { return key; }
    public void setKey(K key) {
        Objects.requireNonNull(key);
        this.key = key;
    }

    public V getValue() { return value; }
    public void setValue(V value) { this.value = value; }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object other){
        if(other == null) return false;
        else if(other.getClass().equals(this.getClass())){
            return key.equals(((KeyValuePair<K, V>) other).key) && value.equals(((KeyValuePair<K, V>) other).value);
        }else return false;
    }

    @Override
    public int hashCode() { return Objects.hash(key, value); }

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(KeyValuePair<K, V> other){
        if(key instanceof Comparable){
            var t = ((Comparable<K>) key).compareTo(other.key);
            if(t == 0) return value.hashCode() - other.hashCode();
            else return t;
        }else if(k_comparator != null){
            var t = k_comparator.compare(key, other.key);
            if(t == 0) return value.hashCode() - other.hashCode();
            else return t;
        }else throw new AssertionError();
    }

    @Override
    public String toString() { return String.format("Pair(key: %s, value: %s)",key.toString(),value.toString()); }
}
