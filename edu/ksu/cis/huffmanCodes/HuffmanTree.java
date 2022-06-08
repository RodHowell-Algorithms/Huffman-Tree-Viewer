// File: HuffmanTree.java
// A Huffman tree.

package  edu.ksu.cis.huffmanCodes;

import edu.ksu.cis.viewer.BinaryTree;
import java.util.Enumeration;
import java.awt.Font;
import java.util.HashMap;
import edu.ksu.cis.viewer.Node;
import edu.ksu.cis.viewer.TreeComponent;
import java.util.Vector;
import java.io.Serializable;

/**
 * This class builds a Huffman tree from a String.  The set of characters
 * used, as well as their frequencies and codes, can then be retrieved.
 */
public class HuffmanTree implements Serializable {

  /**
   * The characters in the encoded string.
   */
  private Vector<Character> characters = new Vector<Character>();

  /**
   * The frequencies.  The keys are Characters, and the elements are
   * Integer frequencies.
   */
  private HashMap<Character, Integer> frequencies =
    new HashMap<Character, Integer>();

  /**
   * The encodings.  The keys are Characters, and the elements are binary
   * encodings represented as Strings.
   */
  private HashMap<Character, String> encodings =
    new HashMap<Character, String>();

  /**
   * The tree.
   */
  private BinaryTree theTree;

  /**
   * Used for consistency in serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a HuffmanTree from the given String.
   */
  public HuffmanTree(String s) {
    findFrequencies(s);
    buildTree();
    generateEncodings(theTree, "");
  }

  /**
   * Returns the Characters occuring in the encoded String.
   * No particular ordering of the Characters is guaranteed.
   */
  public Character[] getCharacters() {
    Character[] c = new Character[characters.size()];
    return characters.toArray(c);
  }

  /**
   * Returns the number of occurrences of the given Character in the
   * encoded String.
   */
  public int getCount(Character c) {
    Integer i = frequencies.get(c);
    if (i == null) return 0;
    else return i.intValue();
  }

  /**
   * Returns the Huffman code of the given Character as a String.
   * If the Character does not occur in the encoded String, null is
   * returned.
   */
  public String getEncoding(Character c) {
    return encodings.get(c);
  }

  /**
   * Returns a drawing of the tree.
   */
  public TreeComponent getDrawing() {
    return theTree.getDrawing();
  }

  /**
   * Returns a drawing of the tree using the given Font.
   */
  public TreeComponent getDrawing(Font f) {
    return theTree.getDrawing(f);
  }

  /**
   * Returns the frequency/encoding table.  No particular ordering is 
   * guaranteed.
   */
  public FrequencyTableEntry[] getFrequencyTable() {
    FrequencyTableEntry[] theTable = 
      new FrequencyTableEntry[characters.size()];
    for (int i = 0; i < theTable.length; i++) {
      Character c = characters.elementAt(i);
      theTable[i] = 
	new FrequencyTableEntry(c, 
				frequencies.get(c).intValue(),
				encodings.get(c));
    }
    return theTable;
  }

  /**
   * Finds the frequencies of the characters in the given String.
   */
  private void findFrequencies(String s) {
    for (int i = 0; i < s.length(); i++) {
      int f = 0;
      Character c = Character.valueOf(s.charAt(i));
      Integer entry = frequencies.get(c);
      if (entry == null) characters.add(c);
      else f = entry.intValue();
      frequencies.put(c, Integer.valueOf(++f));
    }
  }

  /**
   * Builds the tree.  Assumes the frequency table has been constructed.
   */
  private void buildTree() {
    PriorityQueue q = new PriorityQueue();
    Enumeration<Character> e = characters.elements();
    while (e.hasMoreElements()) {
      Character c = e.nextElement();
      int f = frequencies.get(c).intValue();
      q.put(f, new BinaryTree(new Node(String.valueOf(c)), 
			      null, null));
    }
    BinaryTree empty = new BinaryTree();
    while (q.size() > 1) {
      int c1 = q.minKey();
      BinaryTree t1 = (BinaryTree) q.removeMin();
      int c2 = q.minKey();
      BinaryTree t2 = (BinaryTree) q.removeMin();
      q.put(c1 + c2, new BinaryTree(new Node(String.valueOf(c1 + c2)), 
				    t1, t2));
    }
    theTree = q.isEmpty() ? empty : (BinaryTree) q.removeMin();
  }

  /**
   * Generates the Huffman codes.  Assumes the tree has been built.
   * @param tree   The subtree to be processed.
   * @param prefix The prefix of the encoding leading to this subtree.
   */
  private void generateEncodings(BinaryTree tree, String prefix) {
    if (tree.getLeftChild().isEmpty()) 
      encodings.put(Character.valueOf(tree.getRoot().getContents().charAt(0)), 
		    prefix);
    else {
      generateEncodings(tree.getLeftChild(), prefix + '0');
      generateEncodings(tree.getRightChild(), prefix + '1');
    }
  }
}

