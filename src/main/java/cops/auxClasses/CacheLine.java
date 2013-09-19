/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

/**
 *
 * @author pedro
 */
public class CacheLine {

    private String policerString;
    private short decision;
    protected Integer[] queue;

    public CacheLine() {
    }

    public CacheLine(String policerString){
        this.policerString = policerString;
        this.decision = 0;
    }

    public short getDecision() {
        return decision;
    }

    

    public synchronized String getPolicerString() {
        return policerString;
    }

    public synchronized void setDecision(short decision) {
        this.decision = decision;
    }

    public synchronized void setPolicerString(String policerString) {
        this.policerString = policerString;
    }

    public synchronized void setQueueElement(int elem){
        int size = this.queue.length;
        this.queue[size] = elem;
    }

    public synchronized int getQueueSize(){
        return this.queue.length;
    }

    public synchronized void setEmptyQueue()
    {
        this.queue=null;
    }
}
