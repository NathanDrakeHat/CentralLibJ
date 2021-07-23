package org.nathan.centralib.utils.tuples;

import java.util.Objects;

public class Quintuple<T1, T2, T3, T4, T5> {
  final T1 first;
  final T2 second;
  final T3 third;
  final T4 fourth;
  final T5 fifth;

  public Quintuple(T1 f, T2 s, T3 t, T4 fo, T5 fi) {
    first = f;
    second = s;
    third = t;
    fourth = fo;
    fifth = fi;
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

  public T5 fifth() {
    return fifth;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Quintuple<?, ?, ?, ?, ?> quintuple)) {
      return false;
    }
    return Objects.equals(first, quintuple.first) && Objects.equals(second, quintuple.second) && Objects.equals(third
            , quintuple.third) && Objects.equals(fourth, quintuple.fourth) && Objects.equals(fifth, quintuple.fifth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second, third, fourth, fifth);
  }
}
