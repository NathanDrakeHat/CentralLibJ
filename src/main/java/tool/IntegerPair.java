package tool;

public class IntegerPair<V> {
    private int key;
    private V value;

    public IntegerPair(int key, V value){
        this.key = key;
        this.value = value;
    }
    public IntegerPair(int key){ this.key = key; }
    public IntegerPair() {}

    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }

    public V getValue() { return value; }
    public void setValue(V value) { this.value = value; }
}
