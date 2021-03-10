package org.nathan.algorithmsJava.structures;


import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unused")
public class Bag<Item> implements Iterable<Item> {
    private Node first = null;
    private int n = 0;

    public Bag() {

    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.n;
    }

    public void add(Item item) {
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;
        ++this.n;
    }

    public @NotNull Iterator<Item> iterator() {
        return new LinkedIterator(this.first);
    }

    private class Node {
        private Item item;
        private Node next;

        private Node() {
        }
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node current;

        public LinkedIterator(Node first) {
            this.current = first;
        }

        public boolean hasNext() {
            return this.current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                Item item = this.current.item;
                this.current = this.current.next;
                return item;
            }
        }
    }
}
