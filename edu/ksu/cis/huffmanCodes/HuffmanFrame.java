// File: HuffmanFrame.java
// A window for displaying a Huffman tree and codes.

package edu.ksu.cis.huffmanCodes;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.util.Comparator;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

/**
 * A window for displaying a Huffman tree and codes.
 */
public class HuffmanFrame extends JFrame {

  /**
   * The content pane.
   */
  private Box content = new Box(BoxLayout.X_AXIS);

  /**
   * The scrolling frequency table.
   */
  private JScrollPane codePane;

  /**
   * The Huffman tree.
   */
  HuffmanTree theTree;
  /**
   * The frequency table.
   */
  private FrequencyTableEntry[] frequencyTable;

  /**
   * The current font size.
   */
  private static int fontSize = 12;

  /**
   * The current font style.
   */
  private static int fontStyle = Font.PLAIN;

  /**
   * The current font.
   */
  private Font theFont;

  /**
   * The Font size menu.
   */
  private JMenu sizeMenu = new JMenu("Size");

  /**
   * The "Bold" check box.
   */
  private JCheckBoxMenuItem boldBox;

  /**
   * The "Italic" check box.
   */
  private JCheckBoxMenuItem italicBox;

  /**
   * Used for consistency in serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new HuffmanFrame from the given frequency table and tree.
   * @param t      Drawing of the Huffman tree.
   * @param chars  The Characters in the character set.
   * @param codes  The Huffman code of each corresponding Character.
   */
  public HuffmanFrame(HuffmanTree t) {
    theTree = t;
    JMenuBar mb = new JMenuBar();
    setJMenuBar(mb);

    // The "Sort by..." menu
    JMenu m = new JMenu("Sort by...");
    JMenuItem sortByChar = new JMenuItem("Character");
    sortByChar.addActionListener
      (new SortListener(this, FrequencyTableEntry.getCharacterComparator()));
    m.add(sortByChar);
    JMenuItem sortByCode = new JMenuItem("Encoding");
    sortByCode.addActionListener
      (new SortListener(this, FrequencyTableEntry.getEncodingComparator()));
    m.add(sortByCode);
    JMenuItem sortByCodeLength = new JMenuItem("Encoding Length");
    sortByCodeLength.addActionListener
      (new SortListener(this, 
			FrequencyTableEntry.getEncodingLengthComparator()));
    m.add(sortByCodeLength);
    JMenuItem sortByCount = new JMenuItem("Frequency");
    sortByCount.addActionListener
      (new SortListener(this, 
			new ReverseComparator<FrequencyTableEntry>
			(FrequencyTableEntry.getCountComparator())));
    m.add(sortByCount);
    mb.add(m);

    // The "Font" menu
    JMenu fm = new JMenu("Font");
    for (int i = 10; i < 25; i += 2) {
      JMenuItem it = new JMenuItem(String.valueOf(i));
      it.addActionListener(new FontSizeListener(this, i));
      sizeMenu.add(it);
    }
    fm.add(sizeMenu);
    FontStyleListener fstl = new FontStyleListener(this);
    if ((fontStyle & Font.BOLD) == 0)
      boldBox = new JCheckBoxMenuItem("Bold");
    else boldBox = new JCheckBoxMenuItem("Bold", true);
    boldBox.addActionListener(fstl);
    fm.add(boldBox);
    if ((fontStyle & Font.ITALIC) == 0)
      italicBox = new JCheckBoxMenuItem("Italic");
    else italicBox = new JCheckBoxMenuItem("Italic", true);
    italicBox.addActionListener(fstl);
    fm.add(italicBox);
    mb.add(fm);    

    setContentPane(content);
    frequencyTable = t.getFrequencyTable();
    sortFrequencyTable(new ComparatorCombination<FrequencyTableEntry>
		       (new ReverseComparator<FrequencyTableEntry>
			(FrequencyTableEntry.getCountComparator()), 
			new ComparatorCombination<FrequencyTableEntry>
			(FrequencyTableEntry.getEncodingLengthComparator(), 
			 FrequencyTableEntry.getEncodingComparator())));
    setFontSize(fontSize);
    setSize(500, 300);
  }

  /**
   * Sets the font size.
   */
  public void setFontSize(int size) {
    fontSize = size;
    theFont = new Font("Monospaced", fontStyle, fontSize);
    updateContent();
  }

  /**
   * Sets the font style according to user selection.
   */
  public void setFontStyle() {
    int boldMask = boldBox.isSelected() ? Font.BOLD : 0;
    int italicMask = italicBox.isSelected() ? Font.ITALIC : 0;
    fontStyle = boldMask | italicMask;
    theFont = new Font("Monospaced", fontStyle, fontSize);
    updateContent();
  }

