// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;

public class Deque<Item> implements Iterable<Item> {
  // construct an empty deque
  /*
   * We'll use a doubly linked list for this algorithm.
   */

  private BiNode first, last;
  private int n = 0; // size of the doubly linked list

  private class BiNode {
    Item item;
    BiNode next;
    BiNode prev;
  }

  public Deque() {}

  // is the deque empty?
  public boolean isEmpty() { return first == null; }

  // return the number of items on the deque
  public int size() { return n; }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null)
      throw new IllegalArgumentException("Cannot call addFirst with null item");

    if (first == null) { // the hackery is strong in this one
      first = new BiNode();
      first.item = item;
      last = first;
    } else {
      BiNode oldfirst = first;
      first = new BiNode();
      first.item = item;
      first.next = oldfirst;
      first.next.prev = first;
    }
    n++;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null)
      throw new IllegalArgumentException("Cannot call addLast with null item");

    if (last == null) { // what a mess!!
      last = new BiNode();
      last.item = item;
      first = last;
    } else {
      BiNode oldlast = last;
      last = new BiNode();
      last.item = item;
      last.prev = oldlast;
      last.prev.next = last;
    }
    n++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty())
      throw new NoSuchElementException("Stack underflow");
    Item item = first.item;
    if (first.next == null) { // surely this is not as intended
      first = null;
      last = null;
    } else {
      first = first.next;
      first.prev = null;
    }
    n--;
    return item;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty())
      throw new NoSuchElementException("Stack underflow");
    Item item = last.item;
    if (last.prev == null) {
      first = null;
      last = null;
    } else {
      last = last.prev;
      last.next = null;
    }
    n--;
    return item;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() { return new DequeIterator(); }

  private class DequeIterator implements Iterator<Item> {
    private BiNode current = first;

    public boolean hasNext() { return current != null; }

    public void remove() { throw new UnsupportedOperationException(); }

    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    Deque<Integer> deque = new Deque();
    deque.addFirst(1);
    StdOut.println("What's the current size of the deque? " + deque.size());
    deque.addFirst(0);
    StdOut.println("What's the current size of the deque? " + deque.size());
    // deque.removeLast();
    deque.addLast(3);
    StdOut.println("What's the current size of the deque? " + deque.size());
    deque.addLast(4);
    StdOut.println("What's the current size of the deque? " + deque.size());
    StdOut.println("Pop from behind: " + deque.removeLast());
    StdOut.println("Pop from behind: " + deque.removeLast());
    StdOut.println("Pop from behind: " + deque.removeFirst());
    StdOut.println("Pop from behind: " + deque.removeFirst());
    StdOut.println("What's the size of the deque now? " + deque.size());
    StdOut.println("Repopulate it with 1 2 3");
    for (int i = 0; i < 20; i++)
      deque.addFirst(i);
    StdOut.println("And iterate over it.");
    for (int j : deque)
      StdOut.println("Pop from behind: " + j);
  }
}
