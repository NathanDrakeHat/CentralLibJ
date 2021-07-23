package org.nathan.centralib.utils.tuples;

import java.util.Objects;

public class Quaternion<T1, T2, T3, T4> {
  final T1 first;
  final T2 second;
  final T3 third;
  final T4 fourth;

  public Quaternion(T1 f, T2 s, T3 t, T4 fo) {
    first = f;
    second = s;
    third = t;
    fourth = fo;
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

  public T4 fourth() {
    return fourth;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Quaternion<?, ?, ?, ?> that)) {
      return false;
    }
    return Objects.equals(first, that.first) && Objects.equals(second, that.second) && Objects.equals(third,
            that.third) && Objects.equals(fourth, that.fourth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second, third, fourth);
  }
}
