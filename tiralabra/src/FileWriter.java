
import java.io.*;

/**
 * The class used for compressing the input file.
 * @author Jonne Airaksinen
 */
public class FileWriter {
    
    String filename;
    String[] codes;
    FileInputStream fis;
    FileOutputStream out;
    File read;
    File write;
    BitQueue queue;
    int trashBits = 0;
    
    
    /**
     * The constructor initializes all the required variables and the input and output streams.
     * Gets the filename of the input file and a array of Strings containing the 
     * Huffman-codes of all the characters in the input file as parameters.
     * 
     * @param filename name of the original input file.
     * @param codes String-array containing Huffman-codes for all characters in the input file.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public FileWriter(String filename, String[] codes) throws FileNotFoundException, IOException {
        this.filename = filename;
        this.codes = codes;
        read = new File(filename);
        fis = new FileInputStream(read);
        write = new File("compressed.bin");
        out = new FileOutputStream(write);

              
    }
    
    /**
     * Reads the input file, then Huffman-encodes the bytes and writes them.
     * Initializes the queue used as a buffer for the bits that are written into
     * the compressed file.
     * 
     * Reads the input file byte by byte, adding the corresponding Huffman-codes 
     * into the queue. When the queue has 8 or more bits, the method makeBytes 
     * is called for.
     * 
     * When all the bytes in the input file are read, the remaining bits in the 
     * queue along with the necessary amount of trash bits are written into the 
     * compressed file as the final compressed byte.
     * 
     * After this, the amount of trash bits is written as the very last byte of
     * the compressed file.
     * @throws IOException 
     */
    public void write() throws IOException {

        queue = new BitQueue(30);
        int readByte;
        int total = 0;
        String code;
        while (fis.available() > 0) {
            readByte = fis.read();
            total += 1;
            code = codes[readByte];
            for (int i = 0; i < code.length(); i++) {
                if(code.charAt(i) == '0') {
                    queue.add(false);                    
                }
                else if (code.charAt(i) == '1') {
                    queue.add(true);
                }
            }
            if(queue.size >= 8)
                makeBytes();
        }
        
        if(queue.size > 0) {
            trashBits = 8-queue.size();
            makeBytes();
        }
        String trash = "";
        trash+=trashBits;
        char ch = trash.charAt(0);
        out.write(ch);
    }
    
    
    /**
     * Writes the Huffman-codes into the beginning of the compressed file.
     * Writes every Huffman-code with a corresponding character in the input 
     * file into the compressed file.
     * 
     * Uses the FileOutputStream to write the character followed by its' 
     * Huffman-code, then a '|'-character to separate characters and codes from 
     * each other. 
     * 
     * The character code always consists of one byte, but the length of the 
     * length of the Huffman-code for character varies, thus a separator is needed.
     * @throws IOException 
     */
    public void writeCodesToFile() throws IOException {
        String dict = "";
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] != null) {
                dict += (char)i;
                for (int j = 0; j < codes[i].length(); j++) {
                    dict+=codes[i].charAt(j);
                }
                dict+='|';
            }
        }
        String length = "";
        length += dict.length();
        for (int i = 0; i < length.length(); i++) {
            out.write(length.charAt(i));
        }
        out.write('|');
        for (int i = 0; i < dict.length(); i++) {
            out.write(dict.charAt(i));
        }
    }
    
    /**
     * Polls the correct amount of bits from the bitQueue and calls for bitsToByte.
     * If there's 8 or more bits in the queue, the amount of bits to be polled
     * is always 8. If not, the amount is the size of the queue.
     * The for-loop assigns as many bits as needed into the boolean-array. Then
     * the method calls for the bitsToByte-method, which takes a boolean-array
     * as a parameter and returns an integer that is cast into a byte and
     * written to the compressed file.
     * @throws IOException 
     */
    public void makeBytes() throws IOException {
        boolean[] bitTable = new boolean[8];
        int poll;
        
        if(queue.size >= 8)
            poll = 8;
        else
            poll = queue.size();
        
        for (int i = 0; i < poll; i++) {
            bitTable[i] = queue.poll();
        }  
      
        byte b = (byte) bitsToByte(bitTable);
        out.write(b);
    }
    
    /**
     * Takes a boolean-array as the parameter and returns it as an integer.
     * 
     * @param bits boolean-array of 8 boolean values
     * @return 
     */
    public static int bitsToByte(boolean[] bits) {
        if (bits == null || bits.length != 8) {
            throw new IllegalArgumentException();
        }

        int data = 0;
        for (int i = 0; i < 8; i++) {
            if (bits[i]) {
                data += (1 << (7 - i));
            }
        }
        return data;
    }
    
    /**
     * Closes the input and output streams.
     * @throws IOException 
     */
    public void closeStreams() throws IOException {
        fis.close();
        out.close();
    }
    
    
}
