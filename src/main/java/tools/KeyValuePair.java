package tools;

import java.util.Objects;

public final class KeyValuePair<A extends Comparable<A>, B> implements Comparable<KeyValuePair<A, B>>{
    A key;
    B value;
    public KeyValuePair(A key, B value){
        this.key = key;
        this.value = value;
    }
    public KeyValuePair(){}

    public A getKey() { return key; }
    public void setKey(A key) { this.key = key; }

    public B getValue() { return value; }
    public void setValue(B value) { this.value = value; }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object other){
        if(other.getClass().equals(this.getClass())){
            return key.equals(((KeyValuePair<A, B>) other).key) && value.equals(((KeyValuePair<A,B>) other).value);
        }else return false;
    }

    @Override
    public int hashCode() { return Objects.hash(key, value); }

    @Override
    public int compareTo(KeyValuePair<A, B> other){
        return key.compareTo(other.key);
    }

    @Override
    public String toString() { return String.format("Pair (key: %s, value: %s)",key.toString(),value.toString()); }
}
