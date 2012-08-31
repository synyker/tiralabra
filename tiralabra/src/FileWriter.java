
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
    boolean[] huffbits;
    int totalbits;
    int trashBits;
    
    public FileWriter(String filename, String[] codes) throws FileNotFoundException, IOException {
        this.filename = filename;
        this.codes = codes;
        read = new File(filename);
        fis = new FileInputStream(read);
        write = new File("compressed.bin");
        out = new FileOutputStream(write);
        
        
    }
    
    public void OriginalBytesToHuffmanBits() throws IOException {
       
        huffbits = new boolean[fis.available()*8];
        int orig = fis.available()*8;
        int bits = 0;
        int readByte;
        String code;
        while (fis.available() > 0) {
            readByte = fis.read();
            code = codes[readByte];
            for (int i = 0; i < code.length(); i++) {
                if(code.charAt(i) == '0') {
                    huffbits[bits] = false;
                    bits+=1;
                }
                else if (code.charAt(i) == '1') {
                    huffbits[bits] = true;
                    bits+=1;
                }
            }
        }
        System.out.println("Bittejä: "+bits);
        System.out.println("Alkuperäisesti: "+orig);
        totalbits = bits;
        fis.close();
    }
    
    public void makeBytes() throws IOException {
        int bitsWritten = 0;
        int byteBits;
        int data;
        boolean[] bitTable = new boolean[8];
        while(bitsWritten < totalbits) {
            bitTable = new boolean[8];
            if(totalbits-bitsWritten > 8)
                byteBits = 8;
            else {
                byteBits = totalbits-bitsWritten;
                trashBits = 8-byteBits;
            }
            
            for (int i = 0; i < byteBits; i++) {
                bitTable[i] = huffbits[bitsWritten];
                bitsWritten += 1;
            }
            byte b = (byte) bitsToByte(bitTable);
            out.write(b);
        }
        
        out.close();
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
    
    
}
