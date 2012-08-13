
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
    ArrayList<Node> nodeTable = new ArrayList<Node>();
    
    public FrequencyCalculator(String filename) throws FileNotFoundException, IOException {
        this.filename = filename;
        File file = new File(filename);
        freqTable = new int[256];
        FileInputStream fis = new FileInputStream(file);
        
        int read;
            while(fis.available() > 0) {
                read = fis.read();
                freqTable[read] += 1;
            }
            
    }
    
    public ArrayList<Node> getNodeTable() {
        return this.nodeTable;
    }
    
    public void printFrequencies() {
        System.out.println("Eri merkkejä on: " + nodeTable.size());
        for (int i = 0; i < nodeTable.size(); i++) {
                System.out.println("Tiedostossa on " + nodeTable.get(i).freq + " kertaa merkki " + (char)nodeTable.get(i).ch);
        }
    }
    
    public void maneNodeTable() {
        for (int i = 0; i < freqTable.length; i++) {
            if(freqTable[i] != 0) {
                System.out.println(i);
                nodeTable.add(new Node(i, freqTable[i]));
            }
        }
    }
}
