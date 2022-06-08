// File: PriorityQueue.java
// A min-priority-queue.

package edu.ksu.cis.huffmanCodes;

import java.util.Vector;

/**
 * A min-priority-queue.  The elements are Objects keyed by <tt>int</tt>s.
 * Smaller keys have higher priority.
 */
class PriorityQueue {

  /**
   * The key-element pairs.
   */
  private Vector<KeyElement> data = new Vector<>();

  /**
   * Constructs an empty PriorityQueue.
   */
  public PriorityQueue() {
    data.add(new KeyElement(0x80000000, null));
  }

  /**
   * Inserts an element.
   * @param key  The priority.
   * @param el   The element.
   */
  public void put(int key, Object el) {
    int i = data.size();
    int j = i/2;
    data.add(null);
    KeyElement parent = data.elementAt(j);
    while (parent.getKey() > key) {
      data.setElementAt(parent, i);
      i = j;
      j = j/2;
      parent = data.elementAt(j);
    }
    data.setElementAt(new KeyElement(key, el), i);
  }

  /**
   * Returns the minimum key in the priority queue.
   * @throws ArrayIndexOutOfBoundsException  If the PriorityQueue is empty.
   */
  public int minKey() throws ArrayIndexOutOfBoundsException {
    return data.elementAt(1).getKey();
  }

  /**
   * Removes the element with minimum key.
   * @returns  The element with minimum key.
   * @throws   ArrayIndexOutOfBoundsException  If the PriorityQueue is empty.
   */
  public Object removeMin() throws ArrayIndexOutOfBoundsException {
    Object min = data.elementAt(1).getElement();
    KeyElement last = data.remove(data.size() - 1);
    int key = last.getKey();
    int i = 1;
    int j = 2;
    while (j < data.size()) {
      if (j+1 < data.size() && data.elementAt(j).getKey() >
	  data.elementAt(j+1).getKey()) j++;
      if (data.elementAt(j).getKey() > key) break;
      data.setElementAt(data.elementAt(j), i);
      i = j;
      j *= 2;
    }
    if (i < data.size()) data.setElementAt(last, i);
    return min;
  }

  /**
   * Returns the element having minimum key, without removing it.
   * @throws ArrayIndexOutOfBoundsException  If the PriorityQueue is empty.
   */
  public Object minElement() throws ArrayIndexOutOfBoundsException {
    return data.elementAt(1);
  }

  /**
   * Returns <tt>true</tt> if the priority queue is empty.
   */
  public boolean isEmpty() {
    return data.size() == 1;
  }

  /**
   * Returns the number of elements in the priority queue.
   */
  public int size() {
    return data.size() - 1;
  }
}

/**
 * Key-element pairs for the PriorityQueue.  Keys are positive integers,
 * elements are objects.
 */
class KeyElement {

  /**
   * The key.
   */
  private int theKey;

  /**
   * The element.
   */
  private Object theElement;

  /**
   * Constructs a new KeyElement from the given key and element.
   */
  public KeyElement(int key, Object element) {
    theKey = key;
    theElement = element;
  }

  /**
   * Returns the key.
   */
  public int getKey() {
    return theKey;
  }

  /**
   * Returns the element.
   */
  public Object getElement() {
    return theElement;
  }
}
