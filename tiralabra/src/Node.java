/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonnaira
 */
public class Node {
    
    boolean root = false;
    int ch;
    int freq;
    Node left;
    Node right;
    Node parent;
    
    public Node (int ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }
    
    public Node(int freq) {
        this.freq = freq;
        this.left = null;
        this.right = null;
    }
    
    public void setChar(int ch) {
        this.ch = ch;
    }
    
    public int getFreq() {
        return this.freq;
    }
    
    public void setLeft(Node left) {
        this.left = left;
    }
        
    public void setRight(Node right) {
        this.right = right;
    }
    
    public void setParent(Node parent) {
        
    }
    
    public void setRoot() {
        this.root = true;
    }
    
    public boolean isRoot() {
        return root;
    }
}
