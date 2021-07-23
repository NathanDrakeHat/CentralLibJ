package org.nathan.centralibj.utils.misc;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@SuppressWarnings("unused")
public class RangeIterator implements Iterable<Integer> {
  private final int high;
  private int index;
  private int step = 1;

  public RangeIterator(int l, int h) {
    high = h;
    index = l;
  }

  public RangeIterator(int l, int h, int step) {
    this(l, h);
    this.step = step;
  }

  @NotNull
  @Override
  public Iterator<Integer> iterator() {
    return new Iterator<>() {
      @Override
      public boolean hasNext() {
        return index < high;
      }

      @Override
      public Integer next() {
        var t = index;
        index += step;
        return t;
      }
    };
  }
}
