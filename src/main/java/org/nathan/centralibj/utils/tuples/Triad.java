package org.nathan.centralibj.utils.tuples;

import java.util.Objects;

public class Triad<T1, T2, T3> {
  final T1 first;
  final T2 second;
  final T3 third;

  public Triad(T1 f, T2 s, T3 t) {
    first = f;
    second = s;
    third = t;
  }

  public T1 first() {
    return first;
  }

  public T2 second() {
    return second;
  }

  public T3 third() {
    return third;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Triad<?, ?, ?> triad)) {
      return false;
    }
    return Objects.equals(first, triad.first) && Objects.equals(second, triad.second) && Objects.equals(third, triad.third);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second, third);
  }
}
