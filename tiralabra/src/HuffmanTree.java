
import java.util.ArrayList;
import java.util.PriorityQueue;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonnaira
 */
public class HuffmanTree {
    
    private HuffmanHeap heap;
    private PriorityQueue q;
    private Node[] leaves;
    private String[] codes = new String[256];
    
    public HuffmanTree(HuffmanHeap heap, Node[] leaves) {
        this.leaves = leaves;
        this.heap = heap;
    }
    
    
    public void printQueue () {
        while(!heap.isEmpty()) {
            Node n = heap.delMin();
            System.out.println("merkki: " + n.ch + " freq: " + n.freq);
        }
    }
    
    public Node makeTree() {
        
        
        while(true) {
            if (heap.getSize() >= 2) {
                Node x = heap.delMin();
                Node y = heap.delMin();
                Node z = new Node(x.getFreq() + y.getFreq());
                z.setLeft(x);
                x.setParent(z);
                z.setRight(y);
                y.setParent(z);
                heap.insert(z);
            }
            if(heap.getSize() == 1) {
                Node root = heap.delMin();
                root.setRoot();
                return root;
            }
        }
    }
    
    public void makeCodes(Node x, String code) {
        if(x != null) {
            makeCodes(x.left, code+"0");
            if (x.left == null && x.right == null)
                this.codes[x.ch] = code;
            makeCodes(x.right, code+"1");
        }
    }
    
    public String[] getCodes() {
        return this.codes;
    }
}
