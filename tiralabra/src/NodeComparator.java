
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
    
    Node nnode1;
    Node nnode2;
    
    public int compare(Object node1, Object node2) {
        if(node1.getClass() == this.nnode1.getClass())
            this.nnode1 = (Node)node1;
        
        if(node2.getClass() == this.nnode2.getClass())
            this.nnode2 = (Node)node2;
        
        int freq1 = nnode1.freq;
        int freq2 = nnode2.freq;
        
        if(freq1 < freq2)
            return 1;
        else if(freq1 > freq2)
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
