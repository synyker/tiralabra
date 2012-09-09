package tiralabra;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class calculates the frequencies for each different byte/character in 
 * the input file.
 * @author Jonne Airaksinen
 */
public class FrequencyCalculator {
    
    String filename;
    int[] freqTable;
    Node[] nodeTable = new Node[256];
    
    /**
     * The constructor initializes everything needed for the class to work.
     * The constructor also actually reads all the bytes from the input file
     * and increments the frequency in the corresponding index of the integer
     * array containing the frequencies.
     * @param filename
     * @throws FileNotFoundException
     * @throws IOException 
     */
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
    
    /**
     * Basic get-method for the nodeTable.
     * @return Node[] nodeTable containing Nodes that have the character codes 
     * and the corresponding frequencies written into them.
     */
    public Node[] getNodeTable() {
        return this.nodeTable;
    }
    
    /**
     * Creates Nodes according to the frequencies calculated.
     * 
     */
    public void makeNodeTable() {
        for (int i = 0; i < freqTable.length; i++) {
                nodeTable[i] = new Node(i,freqTable[i]);
        }
    }
}
