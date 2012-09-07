
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonnaira
 */
public class FrequencyCalculator {
    
    String filename;
    int[] freqTable;
    Node[] nodeTable = new Node[256];
    
    public FrequencyCalculator(String filename) throws FileNotFoundException, IOException {
        this.filename = filename;
        File file = new File(filename);
        freqTable = new int[256];
        FileInputStream fis = new FileInputStream(file);
        
        int read;
        while (fis.available() > 0) {
            read = fis.read();
            freqTable[read] += 1;
        }
        fis.close();
    }
    
    public Node[] getNodeTable() {
        return this.nodeTable;
    }
    
    public void makeNodeTable() {
        for (int i = 0; i < freqTable.length; i++) {
                nodeTable[i] = new Node(i,freqTable[i]);
        }
    }
}
