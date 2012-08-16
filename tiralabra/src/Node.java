/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonnaira
 */
public class Node {
    
    int ch;
    int freq;
    Node left;
    Node right;
    
    public Node (int ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }
    
    public int getFreq() {
        return this.freq;
    }
}
