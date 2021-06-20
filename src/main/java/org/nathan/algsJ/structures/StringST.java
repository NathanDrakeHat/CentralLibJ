package org.nathan.algsJ.structures;


// TODO string symbol table
public class StringST<V> implements SymbolTable<String, V>{
  @Override
  public void put(String s, V v){

  }

  @Override
  public V get(String s){
    return null;
  }

  @Override
  public void delete(String s){

  }

  @Override
  public boolean contains(String s){
    return false;
  }

  @Override
  public boolean isEmpty(){
    return false;
  }

  @Override
  public int size(){
    return 0;
  }

  @Override
  public String min(){
    return null;
  }

  @Override
  public String max(){
    return null;
  }

  @Override
  public String floor(String s){
    return null;
  }

  @Override
  public String ceiling(String s){
    return null;
  }

  @Override
  public int rank(String s){
    return 0;
  }

  @Override
  public String select(int rank){
    return null;
  }

  @Override
  public int size(String low, String high){
    return 0;
  }

  @Override
  public Iterable<String> keys(String low, String high){
    return null;
  }
}
