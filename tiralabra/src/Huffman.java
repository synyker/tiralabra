
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
        FrequencyCalculator calc = new FrequencyCalculator("test.txt");
        calc.maneNodeTable();
        calc.printFrequencies();
        ArrayList<Node> nodesArray = calc.getNodeTable();

        PriorityQueue<Node> q = new PriorityQueue<Node>(nodesArray.size(), new NodeComparator());
        for (int i = 0; i < nodesArray.size(); i++) {
            q.add(nodesArray.get(i));
        }
        
        while(!q.isEmpty()) {
            Node n = q.poll();
            System.out.println("merkki: " + n.ch + "freq: " + n.freq);
        }
        
    }
}
