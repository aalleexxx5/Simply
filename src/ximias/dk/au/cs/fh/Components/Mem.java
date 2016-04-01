package ximias.dk.au.cs.fh.Components;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Alex on 05/01/2016.
 * Keeps track of the variables used.
 */
public class Mem {
    private ArrayList<MemPair> memory = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    public void add(MemPair item){
        lock.lock();
        try {
            if (containsKey(item.getKey())) {
                remove(item.getKey());
            }
            memory.add(item);
        }finally {
            lock.unlock();
        }
    }

    private void remove(String memKey){
        lock.lock();
        try {
            for (MemPair pair : memory) {
                if (pair.getKey().equals(memKey)) {
                    memory.remove(pair);
                    return;
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public void removeAll(){
        memory = new ArrayList<>();
    }

    private String getValue(String memKey){
        lock.lock();
        try {
            for (MemPair pair : memory) {
                if (pair.getKey().equals(memKey)) {
                    return (pair.getValue());
                }
            }
            return ("");
        }finally {
            lock.unlock();
        }
    }

    private boolean containsKey(String key){
        lock.lock();
        try {
            for (MemPair pair : memory) {
                if (pair.getKey().equals(key)) {
                    return true;
                }
            }
            return false;
        }finally {
            lock.unlock();
        }
    }

    public static String[] getValuesInArgs(String[] args){
        String[] ans = new String[args.length];
        for (int i=0; i<args.length;i++){
            while (args[i].contains(Constants.VARIABLE_SYMBOL)) {
                args[i] = args[i].substring(0,args[i].lastIndexOf(Constants.VARIABLE_SYMBOL))+Lookup.getMemInstance().getValue(args[i].substring(args[i].lastIndexOf(Constants.VARIABLE_SYMBOL)+1));
            }
            ans[i] = args[i];
        }
        return ans;
    }
}
