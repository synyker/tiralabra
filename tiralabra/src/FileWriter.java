
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonnaira
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
    
    public FileWriter(String filename, String[] codes) throws FileNotFoundException, IOException {
        this.filename = filename;
        this.codes = codes;
        read = new File(filename);
        fis = new FileInputStream(read);
        write = new File("compressed.bin");
        out = new FileOutputStream(write);

              
    }
    
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
    
    public void makeBytes() throws IOException {
        int bitsWritten = 0;
        int byteBits;
        int data;
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
    
    public void WriteBytes() {
        
    }
    
    public void closeStreams() throws IOException {
        fis.close();
        out.close();
    }
    
    
}
