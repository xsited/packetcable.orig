/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valter Vicente - 39360 <valtervicente@ua.pt>
 * @author Pedro Bento
 */
public class Cache {

    private CacheLine cache[] = new CacheLine[10];
    private int nSessions = 0;

    public Cache() {
    }

    public boolean newCacheLine(String policerString) throws UnknownHostException {

        // DO THIS
        // Check if this rule is not already present
        for (int i = 0; i < this.nSessions; i++) {
            if (policerString.equalsIgnoreCase(cache[i].getPolicerString())) {
                return true;
            }
        }
        // Do this if the cache Repository is full
        if (nSessions == cache.length) {
            CacheLine aux[] = new CacheLine[cache.length + 10];
            System.arraycopy(cache, 0, aux, 0, cache.length);
            cache = aux;
        }

        cache[nSessions] = new CacheLine(policerString);

        nSessions++;
        return false;
    }

    public void changeCacheLineDecision(String policerString, short decision) {
        for (int i = 0; i < nSessions; i++) {
            if (policerString.equalsIgnoreCase(cache[i].getPolicerString())) {
                cache[i].setDecision(decision);
                break;
            }
        }
    }

    public void changeCacheLineDecision(int elem, short decision) {
        this.cache[elem].setDecision(decision);
    }

    public void changeCacheLineAddElem(String policerString, Integer elem) {
        for (int i = 0; i < nSessions; i++) {
            if (policerString.equalsIgnoreCase(cache[i].getPolicerString())) {
                cache[i].setQueueElement(elem);
                break;
            }
        }
    }

    public int CacheLineDiscover(int elem) {
        for (int i = 0; i < nSessions; i++) {
            for (int j = 0; j < cache[i].getQueueSize(); j++) {
                if (cache[i].queue[j] == elem) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Integer[] getQueueFromLine(int elem) {
        return this.cache[elem].queue;
    }

    public int getLineDecision(int elem) {
        return this.cache[elem].getDecision();
    }

    public int getnSessions() {
        return nSessions;
    }

    public void setQueueNull(int elem){
        this.cache[elem].setEmptyQueue();

    }
}
