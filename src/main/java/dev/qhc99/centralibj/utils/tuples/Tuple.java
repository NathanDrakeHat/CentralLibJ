package dev.qhc99.centralibj.utils.tuples;


import java.util.Objects;

public class Tuple<T1, T2> {
  final T1 first;
  final T2 second;

  public Tuple(T1 f, T2 s) {
    first = f;
    second = s;
  }

  public T1 first() {
    return first;
  }

  public T2 second() {
    return second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Tuple<?, ?> tuple)) {
      return false;
    }
    return Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }

  @Override
  public String toString() {
    return "Tuple{" +
            "first=" + first +
            ", second=" + second +
            '}';
  }
}
