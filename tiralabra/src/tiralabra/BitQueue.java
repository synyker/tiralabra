package tiralabra;


/**
 * A queue, implemented using an array and head/tail-indexing.
 * Used as a buffer for bits when converting bytes to bits.
 * When compressing, stores bits until there's enough to write a full byte.
 * When uncompressing, stores bits that will be translated back into the original character.
 * @author Jonne Airaksinen
 */
public class BitQueue {

    boolean[] queue;
    int head;
    int tail;
    int size;
   
    /**
     * The constructor for this class, initializes the indexes, size and maximum capacity.
     * @param size the maximum capacity of the queue.
     */
    public BitQueue(int size) {
        queue = new boolean[size];
        head = 0;
        tail = 0;
        size = 0;
    }
    
    /**
     * Takes a boolean value as a parameter and adds it to the queue.
     * @param bit 
     */
    
    public void add(boolean bit) {
        queue[tail] = bit;
        size+=1;
        if(tail==queue.length-1)
            tail = 0;
        else
            tail+=1;
    }
    
    /**
     * Used for removing unwanted trash bits from the end of the queue.
     * Removes one boolean value from the queue, decreases the size and tail-index by one.
     */
    
    public void remove() {
        if(size == 0)
            return;
        size -= 1;
        if(tail-1 < 0)
            tail = queue.length-1;
        else
            tail-=1;
    }
    
    /**
     * Returns the first bit in the queue.
     * Decreases size by one and increases the head-index by one.
     * @return bit the first bit in the queue.
     */
    
    public boolean poll() {
        boolean bit = queue[head];
        size-=1;
        if(head==queue.length-1)
            head = 0;
        else
            head+=1;
        
        return bit;
    }
    
    /**
     * Returns the number of bits in the queue.
     * @return size the current number of bits in the queue.
     */
    public int size() {
        return this.size;
    }
}