  /**
   * Draws the tree and frequency panel.
   */
  public void updateContent() {
    content.removeAll();
    content.add(new JScrollPane(theTree.getDrawing(theFont)));
    updateFrequencyPanel();
  }    

  /**
   * Updates the frequency panel to reflect the current state of the frequency
   * table.
   */
  public void updateFrequencyPanel() {
    Box tableBox = new Box(BoxLayout.X_AXIS);
    Box charBox = new Box(BoxLayout.Y_AXIS);
    Box hexBox = new Box(BoxLayout.Y_AXIS);
    Box codeBox = new Box(BoxLayout.Y_AXIS);
    Box countBox = new Box(BoxLayout.Y_AXIS);
    for (int i = 0; i < frequencyTable.length; i++) {
      FrequencyTableEntry e = frequencyTable[i];
      JLabel lbl = new JLabel(e.getCharacter() + "  ");
      lbl.setFont(theFont);
      charBox.add(lbl);
      Box b = new Box(BoxLayout.X_AXIS);
      b.add(Box.createHorizontalGlue());
      lbl = new JLabel(Integer.toString
		       ((int) (e.getCharacter().charValue()), 16));
      lbl.setFont(theFont);
      b.add(lbl);
      hexBox.add(b);
      lbl = new JLabel("  " + e.getEncoding() + "  ");
      lbl.setFont(theFont);
      codeBox.add(lbl);
      b = new Box(BoxLayout.X_AXIS);
      b.add(Box.createHorizontalGlue());
      lbl = new JLabel(String.valueOf(e.getCount()));
      lbl.setFont(theFont);
      b.add(lbl);
      countBox.add(b);
    }
    charBox.add(Box.createGlue());
    hexBox.add(Box.createGlue());
    codeBox.add(Box.createGlue());
    countBox.add(Box.createGlue());
    tableBox.add(Box.createHorizontalStrut(5));
    tableBox.add(charBox);
    tableBox.add(hexBox);
    tableBox.add(codeBox);
    tableBox.add(countBox);
    tableBox.add(Box.createHorizontalStrut(5));
    if (codePane != null) content.remove(codePane);
    codePane =
      new JScrollPane(tableBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    Dimension size = codePane.getPreferredSize();
    codePane.setMinimumSize(new Dimension(size.width, 0));
    codePane.setMaximumSize(new Dimension(size.width, 0x7fffffff));
    content.add(codePane);
    validate();
    repaint();
  }

  /**
   * Sorts the frequency table using the given Comparator.
   */
  public void sortFrequencyTable(Comparator<FrequencyTableEntry> c) {
    Arrays.sort(frequencyTable, c);
  }
}

/**
 * Event handler for sort actions.
 */
class SortListener implements ActionListener {

  /**
   * The parent Frame.
   */
  private HuffmanFrame theFrame;

  /**
   * The Comparator.
   */
  private Comparator<FrequencyTableEntry> theComparator;

  /**
   * Constructs a new SortListener that will sort using the given Comparator.
   */
  public SortListener(HuffmanFrame f, Comparator<FrequencyTableEntry> c) {
    theFrame = f;
    theComparator = c;
  }

  /**
   * Handles the event.  The parameter is ignored.
   */
  public void actionPerformed(ActionEvent e) {
    theFrame.sortFrequencyTable(theComparator);
    theFrame.updateFrequencyPanel();
  }
}

/**
 * Handles font size change events.
 */
class FontSizeListener implements ActionListener {

  /**
   * The parent frame.
   */
  private HuffmanFrame parent;

  /**
   * The size.
   */
  private int size;

  /**
   * Constructs a new FontSizeListener that handles changes to the given 
   * size. 
   */
  public FontSizeListener(HuffmanFrame f, int i) {
    parent = f;
    size = i;
  }

  /**
   * Handles the event.  The parameter is ignored.
   */
  public void actionPerformed(ActionEvent e) {
    parent.setFontSize(size);
  }
}

/**
 * Handles font style change events.
 */
class FontStyleListener implements ActionListener {

  /**
   * The parent frame.
   */
  private HuffmanFrame parent;

  /**
   * Constructs a new FontSizeListener. 
   */
  public FontStyleListener(HuffmanFrame f) {
    parent = f;
  }

  /**
   * Handles the event.  The parameter is ignored.
   */
  public void actionPerformed(ActionEvent e) {
    parent.setFontStyle();
  }
}
