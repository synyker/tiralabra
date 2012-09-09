package tiralabra;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Used for reading the file and uncompressing it.
 * @author Jonne Airaksinen
 */
public class FileReader {

    String filename;
    Node root;
    FileInputStream fis;
    FileOutputStream out;
    String[] codes;
    int dictionaryLength;
    BitQueue queue;
    int trashBits;
    int totalwritten = 0;
    int totalread = 0;

    /**
     * The constructor initializes everything required for uncompressing the file.
     * Gets only a filename as a parameter, initializes the Huffman-tree, input 
     * and output streams and the array for Huffman-codes.
     * @param filename the filename for the uncompressed file.
     * @throws FileNotFoundException 
     */
    public FileReader(String filename) throws FileNotFoundException {
        this.filename = filename;
        root = new Node(0);
        fis = new FileInputStream(filename);
        out = new FileOutputStream("uncompressed");
        codes = new String[256];
    }

    /**
     * Reads the length of the Huffman-dictionary from the beginning of the file.
     * Reads bytes into a string until it finds a '|'-character.
     * Then parses the string into an integer that determines the length of the 
     * character-code pairs that follow after the length of the dictionary in 
     * the file.
     * @throws IOException 
     */
    public void readDictionaryLength() throws IOException {
        int read;
        String length = "";

        read = fis.read();
        while ((char) read != '|') {
            length += (char) read;
            read = fis.read();
        }
        dictionaryLength = Integer.parseInt(length);
    }

    /**
     * Reads the Huffman-codes from the compressed file.
     * Reads the character code, saves it into the variable index.
     * Then proceeds to read the Huffman-code, which is followed by a '|'-character.
     * The length of this dictionary is saved into the variable "dictionaryLength" 
     * earlier, the while loop stops according to this variable.
     * The codes are read from the compressed file and again saved into a String
     * array.
     * @throws IOException 
     */
    public void readCodes() throws IOException {

        int read;
        int index;
        String code;
        int bytesRead = 0;

        while (bytesRead < dictionaryLength) {
            code = "";
            read = fis.read();
            bytesRead += 1;
            index = read;
            read = fis.read();
            bytesRead += 1;
            while (read != '|' && bytesRead < dictionaryLength) {
                code += (char) read;
                read = fis.read();
                bytesRead += 1;
            }
            codes[index] = code;
        }
    }

    /**
     * Goes through the array of Strings containing the Huffman-codes.
     * A simple for-loop that ignores null values in the array. Calls the method
     * makeNode for every non-null code.
     */
    public void handleCodes() {

        for (int i = 0; i < codes.length; i++) {
            if (codes[i] != null && codes[i].length() != 0) {
                makeNode(codes[i], i);
            }
        }
    }

    /**
     * Reconstructs the Huffman-tree using the codes.
     * Uses the Huffman-codes obtained from the compressed file to reconstruct 
     * the Huffman-tree. This is required for making sense of the bits written 
     * into the compressed file.
     * 
     * Creates child-nodes for the previously initialized root-node, if necessary.
     * If the child-nodes already exist, it skips to the next level of the tree.
     * 
     * When the for loop reaches the last character in the String code, it sets
     * the character with code index as the character of this leaf-node.
     * @param code the Huffman-code for the character defined by variable index.
     * @param index the character code for the character that is currently being
     *              added to the tree.
     */
    public void makeNode(String code, int index) {
        Node x = root;

        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0') {
                if (x.left == null) {
                    x.setLeft(new Node(0));
                    x = x.left;
                    if (i == code.length() - 1) {
                        x.setChar(index);
                    }
                } else {
                    x = x.left;
                }

            } else if (code.charAt(i) == '1') {
                if (x.right == null) {
                    x.setRight(new Node(0));
                    x = x.right;
                    if (i == code.length() - 1) {
                        x.setChar(index);
                    }
                } else {
                    x = x.right;
                }
            }
        }
    }

    /**
     * Reads the actual compressed data from the compressed file.
     * Reads bytes, converts them into bits and inserts them into a queue. 
     * The maximum capacity of the queue is 256, because that is the potential 
     * maximum length of a Huffman-code(?). 
     * 
     * The method reads the queue full of bits and then proceeds to call for the
     * writeToFile-method, which uncompresses one character worth of bits.
     * 
     * When the FileInputStream has only 1 byte left, it is read into a separate
     * variable. This variable called trashBits tells the amount of bits with no
     * meaning, used to fill the last byte. Then this amount of bits are removed
     * from the queue, so they won't interfere with the uncompressing of the file.
     * @throws IOException 
     */
    
    public void readFile() throws IOException {
        queue = new BitQueue(512);
        int read;
        boolean[] bitsFromByte;
        
        while(true) {
            while(fis.available() > 1 && queue.size < 256-7) {
                read = fis.read();
                totalread += 1;
                bitsFromByte = byteToBits(read);
                for (int i = 0; i < bitsFromByte.length; i++) {
                    queue.add(bitsFromByte[i]);
                }
            }
            if(fis.available() == 1) {
                char lastByte = (char)fis.read();
                totalread += 1;
                trashBits = Integer.parseInt(""+lastByte);
                for (int i = 0; i < trashBits; i++) {
                    queue.remove();
                }
            }
            if(queue.size > 0) {
                writeToFile();
            }
            else {
                return;
            }
        }
    }
    
    
    /**
     * Handles the actual uncompressing of the file.
     * Polls the queue of bits and navigates the Huffman-tree according to the 
     * boolean value. When false, go left; when true, go right.
     * 
     * When the method reaches a node that has no children, i.e. is a leaf-node, 
     * the character saved to the node is written to the output file using
     * FileOutputStream. After doing this, the method returns.
     * @throws IOException 
     */
    public void writeToFile() throws IOException {
        Node x = root;
        boolean bit;
        while(true) {
            bit = queue.poll();
            if(!bit) {
                x = x.left;
                if(x.left == null && x.right == null) {
                    out.write(x.ch);
                    totalwritten += 1;
                    return;
                }
            }
            else if(bit) {
                x = x.right;
                if(x.left == null && x.right == null) {
                    out.write(x.ch);
                    totalwritten += 1;
                    return;
                }
            }
        }
    }
    
    /**
     * A method used for converting bytes into bits.
     * Loops through the boolean-array of 8 bits. The first bit is the most 
     * meaningful, so it is handled first. If the boolean value is true, the 
     * integer value of the first bit is 1 shifted 7 places left, in other words
     * 128. The 2nd bit equals to 64 and so on.
     * @param data integer value of the byte.
     * @return a boolean array containing the 8 bits in the original byte.
     */
    public static boolean[] byteToBits(int data) {
        if (data < 0 || 255 < data) {
            throw new IllegalArgumentException("" + data);
        }

        boolean[] bits = new boolean[8];
        for (int i = 0; i < 8; i++) {
            bits[i] = ((data & (1 << (7 - i))) != 0);
        }
        return bits;
    }
}
