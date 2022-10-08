package dev.qhc99.centralibj.utils.misc;

public class Ref<T> {
  public T deRef;

  public Ref(T o) {
    deRef = o;
  }

  public Ref() {

  }

  public static <O> Ref<O> of(O obj) {
    return new Ref<>(obj);
  }

  public static <O> Ref<O> none() {
    return new Ref<>();
  }
}
