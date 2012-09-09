package tiralabra;


import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main class. Creates instances of all the required classes.
 * @author Jonne Airaksinen
 */
public class Huffman {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filename = "test1.txt";
        FrequencyCalculator calc = new FrequencyCalculator(filename);
        calc.makeNodeTable();
        Node[] nodesArray = calc.getNodeTable();
        
        
        HuffmanHeap heap = new HuffmanHeap();
        for (int i = 0; i < nodesArray.length; i++) {
            if(nodesArray[i].freq != 0) {
                heap.insert(nodesArray[i]);
            }
        }
        
        
        HuffmanTree huff = new HuffmanTree(heap, nodesArray);
        Node tree = huff.makeTree();
        huff.makeCodes(tree,"");
        String[] codes = huff.getCodes();
        
        
        FileWriter fw = new FileWriter(filename, codes);
        fw.writeCodesToFile();
        fw.write();
        fw.closeStreams();
        
        
        System.out.println("Ennen:");
        for (int i = 0; i < nodesArray.length; i++) {
            if(nodesArray[i].freq != 0)
                System.out.println("Merkki: " + nodesArray[i].ch + " Huff: " + codes[nodesArray[i].ch]);
        }
        
        FileReader fr = new FileReader("compressed.bin");
        System.out.println("Tavuja alussa: "+fr.fis.available());
        fr.readDictionaryLength();
        
        System.out.println("Dictionaryn pituus: "+fr.dictionaryLength);
        System.out.println("erotus: "+ (fr.fis.available()-fr.dictionaryLength));
        
        
        fr.readCodes();
        System.out.println("dict lukemisen jälkeen: "+fr.fis.available());
        
        System.out.println("Jälkeen:");
        for (int i = 0; i < fr.codes.length; i++) {
            if(fr.codes[i] != null)
            System.out.println("Merkki: " + i + " Huff: " + fr.codes[i]);
        }
        fr.handleCodes();        
        fr.readFile();
    }
}
