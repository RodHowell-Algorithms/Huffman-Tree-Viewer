// File: ComparatorCombination.java
// A combination of two Comparators.

package edu.ksu.cis.huffmanCodes;

import java.util.Comparator;

/**
 * A combination of two Comparators.
 */
public class ComparatorCombination<T> implements Comparator<T> {

  /**
   * The primary Comparator.
   */
  private Comparator<T> primary;

  /**
   * The secondary Comparator.
   */
  private Comparator<T> secondary;

  /**
   * Returns a new ComparatorCombination.
   * @param c1  The primary Comparator.
   * @param c2  The secondary Comparator.
   */
  public ComparatorCombination(Comparator<T> c1, Comparator<T> c2) {
    primary = c1;
    secondary = c2;
  }

  /**
   * Compares the two given Objects.
   * @param obj1  The first object.
   * @param obj2  The second object.
   * @returns     The result of applying the primary Comparator to obj1 and
   *              obj2 if that result is nonzero, or the result of applying
   *              the secondary Comparator otherwise.
   */
  public int compare(T obj1, T obj2) {
    int r = primary.compare(obj1, obj2);
    if (r == 0) r = secondary.compare(obj1, obj2);
    return r;
  }
}
