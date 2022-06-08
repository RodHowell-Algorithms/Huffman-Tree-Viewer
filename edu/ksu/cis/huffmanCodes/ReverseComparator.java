// File: ReverseComparator.java
// The reverse of a given Comparator.

package edu.ksu.cis.huffmanCodes;

import java.util.Comparator;

/**
 * The reverse of a given Comparator.
 */
public class ReverseComparator<T> implements Comparator<T> {

  /**
   * The original Comparator.
   */
  private Comparator<T> originalComparator;

  /**
   * Returns a new ReverseComparator.
   */
  public ReverseComparator(Comparator<T> c) {
    originalComparator = c;
  }

  /**
   * Compares the two given objects.
   * @param obj1  The first object.
   * @param obj2  The second object.
   * @returns     The negative of the result of applying the original
   *              Comparator to obj1 and obj2.
   */
  public int compare(T obj1, T obj2) {
    return -originalComparator.compare(obj1, obj2);
  }
}
