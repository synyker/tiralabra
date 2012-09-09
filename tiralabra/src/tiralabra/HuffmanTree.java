package tiralabra;


/**
 * This class generates the Huffman-codes for each character in the input file.
 * @author Jonne Airaksinen
 */
public class HuffmanTree {
    
    private HuffmanHeap heap;
    private String[] codes = new String[256];
    
    /**
     * The constructor initializes the mimimum-heap used as a priority queue.
     * @param heap
     * @param leaves 
     */
    public HuffmanTree(HuffmanHeap heap, Node[] leaves) {
        this.heap = heap;
    }
    
    /**
     * This method constructs the Huffman-tree.
     * The Huffman tree contains the Huffman-codes for each character. To be 
     * specific, the routes from the root to the leaves of the tree along with 
     * the data in the leaf-nodes are everything you need for generating an 
     * array that can contain the Huffman-code for every ascii256-character.
     * crucial information stored into the tree.
     * 
     * On each run of the while(true)-loop, two things can happen.
     * 
     * If there's 2 or more Nodes in the heap, the method takes 2 Nodes with the 
     * smallest frequency from the heap. It then creates a new Node and places 
     * the 2 Nodes taken from the heap as the left and right child of the new 
     * Node. The frequency of the new Node is the sum of its' child nodes' 
     * frequencies. The new node is then inserted into the correct position in 
     * the heap.
     * 
     * If there's only one Node left in the heap, the Huffman-tree has been
     * constructed. The remaining Node is then placed into a variable root and 
     * returned.
     * 
     * @return Node root The fully constructed Huffman-tree
     */
    public Node makeTree() {
        while(true) {
            if (heap.getSize() >= 2) {
                Node x = heap.delMin();
                Node y = heap.delMin();
                Node z = new Node(x.getFreq() + y.getFreq());
                z.setLeft(x);
                //x.setParent(z);
                z.setRight(y);
                //y.setParent(z);
                heap.insert(z);
            }
            if(heap.getSize() == 1) {
                Node root = heap.delMin();
                return root;
            }
        }
    }
    
    /**
     * This is a recursive method used for traversing the Huffman-tree.
     * By traversing the Huffman-tree, all of the Huffman-codes are generated. 
     * When recursively navigating into a left subtree, a "0" is added to the 
     * code and correspondingly when a "1" when navigating to a right subtree. 
     * 
     * The code is always in the correct state, because when returning from a 
     * recursion call, one character is always removed from the variable 
     * containing the code string.
     * 
     * When the tree traversal reaches a leaf-node, a string containing the 
     * Huffman code is written into the String-array codes. The right index in 
     * the array is decided by the character code saved into the leaf-node.
     * @param x
     * @param code 
     */
    public void makeCodes(Node x, String code) {
        if(x != null) {
            makeCodes(x.left, code+"0");
            if (x.left == null && x.right == null)
                this.codes[x.ch] = code;
            makeCodes(x.right, code+"1");
        }
    }
    
    /**
     * A basic getter that returns the String-array containing the Huffman-codes.
     * @return String[] codes, containing the Huffman-codes.
     */
    public String[] getCodes() {
        return this.codes;
    }
}
