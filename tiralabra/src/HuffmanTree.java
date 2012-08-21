
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
    
    private PriorityQueue q;
    private ArrayList<Node> leaves;
    private String[] codes = new String[256];
    
    public HuffmanTree(PriorityQueue q, ArrayList<Node> leaves) {
        this.q = q;
        this.leaves = leaves;
    }
    
    
    public void printQueue () {
        while(!q.isEmpty()) {
            Node n = (Node)q.poll();
            System.out.println("merkki: " + n.ch + " freq: " + n.freq);
        }
    }
    
    public Node makeTree() {
        while(true) {
            Node x = (Node)q.poll();
            Node y = (Node)q.poll();
            Node z = new Node(x.getFreq()+y.getFreq());
            z.setLeft(x);
            x.setParent(z);
            z.setRight(y);
            y.setParent(z);
            q.add(z);
            if(q.size() == 1) {
                Node root = (Node)q.poll();
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
