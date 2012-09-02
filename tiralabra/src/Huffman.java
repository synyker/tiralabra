
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The main class. Creates instances of all the required classes.
 * @author jonnaira
 */
public class Huffman {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filename = "test7.txt";
        FrequencyCalculator calc = new FrequencyCalculator(filename);
        calc.makeNodeTable();
        //calc.printFrequencies();
        ArrayList<Node> nodesArray = calc.getNodeTable();

        PriorityQueue<Node> q = new PriorityQueue<Node>(nodesArray.size(), new NodeComparator());
        for (int i = 0; i < nodesArray.size(); i++) {
            q.add(nodesArray.get(i));
        }
        
        HuffmanTree huff = new HuffmanTree(q, nodesArray);
        Node tree = huff.makeTree();
        huff.makeCodes(tree,"");
        String[] codes = huff.getCodes();
        
        FileWriter fw = new FileWriter(filename, codes);
        fw.writeCodesToFile();
        fw.write();
        fw.closeStreams();
        
        System.out.println("Ennen:");
        for (int i = 0; i < nodesArray.size(); i++) {
            System.out.println("Merkki: " + nodesArray.get(i).ch + " Huff: " + codes[nodesArray.get(i).ch]);
            
        }
        
        FileReader fr = new FileReader("compressed.bin");
        //System.out.println(fr.fis.available());
        fr.readDictionaryLength();
        //System.out.println(fr.fis.available());
        fr.readCodes();
        //System.out.println(fr.fis.available());
        
        System.out.println("Jälkeen:");
        for (int i = 0; i < fr.codes.length; i++) {
            if(fr.codes[i] != null)
            System.out.println("Merkki: " + i + " Huff: " + fr.codes[i]);
            
        }
        fr.handleCodes();
        //System.out.println(fr.fis.available());
        fr.readFile();
        //System.out.println(fr.fis.available());
        //System.out.println("tavuja luettu: " + fr.totalread);
        //System.out.println("tavuja kirjoitettu: " + fr.totalwritten);
        

        
    }
}
