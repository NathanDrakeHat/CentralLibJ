package tool;

public class GenericArray<K> {
    K[] array;

    @SuppressWarnings("unchecked")
    public GenericArray(int len) { array = (K[])(new Object[len]); }

    public void set(int idx, K c) { array[idx] = c; }

    public K get(int idx) { return array[idx]; }

    public int size() {return array.length;}
}
