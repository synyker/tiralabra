/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jonne
 */
public class HuffmanHeap {
    
    Node[] heap;
    int size;
    
    public HuffmanHeap() {
        heap = new Node[256];
        size = 0;
    }
    
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
    
    public void insert(Node n) {
        int i = size;
        size+=1;
        
        while (i>0 && heap[parent(i)] != null && heap[parent(i)].freq > n.freq) {
            heap[i] = heap[parent(i)];
            i = parent(i);
        }
        heap[i] = n;
    }
    
    public Node min() {
        return heap[0];
    }
    
    public Node delMin() {
        Node min = heap[0];
        heap[0] = heap[size-1];
        size-=1;
        heapify(0);
        return min;
    }
    
    public int left(int i) {
        return 2*i;
    }
    
    public int right(int i) {
        return 2*i+1;
    }
    
    public int parent(int i) {
        return (i-1)/2;
    }
    
    public void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    public boolean isEmpty() {
        if(size == 0)
            return true;
        else
            return false;
    }
    
    public int getSize() {
        return this.size;
    }
}
