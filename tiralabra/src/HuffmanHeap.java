
/**
 * This class is an implementation of a minimum-heap.
 * A basic implementation, this heap doesn't do anything special.
 * @author Jonne Airaksinen
 */
public class HuffmanHeap {
    
    Node[] heap;
    int size;
    
    /**
     * The constructor initializes a Node-array and the integer size.
     */
    public HuffmanHeap() {
        heap = new Node[256];
        size = 0;
    }
    
    /**
     * Heapifies the heap starting from the node specified in the parameters.
     * Starts from the given index and proceeds towards the bottom of the heap 
     * until the correct place for each Node is found.
     * @param i index of the Node heapifying should start from.
     */
    public void heapify(int i) {
        int smallest;
        int L = left(i);
        int R = right(i);
        if (R <= size) {
            if (heap[L] != null && L <= size && heap[R] != null && R <= size) {
                if (heap[L].freq < heap[R].freq) {
                    smallest = L;
                } else {
                    smallest = R;
                }

                if (heap[i].freq > heap[smallest].freq) {
                    swap(i, smallest);
                    heapify(smallest);
                }
            }
        }
        else if (L == size && heap[i].freq > heap[L].freq)
            swap(i,L);
    }
    
    /**
     * Inserts a Node into the heap.
     * Gets a Node as the parameter, increments the size of the heap and then 
     * starts looking for a place for the newly added Node. 
     * 
     * The search begins from the last index of the array/heap and continues up 
     * one level at a time while the indexing stays inside the heap and parent 
     * node has a greater frequency value than the node going to be inserted.
     * 
     * When either of the specified conditions are not filled, the loop stops 
     * and assigns the Node n into heap[i], depending how far up the heap/array 
     * the while loop got.
     * 
     * @param n Node n is the node being inserted into the heap.
     */
    public void insert(Node n) {
        int i = size;
        size+=1;
        
        while (i>0 && heap[parent(i)] != null && heap[parent(i)].freq > n.freq) {
            heap[i] = heap[parent(i)];
            i = parent(i);
        }
        heap[i] = n;
    }
    
    /**
     * Returns the Node with the smallest frequency.
     * Returns the Node at heap[0], which is the Node with the smallest 
     * frequency, but does not remove it from the queue.
     * @return heap[0], Node with the smallest frequency in the heap.
     */
    public Node min() {
        return heap[0];
    }
    
    /**
     * Returns the Node with the smallest frequency and deletes it from the Heap
     * Returns the Node at heap[0], which is the Node with the smallest 
     * frequency and removes it from the heap.
     * 
     * When removing a Node from the heap, the Node at the last index of the 
     * heap/array replaces the deleted Node. This Node does not belong into this 
     * position, so the heapify-method is called for with the parameter 0.
     * @return heap[0], Node with the smallest frequency in the heap.
     */
    public Node delMin() {
        Node min = heap[0];
        heap[0] = heap[size-1];
        size-=1;
        heapify(0);
        return min;
    }
    
    /**
     * Calculates the index of the left child for a specified index.
     * Gets the index as a parameter, then returns the index of the left child
     * for this index.
     * @param i int i the index used for calculating the index of a left child
     * @return index of a left child
     */
    public int left(int i) {
        return 2*i;
    }
    /**
     * Calculates the index of the right child for a specified index.
     * Gets the index as a parameter, then returns the index of the right child
     * for this index.
     * @param i int i the index used for calculating the index of a right child
     * @return int index of a right child
     */
    public int right(int i) {
        return 2*i+1;
    }
    
    /**
     * Calculates the index of the parent for a specified index.
     * Gets the index as a parameter, then returns the index of the parent
     * for this index.
     * @param i int i the index used for calculating the index of the parent
     * @return index of the parent
     */
    public int parent(int i) {
        return (i-1)/2;
    }
    
    /**
     * Swaps two nodes.
     * Places the first node into a temporary variable, then places the 2nd
     * Node into first node's original position and finally places the 1st node
     * into the 2nd node's original position.
     * @param i int i Location of the first Node
     * @param j int j Location of the second Node
     */
    public void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    /**
     * A basic getter that tells whether the heap is empty or not.
     * @return boolean true, if the heap is empty, otherwise false.
     */
    public boolean isEmpty() {
        if(size == 0)
            return true;
        else
            return false;
    }
    
    /**
     * A basic getter that returns the size of the heap.
     * @return int size The current size of the heap.
     */
    public int getSize() {
        return this.size;
    }
}
