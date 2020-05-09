class GenericArray<K> {
    K[] array;

    @SuppressWarnings("unchecked")
    GenericArray(int len) { array = (K[])(new Object[len]); }

    void set(int idx, K c) { array[idx] = c; }

    K get(int idx) { return array[idx]; }

    int size() {return array.length;}
}
