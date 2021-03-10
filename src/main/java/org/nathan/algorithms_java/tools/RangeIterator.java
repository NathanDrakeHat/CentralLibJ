package org.nathan.algorithms_java.tools;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class RangeIterator implements Iterable<Integer> {
    private final int high;
    private int index;

    public RangeIterator(int l, int h) {
        high = h;
        index = l;
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
                index++;
                return t;
            }
        };
    }
}
