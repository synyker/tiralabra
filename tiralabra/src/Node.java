
/**
 * This is an implementation of a basic node, used by multiple data structures.
 * @author Jonne Airaksinen
 */
public class Node {
    
    int ch;
    int freq;
    Node left;
    Node right;
    
    /**
     * The primary constructor of this class.
     * Gets a character code and a frequency as the parameters and saves the 
     * values into variables. These values are used in many situations during 
     * the program's run.
     * @param ch int ch The character code of the character that is 
     * saved into the node.
     * @param freq int freq The frequency of the character specified in the 
     * variable ch.
     */
    public Node (int ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }
    /**
     * The alternative constructor of this class, with no character code.
     * Used mostly when adding 2 Nodes as child nodes for a new Node. This is
     * crucial in the building phase of the Huffman-tree.
     * @param freq Only the frequency is saved into the Node.
     */
    public Node(int freq) {
        this.freq = freq;
        this.left = null;
        this.right = null;
    }
    
    /**
     * Sets the character code saved in the Node.
     * Sets or changes the character code saved into the variable ch.
     * @param ch Character code that will be saved into the Node.
     */
    public void setChar(int ch) {
        this.ch = ch;
    }
    
    /**
     * A basic getter for the frequency of the Node.
     * Just returns the frequency saved in the Node.
     * @return The frequency saved into the variable freq in the Node.
     */
    public int getFreq() {
        return this.freq;
    }
    
    /**
     * Sets a left child for the Node.
     * Sets the Node that is given as a parameter to be the left child of this 
     * Node.
     * @param left The new left Node.
     */
    public void setLeft(Node left) {
        this.left = left;
    }
    
    /**
     * Sets a right child for the Node.
     * Sets the Node that is given as a parameter to be the right child of this 
     * Node.
     * @param right The new right Node.
     */
    public void setRight(Node right) {
        this.right = right;
    }
}
