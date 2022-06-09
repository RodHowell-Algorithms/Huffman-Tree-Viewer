# Huffman Tree Viewer

**Huffman Tree Viewer** is an application for creating and displaying Huffman trees. It uses the package [**edu.ksu.cis.viewer**](https://github.com/RodHowell-Algorithms/Tree-Viewer).

## Installation

To install the application, simply download the JAR archive [`huffmanviewer.jar`](https://github.com/RodHowell-Algorithms/Huffman-Tree-Viewer/raw/main/huffmanviewer.jar). The [Java<sup>TM</sup> SE Runtime Environment (JRE)](https://java.com) is required to run the heap viewer.

## Usage

The program may be started by either opening `huffmanviewer.jar` or executing the following from a command line in a folder containing this archive:

<pre>
java -jar huffmanviewer.jar
</pre>


From the main window, input can be provided a string provided either as text entered in the text field or as the contents of a specified file by clicking the "Choose File" button. Based upon this input, a Huffman tree is generated. This tree describes a varying-length binary encoding for each character in the input string such that the length of the encoded string is minimized. The Huffman tree is displayed, along with a table giving, for each character in the string, its original encoding in hexadecimal (this helps to identify non-printing characters), its binary Huffman code, and its number of occurrences in the string. The Huffman code actually describes the path from the root of the tree to the node containing the encoded character: a 0 represents an edge to a left child, and a 1 represents an edge to a right child.

The table is initially sorted, first in descending order of number of occurrences, then in ascending order of length of Huffman code, then in ascending order of Huffman code. Using the Sort menu, the user can choose how the table is sorted. A stable sorting algorithm is used, so that if the table is first sorted by one key then another, the result will have the second key as primary key, and the first key as secondary key.

Font characteristics may be changed using the Font menu.

## Compiling the Code

If you wish to modify the code, you will need to download a copy, either by cloning it with `git` or by downloading and decompressing a [ZIP archive](https://github.com/RodHowell-Algorithms/Huffman-Tree-Viewer/archive/refs/heads/main.zip). To compile the code, assuming you have the [Java Development Kit (JDK)](https://www.java.com/en/download/manual.jsp) installed, enter the following from a command line within the root folder of the project (i.e., the one containing the subfolder `edu`):

<pre>
javac -classpath one-jar/lib/viewer.jar edu/ksu/cis/huffmanCodes/*.java
</pre>


(Depending on your shell, you may need to replace each `/` with `\`.) To run the program after compiling it:

<pre>
java -cp .;one-jar/lib/viewer.jar edu.ksu.cis.huffmanCodes.Driver
</pre>


(Depending on your shell, you may need to escape the `;` or replace it with `:`, and/or you may need to replace each `/` with `\`.)

## Creating the JAR Archive

To create a JAR archive, you will first need to create an archive containing just the package **edu.ksu.cis.huffmanCodes**. Because the command is rather long, the files `options.txt` and `Manifest.txt` have been provided to shorten it:

<pre>
jar -c -f huffmanviewer-only.jar @options.txt edu/ksu.cis/huffmanCodes/*.class
</pre>


This creates the Jar archive `huffmanviewer-only.jar`. It can be run by opening it, but only if the file `viewer.jar` (found in the folder `one-jar/lib`) is in the same folder. These can be packaged together using [One-Jar<sup>TM</sup>](http://one-jar.sourceforge.net/index.php?page=getting-started&file=quickstart). First, move `huffmanviewer-only.jar` to the folder `one-jar/main`. Then from the `one-jar` folder:

<pre>
jar -c -f ../huffmanviewer.jar -m boot-manifest.mf .
</pre>


This will create the standalone JAR archive `huffmanviewer.jar`.