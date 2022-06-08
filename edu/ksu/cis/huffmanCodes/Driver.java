// File: Driver.java
// A demo to display Huffman codes and trees.

package edu.ksu.cis.huffmanCodes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Button;
import java.awt.Container;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.FileReader;
import java.awt.FlowLayout;
import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A demo to display Huffman codes and trees.
 */
public class Driver extends JPanel {

  /**
   * The text field in which the input strings will be entered.
   */
  JTextField text = new JTextField(15);

  /**
   * Used for consistency in serialization.
   */
  private static final long serialVersionUID = 1L;

  /** Constructs a new Driver.
   */
  public Driver(JFrame f) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
      // This shouldn't happen
      e.printStackTrace();
    }
    setLayout(new FlowLayout());
    add(new JLabel("Enter String: "));
    text.addActionListener(new TextEntryListener(this));
    add(text);
    Button btn = new Button("Choose File");
    btn.addActionListener(new FileOpenListener(this, f));
    add(btn);
  }

  /**
   * Starts the program as an application.
   */
  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setContentPane(new Driver(f));
    f.addWindowListener(new Terminator());
    f.setTitle("Huffman Codes");
    f.pack();
    f.setVisible(true);
  }

  /**
   * Returns the input string provided by the user.
   */
  public String getInputString() {
    String s = text.getText();
    text.setText("");
    return s;
  }
}

/**
 * The event handler for the text field.
 */
class TextEntryListener implements ActionListener {

  /**
   * The driver applet.
   */
  private Driver theDriver;

  /**
   * Constructs a new TextEntryListener.
   */
  public TextEntryListener(Driver d) {
    theDriver = d;
  }

  /**
   * Handles the event.
   */
  public void actionPerformed(ActionEvent e) {
    String s = theDriver.getInputString();
    HuffmanFrame f = new HuffmanFrame(new HuffmanTree(s));
    f.setTitle("Huffman Tree");
    f.setVisible(true);
  }
}

/**
 * The event handler for closing the driver frame.
 */
class Terminator extends WindowAdapter {

  /**
   * Handles the event by terminating the program.
   */
  public void windowClosing(WindowEvent e) {
    System.exit(0);
  }
}

/**
 * The event handler for opening a file.
 */
class FileOpenListener implements ActionListener {

  /**
   * The driver.
   */
  private Driver theDriver;

  /**
   * The frame containing this panel.
   */
  private JFrame theFrame;

  /**
   * Constructs a new FileOpenListener.
   */
  public FileOpenListener(Driver d, JFrame f) {
    theDriver = d;
    theFrame = f;
  }

  /**
   * Handles the event.  The parameter is ignored.
   */
  public void actionPerformed(ActionEvent e) {
    InputStreamReader in;
    try {
      in = getInputStreamReader(); 
      if (in != null) { // If a file was selected
	StringBuffer sb = new StringBuffer();
	int c = in.read();
	while (c != -1) {
	  sb.append((char) c);
	  c = in.read();
	}
	HuffmanFrame f = new HuffmanFrame(new HuffmanTree(sb.toString()));
	f.setTitle("Huffman Tree");
	f.setVisible(true);
      }
    }
    catch (IOException ex) { 
      JOptionPane.showMessageDialog(theDriver, ex.getMessage(), 
				    "I/O Error", JOptionPane.WARNING_MESSAGE);
    }
    catch (SecurityException ex) {
      JOptionPane.showMessageDialog(theDriver,
				    "Access to the file system is prohibited.",
				    "Security Error",
				    JOptionPane.WARNING_MESSAGE);
    }
    catch (NoClassDefFoundError ex) {
      JOptionPane.showMessageDialog(theDriver,
				    "Package EDU.ksu.cis.viewer not found.",
				    "Load Error", JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Gets an InputStreamReader associated with a file chosen by the user.
   * This method uses a JFileChooser to obtain the file.  If no file is
   * chosen, the method returns null.
   * @throws SecurityException  If read access to the file or file system is
   *                            denied.
   * @throws IOException        If the file cannot be opened.
   */
  private InputStreamReader getInputStreamReader() 
    throws SecurityException, IOException {
    FileDialog fileChooser = new FileDialog(theFrame, "Open file",
					    FileDialog.LOAD);
    fileChooser.setVisible(true);
    String fn = fileChooser.getFile();
    if (fn != null) {
      return new FileReader(fn);
    }
    else return null;
  }
}

