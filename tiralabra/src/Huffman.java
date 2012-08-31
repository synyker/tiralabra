
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
 *
 * @author jonnaira
 */
public class Huffman {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filename = "test3.txt";
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
        fw.OriginalBytesToHuffmanBits();
        fw.makeBytes();
        
        for (int i = 0; i < nodesArray.size(); i++) {
            System.out.println("Merkki: " + (char)nodesArray.get(i).ch + " Huff: " + codes[nodesArray.get(i).ch]);
            
        }
        

        
    }
}
