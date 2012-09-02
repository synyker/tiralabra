/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kristalongi
 */
public class BitQueue {

    boolean[] queue;
    int head;
    int tail;
    int size;
    
    public BitQueue() {
        queue = new boolean[30];
        head = 0;
        tail = 0;
        size = 0;
    }
    
    public void add(boolean bit) {
        queue[tail] = bit;
        size+=1;
        if(tail==queue.length-1)
            tail = 0;
        else
            tail+=1;
    }
    
    public boolean poll() {
        boolean bit = queue[head];
        size-=1;
        if(head==queue.length-1)
            head = 0;
        else
            head+=1;
        
        return bit;
    }
    
    public int size() {
        return this.size;
    }
}
