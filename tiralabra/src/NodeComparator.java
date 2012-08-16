
import java.util.Comparator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonnaira
 */
public class NodeComparator implements Comparator{
    
    Node node1 = new Node(0,0);
    Node node2 = new Node(0,0);
    
    public int compare(Object o1, Object o2) {
        if(o1.getClass() == this.node1.getClass())
            this.node1 = (Node)o1;
        
        if(o2.getClass() == this.node2.getClass())
            this.node2 = (Node)o2;
        
        int freq1 = node1.getFreq();
        int freq2 = node2.getFreq();
        
        if(freq1 > freq2)
            return 1;
        else if(freq1 < freq2)
            return -1;
        else
            return 0;
    }
    
    public boolean equals (Object o) {
        if(o.equals(this))
            return true;
        else
            return false;
    }
}
