
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author kristalongi
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

    public FileReader(String filename) throws FileNotFoundException {
        this.filename = filename;
        root = new Node(0);
        fis = new FileInputStream(filename);
        out = new FileOutputStream("uncompressed");
        codes = new String[256];
    }

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

    public void readCodes() throws IOException {

        int read;
        int index;
        String code;
        int bytesRead = 0;

        while (bytesRead <= dictionaryLength) {
            code = "";
            read = fis.read();
            bytesRead += 1;
            index = read;
            read = fis.read();
            bytesRead += 1;
            while (read != '|' && bytesRead <= dictionaryLength) {
                code += (char) read;
                read = fis.read();
                bytesRead += 1;
            }
            codes[index] = code;
        }
    }

    public void handleCodes() {

        for (int i = 0; i < codes.length; i++) {
            if (codes[i] != null && codes[i].length() != 0) {
                makeNode(codes[i], i);
            }
        }
    }

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

    public void readFile() throws IOException {
        queue = new BitQueue(256);
        int read;
        boolean[] bitsFromByte;
        
        while(true) {
            while(fis.available() > 1 && queue.size < 256) {
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
                int trashBytes = Integer.parseInt(""+lastByte);
                for (int i = 0; i < 6; i++) {
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
