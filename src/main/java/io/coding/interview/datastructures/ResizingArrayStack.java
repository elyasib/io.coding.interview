package io.coding.interview.datastructures;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayStack<Item> implements Iterable<Item> {

  private Item[] items;
  private int n;

  @SuppressWarnings("unchecked")
  public ResizingArrayStack() {
    this.items = (Item[]) new Object[2];
    this.n = 0;
  }

  @SuppressWarnings("unchecked")
  public ResizingArrayStack(Item... items) {
    if (items == null) {
      throw new NullPointerException();
    }
    int len = items.length;
    int initialCapacity = len == 0 ? 2 : len * 2;
    this.items = Arrays.copyOf(items, initialCapacity);
    this.n = len;
  }

  @SuppressWarnings("unchecked")
  public ResizingArrayStack(Collection<Item> items) {
    this((Item[]) items.toArray());
  }

  public int size() {
    return n;
  }

  public boolean isEmpty() {
    return n == 0;
  }

  public void push(Item item) {
    if (n == items.length) {
      resize(items.length * 2);
    }
    items[n] = item;
    ++n;
  }

  public Item pop() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    --n;
    Item item = items[n];
    items[n] = null;
    if (n > 0 && n == items.length / 4) {
      resize(items.length / 2);
    }
    return item;
  }

  public Item peek() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return items[n - 1];
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    int lastItemIndex = n - 1;
    for (int i = lastItemIndex; i >= 0; --i) {
      if (i != lastItemIndex) {
        sb.append(", ");
      }
      sb.append(items[i]);
    }
    sb.append("]");
    return sb.toString();
  }

  @Override
  public Iterator<Item> iterator() {
    return new ReverseArrayIterator();
  }

  private class ReverseArrayIterator implements Iterator<Item> {

    private int i;

    public ReverseArrayIterator() {
      i = n;
    }

    @Override
    public boolean hasNext() {
      return i > 0;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      --i;
      return items[i];
    }
  }

  private void resize(int newSize) {
    items = Arrays.copyOf(items, newSize);
  }

  public static void main(String[] args) {
    ResizingArrayStack<Integer> stack = new ResizingArrayStack<>(1, 2, 2);
    System.out.println(stack);
  }
}
